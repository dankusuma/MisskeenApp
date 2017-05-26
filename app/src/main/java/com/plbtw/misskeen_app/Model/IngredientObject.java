package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/22/2017.
 */
public class IngredientObject
{
    @SerializedName("ingredient_id")
    private int id_ingredient;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String nama;
    @SerializedName("amount")
    private String amount;
    @SerializedName("unit")
    private String unit;
    @SerializedName("description")
    private String description;

    public IngredientObject()
    {
    }
    public IngredientObject(int id,String name)
    {
        this.id_ingredient=id;
        this.nama=name;
    }
    public IngredientObject(int id,String name, String amount, String unit, String description)
    {
        this.id = id;
        this.nama = name;
        this.amount = amount;
        this.unit = unit;
        this.description = description;
    }
    public IngredientObject(int id, String amount, String unit, String description)
    {
        this.id_ingredient = id;
        this.amount = amount;
        this.unit = unit;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }
}
