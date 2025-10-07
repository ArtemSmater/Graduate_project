package com.example.dishescollection.api;

import com.example.dishescollection.pojo.DetailMeal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSONObject {

    // json array
    private static final String KEY_MEALS = "meals";

    // json object fields
    private static final String KEY_DISH_NAME = "strMeal";
    private static final String KEY_CATEGORY = "strCategory";
    private static final String KEY_AREA = "strArea";
    private static final String KEY_INSTRUCTION = "strInstructions";
    private static final String KEY_IMAGE = "strMealThumb";
    private static final String KEY_TAGS = "strTags";
    private static final String KEY_INGREDIENT = "strIngredient";
    private static final String KEY_MEASURE = "strMeasure";
    private static final String KEY_VIDEO = "strYoutube";

    public static DetailMeal getDetailMeal(JSONObject jsonObject) {
        DetailMeal result;
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONArray mealArray = jsonObject.getJSONArray(KEY_MEALS);
            JSONObject mealObject = mealArray.getJSONObject(0);

            // parse lines
            String dishName = mealObject.getString(KEY_DISH_NAME);
            String category = mealObject.getString(KEY_CATEGORY);
            String area = mealObject.getString(KEY_AREA);
            String instruction = mealObject.getString(KEY_INSTRUCTION);
            String image = mealObject.getString(KEY_IMAGE);
            String tags = mealObject.getString(KEY_TAGS);
            String video = mealObject.getString(KEY_VIDEO);

            StringBuilder ingredients = new StringBuilder();
            for (int i = 1; i < 20; i++) {
                String key = KEY_INGREDIENT + i;
                String value = mealObject.getString(key);
                if (!value.isEmpty()) ingredients.append(value).append("\n");
                else break;
            }

            StringBuilder measures = new StringBuilder();
            for (int i = 1; i < 20; i++) {
                String key = KEY_MEASURE + i;
                String value = mealObject.getString(key);
                if (!value.isEmpty()) measures.append(value).append("\n");
                else break;
            }

            result = new DetailMeal(dishName, category, area, instruction,
                    image, tags, ingredients.toString().trim(), measures.toString().trim(), video);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
