package logist.ed.by.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mapbox.mapboxsdk.annotations.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;
import logist.ed.by.R;
import logist.ed.by.mvp.presenter.MarkerPresenter;
import logist.ed.by.mvp.view.MarkerIView;

public class MarkerActivity extends MvpAppCompatActivity implements MarkerIView {

    @BindView(R.id.titleEditText)
    public EditText titleEditText;
    @BindView(R.id.descriptionEditText)
    public EditText descriptionEditText;
    @BindView(R.id.positionTextView)
    public TextView positionTextView;
    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @InjectPresenter
    MarkerPresenter presenter;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        marker = presenter.getCurrentMarker();
        setViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.marker_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuDone:
                marker.setTitle(titleEditText.getText().toString().trim());
                marker.setSnippet(descriptionEditText.getText().toString().trim());
                presenter.saveMarker(marker);

                setResult(RESULT_OK);
                finish();
                break;

            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViews(){
        positionTextView.setText(marker.getPosition().getLatitude() + " , " + marker.getPosition().getLongitude());

        //String coordinates = MarkerService.getInstance().getCurrentMarker().getPosition().getLongitude() + "," + MarkerService.getInstance().getCurrentMarker().getPosition().getLatitude();
        //String type = "address";

        //presenter.searchPlace(MarkerService.getInstance().getCurrentMarker().getPosition(), descriptionEditText);

    }


}
