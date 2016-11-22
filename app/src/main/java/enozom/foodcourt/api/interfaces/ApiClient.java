package enozom.foodcourt.api.interfaces;

public interface ApiClient {

    String GET_STORES_URL = "/staging/public/stores.json";

    void getStores(ClientCallback callback);

}
