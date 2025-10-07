package com.example.dishescollection.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishescollection.R;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final String title;

    public VideoAdapter(String title) {
        this.title = title;
    }

    // set on click listener
    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onMovieClick();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.cvVideo.setBackgroundResource(R.drawable.text_detail_background);
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final CardView cvVideo;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitleVideo);
            cvVideo = itemView.findViewById(R.id.cvVideos);
            itemView.setOnClickListener(v -> {
                if(onClickListener != null) {
                    onClickListener.onMovieClick();
                }
            });
        }
    }
}
