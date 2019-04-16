package com.example.letsgo.categoryOfPlacePack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.letsgo.HomeFragment.PlaceModel;
import com.example.letsgo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatoegryOfPlaceAdapter extends RecyclerView.Adapter<CatoegryOfPlaceAdapter.ViewHolder> {
    private List<PlaceModel> listOfPlaces;
    private Context context;

    public CatoegryOfPlaceAdapter(List<PlaceModel> listOfPlaces, Context context) {
        this.listOfPlaces = listOfPlaces;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_historical,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PlaceModel item=listOfPlaces.get(i);
        Picasso.get().load(item.getImg().toString()).into(viewHolder.imgPlace);
        if(!item.getImg().toString().equals("")){

            viewHolder.progressBar.setVisibility(View.INVISIBLE);

        }
        viewHolder.namePlace.setText(item.getName().toString());
        viewHolder.duration.setText(item.getDurationTo().toString());
        viewHolder.description.setText(item.getDescription().toString());
        viewHolder.titlePrice.setText(item.getPrice().toString());

    }

    @Override
    public int getItemCount() {
        if (listOfPlaces==null){return 0;}
        return listOfPlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPlace;
        private TextView namePlace;
        private TextView titlePrice;
        private TextView duration;
        private TextView description;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlace=itemView.findViewById(R.id.img);
            namePlace=itemView.findViewById(R.id.nameOfPlaces);
            titlePrice=itemView.findViewById(R.id.title);
            duration=itemView.findViewById(R.id.durationOfPlaces);
            description=itemView.findViewById(R.id.discriptionOfHistoricalPlaces);
            progressBar=itemView.findViewById(R.id.progress_load_photo);
        }
    }
}
