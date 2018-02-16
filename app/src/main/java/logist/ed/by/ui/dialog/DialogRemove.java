package logist.ed.by.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import logist.ed.by.R;

/**
 * Created by Egor on 11.02.2018.
 */

public class DialogRemove {

    public interface RemoveListener{
        void remove();
    }

    public static void show(Context context, RemoveListener listener){
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_remove, null);
        AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();

        view.findViewById(R.id.yesEditText).setOnClickListener(view1 -> {
            if(listener != null){
                listener.remove();
            }
            dialog.dismiss();
        });

        view.findViewById(R.id.noEditText).setOnClickListener(view2 -> {
            dialog.dismiss();
        });

        dialog.show();

    }
}
