package com.example.discountcalculate;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NotesViewHolder> {

    private LinkedHashMap<String, String> values;

    public NoteAdapter(LinkedHashMap<String, String> values) {
        this.values = values;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        ArrayList<String> keys = new ArrayList<>(values.keySet());
        ArrayList<String> numbers = new ArrayList<>(values.values());
        Note note = new Note(keys.get(position), numbers.get(position));
        String result = String.format(Locale.getDefault(), "%-2d. New price is %-11s Discount is %s",
                position + 1, note.getPrice(), note.getDiscount());
        holder.textView.setText(result);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvNote);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setValues(LinkedHashMap<String, String> notes) {
        this.values = notes;
        notifyDataSetChanged();
    }

}
