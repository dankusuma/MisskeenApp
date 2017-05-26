package com.plbtw.misskeen_app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Paulina on 5/22/2017.
 */
public class RecipeDetails implements Parcelable {
    private String recipeid;
    private String recipename;
    private String recipedescription;
    private String reciperating;
    private String recipethumbnail;
    private String totalPrice;
    private String totalCalory;
    private String portion;

    public RecipeDetails() {
    }

    protected RecipeDetails(Parcel in) {
        recipeid = in.readString();
        recipename = in.readString();
        recipedescription = in.readString();
        reciperating = in.readString();
        recipethumbnail = in.readString();
    }

    public static final Creator<RecipeDetails> CREATOR = new Creator<RecipeDetails>() {
        @Override
        public RecipeDetails createFromParcel(Parcel in) {
            return new RecipeDetails(in);
        }

        @Override
        public RecipeDetails[] newArray(int size) {
            return new RecipeDetails[size];
        }
    };

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getRecipedescription() {
        return recipedescription;
    }

    public void setRecipedescription(String recipedescription) {
        this.recipedescription = recipedescription;
    }

    public String getReciperating() {
        return reciperating;
    }

    public void setReciperating(String reciperating) {
        this.reciperating = reciperating;
    }

    public String getRecipethumbnail() {
        return recipethumbnail;
    }

    public void setRecipethumbnail(String recipethumbnail) {
        this.recipethumbnail = "http://ditoraharjo.co/misskeen/uploads/RecipePicture/"+recipethumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(recipeid);
        parcel.writeString(recipename);
        parcel.writeString(recipedescription);
        parcel.writeString(reciperating);
        parcel.writeString(recipethumbnail);
        parcel.writeString(totalCalory);
        parcel.writeString(totalPrice);
        parcel.writeString(portion);
    }

    public String getTotalCalory() {
        return totalCalory;
    }

    public void setTotalCalory(String totalCalory) {
        this.totalCalory = totalCalory;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }
}
