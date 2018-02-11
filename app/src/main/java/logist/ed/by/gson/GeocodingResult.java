package logist.ed.by.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Egor on 10.02.2018.
 */

public class GeocodingResult {

    @SerializedName("type")
    private String type;

    @SerializedName("features")
    private List<Feature> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
