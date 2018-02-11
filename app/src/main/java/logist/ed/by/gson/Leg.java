
package logist.ed.by.gson;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Leg {

    @SerializedName("duration")
    private Double duration;

    @SerializedName("steps")
    private List<Object> steps;

    @SerializedName("distance")
    private Double distance;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public List<Object> getSteps() {
        return steps;
    }

    public void setSteps(List<Object> steps) {
        this.steps = steps;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
