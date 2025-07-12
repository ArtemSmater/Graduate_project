package com.example.fragmentshomeworks;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {



    private List<Note> notes;

    @SuppressLint("NotifyDataSetChanged")
    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onNoteClick(int position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);
        return new NoteViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvTitle.setBackgroundResource(R.drawable.tv_background);
        holder.tvDescription.setText(note.getDescription());
        holder.tvTime.setText(note.getTime());
        holder.tvDate.setText(note.getDate());
        holder.bottomInfo.setBackgroundResource(R.drawable.tv_background);
        holder.cardView.setBackgroundResource(R.drawable.item_background);
        int res = R.drawable.baseline_edit_note_24_green;
        if (note.getPriority() == 1) res = R.drawable.baseline_edit_note_24_green;
        if (note.getPriority() == 2) res = R.drawable.baseline_edit_note_24_yellow;
        if (note.getPriority() == 3) res = R.drawable.baseline_edit_note_24_red;
        holder.ivPriority.setImageResource(res);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvDescription;
        private final TextView tvTime;
        private final TextView tvDate;
        private final ImageView ivPriority;
        private final CardView cardView;
        private final ConstraintLayout bottomInfo;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvDescription = itemView.findViewById(R.id.tvNoteDescription);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivPriority = itemView.findViewById(R.id.ivPriority);
            cardView = itemView.findViewById(R.id.cardView);
            bottomInfo = itemView.findViewById(R.id.clUnderInfo);
            itemView.setOnLongClickListener(v -> {
                if(onClickListener != null) {
                    onClickListener.onNoteClick(getAdapterPosition());
                }
                return true;
            });
        }
    }
}
