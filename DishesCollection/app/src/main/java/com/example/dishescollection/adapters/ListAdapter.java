package com.example.dishescollection.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishescollection.R;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    // collection objects
    private List<String> items;

    public ListAdapter(List<String> items) {
        this.items = items;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<String> getItems() {
        return items;
    }

    // click listener objects
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.listItem.setText(items.get(position));
        holder.cardViewItem.setBackgroundResource(R.drawable.card_background);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView listItem;
        private final CardView cardViewItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            listItem = itemView.findViewById(R.id.tvListItem);
            cardViewItem = itemView.findViewById(R.id.cardViewList);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(getAbsoluteAdapterPosition());
            });
        }
    }
}
