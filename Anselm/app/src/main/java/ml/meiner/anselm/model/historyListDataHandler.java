package ml.meiner.anselm.model;

import android.text.Html;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ml.meiner.anselm.DataBase.Booking;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.R;

public class historyListDataHandler extends RecyclerView.Adapter<historyListDataHandler.MyViewHolder> {

    private ArrayList<Booking> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView; //Element to manipulate
        public ImageView imageView; //Element to manipulate
        public MyViewHolder(ConstraintLayout v)  //Layout around XML
        {
            super(v);
            textView = v.findViewById(R.id.textView3);
            imageView = v.findViewById(R.id.imageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public historyListDataHandler(ArrayList<Booking> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public historyListDataHandler.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Booking booking = mDataset.get(position);
        Picasso.get().load(booking.getUsernamePicturePath()).into(holder.imageView);
        holder.textView.setText("Booked from: " + booking.getUsername() + "\nStation belongs to: " + booking.getStation().getName() + "\nPrice: " + booking.getStation().getPph() + " €/h" + "\nAdress: " + booking.getStation().getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}