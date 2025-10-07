package com.example.dishescollection.adapters;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishescollection.R;
import com.example.dishescollection.pojo.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private List<Category> categories;

    public CategoriesAdapter() {
        this.categories = new ArrayList<>();
    }

    // click listener

    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        // image settings
        ImageView ivCategory = holder.ivCategoryItem;
        ivCategory.setClipToOutline(true);
        Picasso mPicasso = Picasso.get();
        mPicasso.load(category.getStrCategoryThumb()).into(ivCategory);

        // text view settings
        TextView tvCategory = holder.tvCategoryItem;
        tvCategory.setText(category.getStrCategory());
        tvCategory.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvCategory.setSingleLine(true);
        tvCategory.setMarqueeRepeatLimit(5);
        tvCategory.setSelected(true);

        holder.cardView.setBackgroundResource(R.drawable.card_background);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivCategoryItem;
        private final TextView tvCategoryItem;
        private final CardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCategoryItem = itemView.findViewById(R.id.ivCategoryItem);
            tvCategoryItem = itemView.findViewById(R.id.tvCategoryItem);
            cardView = itemView.findViewById(R.id.cardViewCategory);

            // set on click listener to view
            itemView.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onItemClick(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
