package logist.ed.by.gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Egor on 03.02.2018.
 */

public class Waypoint {

    @SerializedName("location")
    private List<Double> location = null;

    @SerializedName("waypoint_index")
    private Integer waypointIndex;

    @SerializedName("name")
    private String name;

    @SerializedName("trips_index")
    private Integer tripsIndex;

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Integer getWaypointIndex() {
        return waypointIndex;
    }

    public void setWaypointIndex(Integer waypointIndex) {
        this.waypointIndex = waypointIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTripsIndex() {
        return tripsIndex;
    }

    public void setTripsIndex(Integer tripsIndex) {
        this.tripsIndex = tripsIndex;
    }
}