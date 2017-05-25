package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulina on 5/22/2017.
 */
public class Ingredients {
    @SerializedName("ingredients")
    private List<Integer> ingredientObject = new ArrayList<>();

    public List<Integer> getIngredientObject() {
        return ingredientObject;
    }

    public void setIngredientObject(List<Integer> ingredientObject) {
        this.ingredientObject = ingredientObject;
    }
}


