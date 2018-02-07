package logist.ed.by.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import logist.ed.by.R;


public class MarkerActivity extends AppCompatActivity {

    @BindView(R.id.latEditText)
    public EditText latEditText;

    @BindView(R.id.lngEditText)
    public EditText lngEditText;

    @BindView(R.id.titleEditText)
    public EditText titleEditText;

    @BindView(R.id.descriptionEditText)
    public EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        ButterKnife.bind(this);
        double lat = getIntent().getDoubleExtra("lat", 0.0);
        double lng = getIntent().getDoubleExtra("lng", 0.0);
        setViews(lat, lng);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.marker_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setViews(double lat, double lng){
        latEditText.setText(String.valueOf(lat));
        lngEditText.setText(String.valueOf(lng));
    }
}
