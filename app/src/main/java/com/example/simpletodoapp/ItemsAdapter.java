package com.example.simpletodoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//Adapter is responsible for taking data at a particular position and putting it into a viewholder
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
public interface OnLongClickedListener{
    void onItemLongClicked(int position);
}
    List<String> items;
    OnLongClickedListener longClickedListener;

    public ItemsAdapter(List<String> items, OnLongClickedListener longClickListener) {
        this.items = items;
        this.longClickedListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       //Create a new view and wrap it inside a new view holder
        //we are doing so by using a inflator to inflate a view
      View toDoView=  LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);
       //wrap inside a viewholder and return it

        return new ViewHolder(toDoView);
    }
  // responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    //Grab the item at the position
        String item  = items.get(position);
        //Bind the item into the specified view holder
        holder.bind(item);
    }
// tells the rv how many items are inside the class
    @Override
    public int getItemCount() {
        return items.size();
    }

    //container to provide easy access that represent each of the list
  class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItem = itemView.findViewById(android.R.id.text1);
    }
     //update the vhew inside the view holder the data of the string item
        public void bind(String item) {
        tvItem.setText(item);
        tvItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //notify the position that was long pressed
               longClickedListener.onItemLongClicked(getAdapterPosition());
                return true;
            }
        });

       }
    }

}
