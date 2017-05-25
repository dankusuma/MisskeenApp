package com.plbtw.misskeen_app;

import com.plbtw.misskeen_app.Model.IngredientObject;
import com.plbtw.misskeen_app.Model.IngredientSpinner;
import com.plbtw.misskeen_app.Model.Ingredients;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.Model.Recipes;
import com.plbtw.misskeen_app.Model.User;
import com.plbtw.misskeen_app.Model.UserObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Paulina on 5/15/2017.
 */
public interface Rest {
    //    @Headers({
//            "Content-Type : application json",
//            "X-Requested-With : XMLHttpRequest"
//    })
    @POST("user/auth")
    Call<User> getLogin(@Body UserObject user, @Header("api-key") String apikey);
    @POST("user/register")
    Call<User> getRegis(@Body UserObject user, @Header("api-key") String apikey);
    @POST("recipe")
    Call<Recipe> createRecipe(@Body Recipe recipe, @Header("api-key") String apikey);
    @GET("ingredient")
    Call<List<IngredientObject>> getAllIngredients(@Header("api-key") String apikey);
    @PATCH("recipe")
    Call<Recipe> editRecipe(@Body Recipe recipe, @Header("api-key") String apikey);
    @DELETE("recipe")
    Call<Recipe> deleteRecipe(@Query("id") int id, @Header("api-key") String apikey);
    @POST("search")
    Call<List<Recipe>> searchRecipe(@Body Ingredients ingredients, @Header("api-key") String apikey);
}
