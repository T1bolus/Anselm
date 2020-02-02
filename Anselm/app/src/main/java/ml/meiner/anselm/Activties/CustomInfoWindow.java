package ml.meiner.anselm.Activties;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.R;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindow(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_info_window, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView address_tv = view.findViewById(R.id.address);
        //  ImageView img = view.findViewById(R.id.pic);

        Chargingstation cs = (Chargingstation) marker.getTag();

        name_tv.setText(cs.getName());
        address_tv.setText(cs.getAddress());

        // int imageId = context.getResources().getIdentifier(cs.getImage().toLowerCase(),
        //        "drawable", context.getPackageName());
        // img.setImageResource(imageId);

        return view;
    }
}
