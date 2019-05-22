package com.example.letsgo.Favorite;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.letsgo.HomeFragment.PlaceModel;
import com.example.letsgo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<PlaceModel> list;

    public ArrayList<PlaceModel> getList() {
        return list;
    }

    public void setList(ArrayList<PlaceModel> list) {
        this.list = list;
    }

    FavoriteAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(FavoriteAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FavoriteAdapter() {
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item,viewGroup,false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        PlaceModel item=list.get(i);
        try {
            Picasso.get().load(item.getImg().toString()).into(viewHolder.img);
            Log.e("FavoriteAdapter", item.getName().toString());
            viewHolder.name.setText(item.getName().toString());
            viewHolder.description.setText(item.getDescription().toString());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(i);
                }
            });

        }catch (Exception e) {
            Log.e("FavoriteAdapter",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        if (list==null)
            return 0;
        return list.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name,description;
         ViewHolder(@NonNull View itemView) {
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
