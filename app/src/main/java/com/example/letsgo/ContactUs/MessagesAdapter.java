package com.example.letsgo.ContactUs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.letsgo.R;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    List<Message> messageList;
    String Uid,Aid;



    public MessagesAdapter(List<Message> messageList,String Uid,String Aid){
        this.Aid=Aid;
        this.messageList=messageList;
        this.Uid=Uid;
        //notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_row,viewGroup,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

     if(messageList.get(i).getFrom().equals(Uid))
     {
         viewHolder.sender.setVisibility(View.VISIBLE);
         viewHolder.sender.setText(messageList.get(i).getMessage());
         Log.e("message",messageList.get(i).getMessage());


     }

     else{

         viewHolder.receiver.setVisibility(View.VISIBLE);
         //viewHolder.sender.setVisibility(View.GONE);
         viewHolder.receiver.setText(messageList.get(i).getMessage());
         Log.e("message",messageList.get(i).getMessage());

     }

    }

    @Override
    public int getItemCount() {
        if(messageList==null){
            Log.d("null", "getItemCount: ");
            return 0;
        }

        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sender,receiver;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sender=itemView.findViewById(R.id.sender);
            receiver=itemView.findViewById(R.id.receiver);
            sender.setVisibility(View.GONE);
            receiver.setVisibility(View.GONE);

        }
    }
}
