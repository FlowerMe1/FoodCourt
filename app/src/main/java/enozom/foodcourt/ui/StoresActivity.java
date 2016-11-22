package enozom.foodcourt.ui;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import enozom.foodcourt.R;
import enozom.foodcourt.adapters.StoresAdapter;
import enozom.foodcourt.api.VolleyHelper;
import enozom.foodcourt.api.interfaces.ClientCallback;
import enozom.foodcourt.custom.DividerItemDecoration;
import enozom.foodcourt.databinding.ActivityStoresBinding;
import enozom.foodcourt.managers.StoresManager;
import enozom.foodcourt.models.Store;
import enozom.foodcourt.util.UtilityMethods;

public class StoresActivity extends BaseActivity implements ClientCallback, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private ActivityStoresBinding binding;
    private StoresAdapter adapter;
    private List<Store> storeArrayList;
    public static final int LANDSCAPE_COLS_NUM = 2, PORTRAIT_COLS_NUM = 1;
    private StaggeredGridLayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stores);
        storeArrayList = new ArrayList<>();
        apiClient.getStores(this);
        initUI();
    }

    private void initUI() {
        binding.storesSwipeContainer.setRefreshing(true);
        initToolbar(binding.toolbar.toolbar, false);
        setToolBarCustomView(R.layout.custom_toolbar_layout);
        initRecyclerView();
        initSearchView();
    }

    @Override
    public void onServerResultSuccess(Object object) {
        JSONArray responseJsonArray = (JSONArray) object;
        Gson gson = VolleyHelper.customizeGsonBuilder().create();
        Type collectionType = new TypeToken<List<Store>>() {
        }.getType();
        if (responseJsonArray != null && collectionType != null) {
            storeArrayList = gson.fromJson(responseJsonArray.toString(), collectionType);
        }
        adapter.setItems(storeArrayList);

        /* Assuming we have got small amount of data, otherwise..this process must be moved to bg thread
        * Assuming that we just clear cached stores and save newly retrieved items by now ,, could be done via checking the one that has server id found locally then update
        * otherwise create new record*/
        StoresManager.getInstance().deleteStores();
        StoresManager.getInstance().saveStores(storeArrayList);
        binding.storesSwipeContainer.setRefreshing(false);
    }

    @Override
    public void onServerResultFailure(String errorMessage) {
        if (!isFinishing()) {
            binding.storesSwipeContainer.setRefreshing(false);
            UtilityMethods.showSnackbar(errorMessage, binding.getRoot(), this);
            binding.storesSwipeContainer.setRefreshing(false);
            /*load from cache only if recyclerview doesn't have any record*/
            if (adapter.getItems() == null || adapter.getItems().size() == 0) {
                storeArrayList = StoresManager.getInstance().getStoresList();
                adapter.setItems(storeArrayList);
            }
        }
    }

    protected void initRecyclerView() {
        RecyclerView recyclerView = binding.storesRecyclerView;
        recyclerViewLayoutManager = new StaggeredGridLayoutManager(getColsNum(), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        adapter = new StoresAdapter(storeArrayList, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                recyclerViewLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        binding.storesSwipeContainer.setOnRefreshListener(this);
    }

    private int getColsNum() {
        int columnsNum = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? LANDSCAPE_COLS_NUM : PORTRAIT_COLS_NUM;
        return columnsNum;
    }

    @Override
    public void onRefresh() {
        binding.storesSwipeContainer.setRefreshing(true);
        apiClient.getStores(this);
    }

    private void initSearchView() {
        SearchView searchView = (SearchView) findViewById(R.id.stores_search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.clearFocus();
        Field searchField = null;
        try {
            searchField = SearchView.class.getDeclaredField("mCloseButton");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        searchField.setAccessible(true);
        ImageView mSearchCloseButton = null;
        try {
            mSearchCloseButton = (ImageView) searchField.get(searchView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (mSearchCloseButton != null)
            mSearchCloseButton.setEnabled(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchStore(newText.trim());
        return false;
    }


    private void searchStore(String searchStr) {
        if (!searchStr.isEmpty()) {
            ArrayList<Store> searchList = new ArrayList<>();
            for (Store store : storeArrayList) {
                if (store.getName().toLowerCase().contains(searchStr.toLowerCase()))
                    searchList.add(store);
            }
            adapter.setItems(searchList);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recyclerViewLayoutManager.setSpanCount(getColsNum());
    }
}
