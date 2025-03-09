package com.example.weatherapplicationdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private OnClickListener onClickListener;

    interface OnClickListener{
        void onItemClick(int position);
        void onClickUpdate(int position) throws ExecutionException, InterruptedException;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private ArrayList<Weather> weatherItems;

    public ItemAdapter(ArrayList<Weather> weatherItems) {
        this.weatherItems = weatherItems;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap;
            URL url = null;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return bitmap;
        }
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Weather weather = weatherItems.get(position);
        DownloadImageTask task = new DownloadImageTask();
        Bitmap bitmap;
        try {
            bitmap = task.execute(weather.getImgURL()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        holder.textViewTitle.setText(weather.getTitle());
        holder.textViewInfo.setText(weather.getInfo());
        holder.imageView.setImageBitmap(bitmap);
        holder.cardView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.item_background));
    }

    @Override
    public int getItemCount() {
        return weatherItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewInfo;
        private ImageView imageView;
        private CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewInfo = itemView.findViewById(R.id.textViewInfo);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null){
                        onClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onClickListener != null){
                        try {
                            onClickListener.onClickUpdate(getAdapterPosition());
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return true;
                }
            });
        }
    }

    public ArrayList<Weather> getWeatherItems() {
        return weatherItems;
    }
}
