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
import com.example.letsgo.HomeFragment.PlacesAdapter;
import com.example.letsgo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatoegryOfPlaceAdapter extends RecyclerView.Adapter<CatoegryOfPlaceAdapter.ViewHolder> {
    private List<PlaceModel> listOfPlaces;
    private Context context;
    private OnItemClickListener onHeartClickListener;
    private OnItemClickListener onItemClickListener;

    //firebase
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference cityRef;

    List<PlaceModel>getListOfPlaces(){
        return listOfPlaces;
    }
     CatoegryOfPlaceAdapter(List<PlaceModel> listOfPlaces, Context context) {
        this.listOfPlaces = listOfPlaces;
        this.context = context;
        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();
        cityRef=FirebaseDatabase.getInstance().getReference().child("cities");
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
     void setOnHeartClickListener(OnItemClickListener onHeartClickListener) {
        this.onHeartClickListener = onHeartClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_historical,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final PlaceModel item=listOfPlaces.get(i);
        Picasso.get().load(item.getImg().toString()).into(viewHolder.imgPlace);
        if(!item.getImg().toString().equals("")){

            viewHolder.progressBar.setVisibility(View.INVISIBLE);

        }
        else{
            viewHolder.progressBar.setVisibility(View.VISIBLE);
        }
        cityRef.child(item.getCity().toString()).child(item.getCategory().toString()).child(item.getName().toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(userId).exists()){
                                item.setChecked(true);
                                viewHolder.shineButton.setChecked(true);
                            }else
                            {
                                item.setChecked(false);
                                viewHolder.shineButton.setChecked(false);
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        viewHolder.namePlace.setText(item.getName().toString());
        viewHolder.duration.setText("From: "+item.getDurationTo()+" ");
        viewHolder.durationTo.setText("To: "+item.getDurationFrom()+"");

      //  viewHolder.titlePrice.setText(item.getPrice().toString());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(i);
            }
        });
        boolean x=true;

        viewHolder.shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeartClickListener.onClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listOfPlaces==null){return 0;}
        return listOfPlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPlace;
        private TextView namePlace;
        private TextView durationTo;
        private TextView duration;
        private TextView description;
        private ShineButton shineButton;

        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlace=itemView.findViewById(R.id.img);
            namePlace=itemView.findViewById(R.id.nameOfPlaces);
            durationTo=itemView.findViewById(R.id.durationToOfPlaces);
            duration=itemView.findViewById(R.id.durationOfPlaces);
            shineButton = (ShineButton) itemView.findViewById(R.id.po_image2);
           // description=itemView.findViewById(R.id.discriptionOfHistoricalPlaces);
            progressBar=itemView.findViewById(R.id.progress_load_photo);
        }
    }
    public interface OnItemClickListener{
        public void onClick(int position);
    }

}
