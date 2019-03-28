package com.example.letsgo.HomeFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.letsgo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    ArrayList<PlaceModel> list;
    CategoriesAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CategoriesAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PlacesAdapter(ArrayList<PlaceModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item,viewGroup,false);
        return new PlacesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
      PlaceModel item=list.get(i);
        Picasso.get().load(item.getImg().toString()).into(viewHolder.img);
        viewHolder.name.setText(item.getName().toString());
        viewHolder.description.setText(item.getDescription().toString());
    }

    @Override
    public int getItemCount() {
        if (list==null)
            return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.place_item_image);
            name=itemView.findViewById(R.id.place_item_name);
            description=itemView.findViewById(R.id.place_item_description);
        }
    }

    public interface OnItemClickListener{
        public void onClick(int position);
    }
}
