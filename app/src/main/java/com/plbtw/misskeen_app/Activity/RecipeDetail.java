package com.plbtw.misskeen_app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.RecipeDetails;
import com.plbtw.misskeen_app.Rest;
import com.scalified.fab.ActionButton;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.plbtw.misskeen_app.AppController;
import com.plbtw.misskeen_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class RecipeDetail extends AppCompatActivity {
    private static final String url = "http://ditoraharjo.co/misskeen/api/v1/recipe/".toString().trim();
    private ImageView imageView;
    private TextView txtrecipename,txtrecipedescription,txtrecipesteps,txtrecipeingredients,txtrecipescalories,txtrecipesprice,txtrecipesportion;
    Bundle extras;
    String id;
    private com.github.clans.fab.FloatingActionButton fab, fabdel;

    private String apikey = "nN2BVe0vO6t42PO3xCqywJNF2jWZ59";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        imageView= (ImageView)findViewById(R.id.recipeimagedetail);
        txtrecipename=(TextView)findViewById(R.id.recipedetailname);
        txtrecipedescription=(TextView)findViewById(R.id.recipedetaildescription);
        txtrecipesteps=(TextView)findViewById(R.id.recipesteps);
        txtrecipeingredients=(TextView)findViewById(R.id.recipeingredients);
        txtrecipesprice=(TextView)findViewById(R.id.recipedetailprice);
        txtrecipescalories=(TextView)findViewById(R.id.recipedetailcalories);
        txtrecipesportion=(TextView)findViewById(R.id.recipedetailportion);

        extras = getIntent().getExtras();
        id=extras.getString("recipeid");
        getDataResep();


        fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.resep_edit);
        fabdel = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.resep_delete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Intent i = new Intent(getApplicationContext(), EditRecipe.class);
                b.putString("recipeid",id);
                b.putString("name", txtrecipename.getText().toString());
                b.putString("deskripsi", txtrecipedescription.getText().toString());
                b.putString("cara", txtrecipesteps.getText().toString());
                b.putString("bahan", txtrecipeingredients.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
        });

        fabdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RecipeDetail.this);
                alertDialogBuilder.setTitle("Konfirmasi");
                alertDialogBuilder.setMessage("Anda yakin akan menghapus resep ini?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Rest rest = Client.getClient().create(Rest.class);
                        Call<Recipe> call = rest.deleteRecipe(Integer.parseInt(id), apikey);
                        call.enqueue(new Callback<Recipe>() {
                            @Override
                            public void onResponse(Call<Recipe> call, retrofit2.Response<Recipe> response) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RecipeDetail.this);
                                alertDialogBuilder.setTitle("Berhasil!");
                                alertDialogBuilder.setMessage("Resep berhasil dihapus");
                                alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Recipe> call, Throwable t) {
                                Log.d("Error Edit Resep : ", t.toString());
                            }
                        });
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }


    private void getDataResep() {

        JsonArrayRequest recipeReq = new JsonArrayRequest(url+id,//?api-key=nN2BVe0vO6t42PO3xCqywJNF2jWZ59
            new Response.Listener<JSONArray>() {

                public void onResponse(JSONArray response) {
                    try {
                        JSONObject obj = response.getJSONObject(0);
                        RecipeDetails recipeDetail = new RecipeDetails();
                        recipeDetail.setRecipeid(obj.getString("id"));
                        recipeDetail.setRecipethumbnail(obj.getString("image"));
                        recipeDetail.setRecipedescription(obj.getString("description"));
                        recipeDetail.setRecipename(obj.getString("name"));
                        recipeDetail.setReciperating(obj.getString("rating"));
                        recipeDetail.setTotalCalory(obj.getString("totalCalory"));
                        recipeDetail.setTotalPrice(obj.getString("totalPrice"));
                        recipeDetail.setPortion(obj.getString("portion"));
                        JSONArray ingredients = obj.getJSONArray("ingredients");
                        Picasso
                                .with(RecipeDetail.this)
                                .load(recipeDetail.getRecipethumbnail()).memoryPolicy(MemoryPolicy.NO_CACHE) .networkPolicy(NetworkPolicy.NO_CACHE)
                                .into(imageView);
                        txtrecipename.setText("Name : "+recipeDetail.getRecipename());
                        txtrecipesportion.setText("Portion : "+recipeDetail.getPortion());
                        txtrecipescalories.setText("Calories : "+recipeDetail.getTotalCalory());
                        txtrecipesprice.setText("Price : "+recipeDetail.getTotalPrice());
                        txtrecipedescription.setText("Description : "+recipeDetail.getRecipedescription());
                        txtrecipesteps.setText(obj.getString("procedure"));
                        String bahan;
                        for (int i=0;i<ingredients.length();i++)
                        {
                            txtrecipeingredients.append(ingredients.getJSONObject(i).getString("name")+"\n");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RecipeDetail.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            })
        {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<>();

            headers.put("api-key", "nN2BVe0vO6t42PO3xCqywJNF2jWZ59");
            return headers;
        }
        };
        AppController.getInstance().addToRequestQueue(recipeReq);
    }
}
