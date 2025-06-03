package com.example.fragmenthomework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private final ArrayList<Car> cars;

    public CarAdapter(ArrayList<Car> cars) {
        this.cars = cars;
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onNoteClick(int position);
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.ivCar.setImageResource(car.getImgRes());
        holder.tvModel.setText(car.getTitle());
        holder.cardView.setBackgroundResource(R.drawable.item_background);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivCar;
        private final TextView tvModel;
        private final CardView cardView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCar = itemView.findViewById(R.id.ivCar);
            tvModel = itemView.findViewById(R.id.tvCarModel);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(v -> {
                if(onClickListener != null) {
                    onClickListener.onNoteClick(getAdapterPosition());
                }
            });
        }
    }
}
