package com.example.englishwordsapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<SavedItem> items;

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(ArrayList<SavedItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public ItemAdapter(ArrayList<SavedItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SavedItem item = items.get(position);
        String playerName = String.format(Locale.getDefault(), "Name: %s", item.getName());
        String finalRes = String.format(Locale.getDefault(), "Results: %s\nTime: %s",
                item.getPoints(), item.getTime());
        holder.tvPlayerName.setText(playerName);
        holder.tvPlayerResult.setText(finalRes);
        holder.cardView.setBackgroundResource(R.drawable.item_res_background);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPlayerName;
        private TextView tvPlayerResult;
        private CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvPlayerName.setBackgroundResource(R.drawable.title_background);
            tvPlayerResult = itemView.findViewById(R.id.tvPlayerResult);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
