package logist.ed.by.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Egor on 10.02.2018.
 */

public class Feature {
    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("text")
    private String text;

    @SerializedName("place_name")
    private String placeName;

    @SerializedName("address")
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
