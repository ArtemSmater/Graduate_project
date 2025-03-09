package com.example.mymoviesdatabase.adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviesdatabase.R;
import com.example.mymoviesdatabase.room.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> trailers;
    private OnVideoClickListener onVideoClickListener;
    public interface OnVideoClickListener{
        void onVideoClick(String url);
    }
    public void setOnVideoClickListener(OnVideoClickListener onVideoClickListener) {
        this.onVideoClickListener = onVideoClickListener;
    }
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.textViewTrailer.setText(trailer.getTrailerName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewTrailer;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailer = itemView.findViewById(R.id.textViewVideoTitle);
            ImageView imageView = itemView.findViewById(R.id.imageViewPlay);
            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = itemView.getResources().getDrawable(R.drawable.youtube);
            imageView.setImageDrawable(drawable);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onVideoClickListener != null){
                        onVideoClickListener.onVideoClick(trailers.get(getAdapterPosition()).getVideoKey());
                    }
                }
            });
        }
    }
}
