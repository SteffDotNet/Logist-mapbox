package logist.ed.by.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Egor on 11.02.2018.
 */

public class DirectionResult {
    @SerializedName("code")
    private String code;

    @SerializedName("waypoints")
    private List<Waypoint> waypoints;

    @SerializedName("trips")
    private List<Trip> trips;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
