package logist.ed.by.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Egor on 11.02.2018.
 */

public class Step {

    @SerializedName("geometry")
    private String geometry;

    @SerializedName("mode")
    private String mode;

    @SerializedName("duration")
    private Integer duration;

    @SerializedName("distance")
    private Integer distance;

    @SerializedName("name")
    private String name;
}
