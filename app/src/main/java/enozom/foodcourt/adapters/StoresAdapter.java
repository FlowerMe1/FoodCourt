package enozom.foodcourt.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

import java.util.List;

import enozom.foodcourt.R;
import enozom.foodcourt.databinding.StoreCellBinding;
import enozom.foodcourt.models.Store;
import enozom.foodcourt.util.UtilityMethods;

public class StoresAdapter extends BaseRecyclerViewAdapter {

    private AQuery aQuery;
    private Context context;

    public StoresAdapter(List<Store> storeList, Context context) {
        super(storeList);
        aQuery = new AQuery(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            return super.onCreateViewHolder(parent, viewType);
        }

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        final StoreCellBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.store_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        int type = getItemViewType(position);

        if (type == TYPE_EMPTY)
            return;

        final ViewHolder holder = (ViewHolder) viewHolder;
        Store store = (Store) items.get(position);
        holder.binding.setStore(store);

        aQuery.id(holder.binding.storeLogo).image(store.getLogo(), false, true, 0, 0, new BitmapAjaxCallback() {
            @Override
            protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                float bitmapHeight = context.getResources().getDimension(R.dimen.cell_img_height);
                float bitmapwidth = UtilityMethods.getBitmapWidth(bm, (int) bitmapHeight);
                Bitmap scaledBitmap = UtilityMethods.scaleBitmap(bm, bitmapwidth, bitmapHeight);
                holder.binding.storeLogo.setImageBitmap(scaledBitmap);
            }
        });
        holder.binding.imageProgress.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final StoreCellBinding binding;

        ViewHolder(final StoreCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
