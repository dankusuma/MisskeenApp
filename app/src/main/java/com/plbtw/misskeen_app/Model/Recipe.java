package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Paulina on 5/22/2017.
 */
public class Recipe {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("procedure")
    private String procedure;
    @SerializedName("portion")
    private String portion;
    @SerializedName("image")
    private String image;
    @SerializedName("totalCalory")
    private String totalCalory;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("ingredients")
    private List<IngredientObject> ingredients;

    public Recipe(String name, String description, String procedure, String portion,List<IngredientObject> ingredients, String Image)
    {
        this.name = name;
        this.description = description;
        this.procedure = procedure;
        this.portion = portion;
        this.ingredients = ingredients;
        this.image = image;
    }
    public Recipe(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    public Recipe(String id,String name, String description,String procedure, String portion)
    {
        this.id=id;
        this.name = name;
        this.procedure=procedure;
        this.portion=portion;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPortion() {
        return portion;
    }

    public String getProcedure() {
        return procedure;
    }

    public List<IngredientObject> getIngredients() {
        return ingredients;
    }


    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
