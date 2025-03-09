package com.example.mymoviesdatabase.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviesdatabase.R;
import com.example.mymoviesdatabase.room.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> reviews;


    @NonNull

    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textViewAuthor.setText( "Username is: " + review.getAuthor());
        holder.textViewContent.setText(review.getContent());
        holder.textViewDate.setText(review.getUpdated());
        holder.cardViewReview.setBackground(holder.itemView.getResources().getDrawable(R.drawable.info_background));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder{

        private final TextView textViewAuthor;
        private final TextView textViewContent;
        private final TextView textViewDate;
        private final CardView cardViewReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            cardViewReview = itemView.findViewById(R.id.cardViewReview);
        }
    }
}
