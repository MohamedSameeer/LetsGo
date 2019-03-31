package com.example.letsgo.Place;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.letsgo.R;

import java.util.List;

public class ReviewRecyclerView extends RecyclerView.Adapter<ReviewRecyclerView.ViewHolder> {
    List<ReviewModel>reviewModelList;
    public ReviewRecyclerView(List<ReviewModel> reviewModelList){
        this.reviewModelList=reviewModelList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.place_item,viewGroup,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(reviewModelList.get(i).getUserId());
        viewHolder.description.setText(reviewModelList.get(i).getReview());

    }

    @Override
    public int getItemCount() {
       if(reviewModelList==null){
           return 0;
       }
       return reviewModelList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.place_item_name);
            description=itemView.findViewById(R.id.place_item_description);
        }
    }
}
