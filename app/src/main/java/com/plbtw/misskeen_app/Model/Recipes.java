package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulina on 5/24/2017.
 */
public class Recipes {
    @SerializedName("recipe")
    private List<Recipe> recipeObject = new ArrayList<>();

    public List<Recipe> getRecipeObject() {
        return recipeObject;
    }

    public void setRecipeObject(List<Recipe> recipeObject) {
        this.recipeObject = recipeObject;
    }
}
