package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/22/2017.
 */
public class IngredientObject
{
    @SerializedName("id")
    private int id;
    @SerializedName("ingredient_id")
    private int id_ingredient;
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
        this.setId(id);
        this.setId_ingredient(id);
        this.setNama(name);
    }

    public IngredientObject(int id,String name, String amount, String unit, String description)
    {
        this.setId(id);
        this.setNama(name);
        this.setAmount(amount);
        this.setUnit(unit);
        this.setDescription(description);
    }
    public IngredientObject(int id, String amount, String unit, String description)
    {
        this.setId(id);
        this.setId_ingredient(id);
        this.setAmount(amount);
        this.setUnit(unit);
        this.setDescription(description);
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

    public int getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(int id_ingredient) {
        this.id_ingredient = id_ingredient;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
