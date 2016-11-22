package enozom.foodcourt.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name = "Store")
public class Store extends Model {

    public final static String STORE_ID = "StoreID";
    public final static String STORE_NAME = "StoreName";
    public final static String STORE_DESC = "StoreDescription";
    public final static String STORE_LOGO = "StoreLogo";

    public Store() {
    }

    @Column(name = STORE_ID)
    @SerializedName(STORE_ID)
    private int serverId;

    @Column(name = STORE_NAME)
    @SerializedName(STORE_NAME)
    private String name;

    @Column(name = STORE_DESC)
    @SerializedName(STORE_DESC)
    private String description;

    @Column(name = STORE_LOGO)
    @SerializedName(STORE_LOGO)
    private String logo;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
