package com.example.filemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ItemViewHolder> {

    private final List<Option> options;

    public OptionAdapter(List<Option> options) {
        this.options = options;
    }

    private OnClickListener clickListener;

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Option option = options.get(position);
        holder.tvOptionTitle.setText(option.getTitle());
        holder.ivOptionIcon.setImageResource(option.getIcon());
        CardView cardView = holder.cardView;
        cardView.setBackgroundResource(R.color.transparent);
        LinearLayout layout = holder.layout;
        layout.setClickable(true);
        layout.setBackgroundResource(R.drawable.cv_background);

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivOptionIcon;
        private final TextView tvOptionTitle;
        private final LinearLayout layout;
        private final CardView cardView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOptionIcon = itemView.findViewById(R.id.ivOption);
            tvOptionTitle = itemView.findViewById(R.id.tvOption);
            layout = itemView.findViewById(R.id.llOption);
            cardView = itemView.findViewById(R.id.cvOption);

            // set click listener
            layout.setOnClickListener(v -> {
                if(clickListener != null) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
