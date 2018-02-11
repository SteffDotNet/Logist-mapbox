
package logist.ed.by.gson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Trip {

    @SerializedName("distance")
    private Double distance;

    @SerializedName("duration")
    private Double duration;

    @SerializedName("legs")
    private List<Leg> legs = null;

    @SerializedName("geometry")
    private String geometry;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

}
