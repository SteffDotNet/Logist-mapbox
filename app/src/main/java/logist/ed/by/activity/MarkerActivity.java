package logist.ed.by.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import logist.ed.by.R;
import logist.ed.by.mvp.presenter.MarkerPresenter;
import logist.ed.by.mvp.service.MapBoxApiService;
import logist.ed.by.mvp.service.MarkerService;
import logist.ed.by.mvp.view.MarkerIView;

public class MarkerActivity extends MvpAppCompatActivity implements MarkerIView {

    @BindView(R.id.latEditText)
    public EditText latEditText;

    @BindView(R.id.lngEditText)
    public EditText lngEditText;

    @BindView(R.id.titleEditText)
    public EditText titleEditText;

    @BindView(R.id.descriptionEditText)
    public EditText descriptionEditText;

    @BindView(R.id.maToolBar)
    Toolbar toolbar;

    @InjectPresenter
    MarkerPresenter presenter;

    private final static String ACCESS_TOKEN = "pk.eyJ1IjoiZWdvcjk0IiwiYSI6ImNqZDc4cDFjcjE3b3AycXIybnRzMmx3NGoifQ.83ldmjO57BRCSKnnQx2qag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        setViews();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.marker_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Marker marker = MarkerService.getInstance().getCurrentMarker();

        switch (item.getItemId()){
            case R.id.menuDone:
               /* Intent intent = new Intent();
                intent.putExtra("lat", Double.parseDouble(latEditText.getText().toString()));
                intent.putExtra("lng", Double.parseDouble(lngEditText.getText().toString()));
                intent.putExtra("title", titleEditText.getText().toString());
                intent.putExtra("description", descriptionEditText.getText().toString());*/


                LatLng latLng = new LatLng(Double.parseDouble(latEditText.getText().toString()), Double.parseDouble(lngEditText.getText().toString()));

                marker.setPosition(latLng);
                marker.setTitle(titleEditText.getText().toString().trim());
                marker.setSnippet(descriptionEditText.getText().toString().trim());
                presenter.saveMarker(marker);
                MarkerService.getInstance().setCurrentMarker(null);

                setResult(RESULT_OK);
                finish();
                break;

            case android.R.id.home:
                marker.remove();
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViews(){
        latEditText.setText(String.valueOf(MarkerService.getInstance().getCurrentMarker().getPosition().getLatitude()));
        lngEditText.setText(String.valueOf(MarkerService.getInstance().getCurrentMarker().getPosition().getLongitude()));

        String coordinates = MarkerService.getInstance().getCurrentMarker().getPosition().getLatitude() + "," + MarkerService.getInstance().getCurrentMarker().getPosition().getLongitude();
        String type = "address";

        MapBoxApiService.getRetrofit().searchPlace(coordinates, type, ACCESS_TOKEN)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    titleEditText.setText(result.getFeatures().get(0).getAddress());
                }, error ->{
                    titleEditText.setText(error.getMessage());
                });
    }


}
