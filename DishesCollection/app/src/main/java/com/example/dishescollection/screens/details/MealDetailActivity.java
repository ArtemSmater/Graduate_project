package com.example.dishescollection.screens.details;

import static android.view.View.GONE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dishescollection.R;
import com.example.dishescollection.adapters.VideoAdapter;
import com.example.dishescollection.pojo.DetailMeal;
import com.squareup.picasso.Picasso;


public class MealDetailActivity extends AppCompatActivity implements MealDetailView {

    private ImageView ivDetailDish;
    private RecyclerView rvVideos;
    private TextView tvDishName, tvCategory, tvArea,
            tvTagsValue, tvIngredients, tvIngredientsCount,
            tvInstruction, tvTags, tvTutorial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        // init views
        ivDetailDish = findViewById(R.id.imageViewDish);
        ivDetailDish.setClipToOutline(true);
        rvVideos = findViewById(R.id.rvVideos);
        tvDishName = findViewById(R.id.tvDetailDish);
        tvCategory = findViewById(R.id.tvCategoryValue);
        tvArea = findViewById(R.id.tvAreaValue);
        tvTagsValue = findViewById(R.id.tvTagsValue);
        tvIngredients = findViewById(R.id.tvIngredientValue);
        tvIngredientsCount = findViewById(R.id.tvIngredientCount);
        tvInstruction = findViewById(R.id.tvInstructionValue);
        tvTags = findViewById(R.id.tvTags);
        tvTutorial = findViewById(R.id.tvTutorial);

        // video values
        rvVideos.setLayoutManager(new LinearLayoutManager(this));

        // get intent
        Intent intent = getIntent();
        String mealId = intent.getStringExtra("mealId");

        // set values
        MealDetailPresenter presenter = new MealDetailPresenter(this);
        presenter.loadData(mealId);
    }

    @Override
    public void showData(DetailMeal detailMeal) {

        // set image to image view
        Picasso.get().load(detailMeal.getStrMealThumb()).into(ivDetailDish);

        // set text info
        tvDishName.setText(detailMeal.getStrMeal());
        tvCategory.setText(detailMeal.getStrCategory());
        tvArea.setText(detailMeal.getStrArea());
        tvIngredients.setText(detailMeal.getStrIngredient1());
        tvIngredientsCount.setText(detailMeal.getStrMeasure1());
        tvInstruction.setText(detailMeal.getStrInstructions());

        // set video values
        if (detailMeal.getStrYoutube() != null && !detailMeal.getStrYoutube().isEmpty()) {
            VideoAdapter adapter = new VideoAdapter(detailMeal.getStrMeal());
            rvVideos.setAdapter(adapter);
            adapter.setOnClickListener(() -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailMeal.getStrYoutube()));
                startActivity(intent);
            });
        } else {
            tvTutorial.setVisibility(GONE);
            rvVideos.setVisibility(GONE);
        }

        // get tags
        if (!detailMeal.getStrTags().contains("null")) tvTagsValue.setText(detailMeal.getStrTags());
        else {
            tvTags.setVisibility(GONE);
            tvTagsValue.setVisibility(GONE);
        }


    }

    @Override
    public void showError() {
        Toast.makeText(this, "Check internet connection!", Toast.LENGTH_SHORT).show();
    }
}

