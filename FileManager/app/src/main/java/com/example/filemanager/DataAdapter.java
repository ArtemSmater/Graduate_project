package com.example.filemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private final Context context;

    @SuppressLint("NotifyDataSetChanged")
    public void setFile(List<File> file) {
        this.file = file;
        notifyDataSetChanged();
    }

    private List<File> file;

    // set click listener
    private OnClickListener clickListener;

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnClickListener {
        void onItemClick(File file);

        void onLongClick(File file, int position);
    }

    public DataAdapter(Context context, List<File> file) {
        this.context = context;
        this.file = file;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(LayoutInflater.from(context).inflate(R.layout.data_view, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        // set file title
        holder.tvTitle.setText(file.get(position).getName());
        holder.tvTitle.setSelected(true);

        // set file size
        int items = 0;
        if (file.get(position).isDirectory()) {
            File[] files = file.get(position).listFiles();
            assert files != null;
            for (File oneFile : files) {
                if (!oneFile.isHidden()) {
                    items++;
                }
            }
            holder.tvType.setText(items + " files");
        } else {
            holder.tvType.setText(Formatter.formatShortFileSize(context, file.get(position).length()));
        }

        // set file image
        if (file.get(position).getName().toLowerCase().endsWith(".jpeg") ||
                file.get(position).getName().toLowerCase().endsWith(".jpg") ||
                file.get(position).getName().toLowerCase().endsWith(".png")) {
            holder.ivType.setImageResource(R.drawable.baseline_image_24);
        } else if (file.get(position).getName().toLowerCase().endsWith(".mp3") ||
                file.get(position).getName().toLowerCase().endsWith(".wav")) {
            holder.ivType.setImageResource(R.drawable.baseline_music_note_24);
        } else if (file.get(position).getName().toLowerCase().endsWith(".pdf")) {
            holder.ivType.setImageResource(R.drawable.baseline_picture_as_pdf_24);
        } else if (file.get(position).getName().toLowerCase().endsWith(".doc")) {
            holder.ivType.setImageResource(R.drawable.baseline_insert_drive_file_24);
        } else if (file.get(position).getName().toLowerCase().endsWith(".mp4")) {
            holder.ivType.setImageResource(R.drawable.baseline_ondemand_video_24);
        } else if (file.get(position).getName().toLowerCase().endsWith(".apk")) {
            holder.ivType.setImageResource(R.drawable.baseline_android_24);
        } else {
            holder.ivType.setImageResource(R.drawable.baseline_folder_24);
        }

        // set card view background
        holder.cardView.setBackgroundResource(R.drawable.item_background);
    }

    @Override
    public int getItemCount() {
        return file.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final ImageView ivType;
        private final TextView tvTitle;
        private final TextView tvType;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            // init views
            cardView = itemView.findViewById(R.id.cardView);
            ivType = itemView.findViewById(R.id.ivDataType);
            tvTitle = itemView.findViewById(R.id.tvDataTitle);
            tvType = itemView.findViewById(R.id.tvDataType);

            // set click listeners
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onItemClick(file.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onLongClick(file.get(getAdapterPosition()), getAdapterPosition());
                }
                return true;
            });
        }
    }
}
