package enozom.foodcourt.api.interfaces;

public interface ClientCallback<T> {

    void onServerResultSuccess(T t);

    void onServerResultFailure(String errorMessage);
}