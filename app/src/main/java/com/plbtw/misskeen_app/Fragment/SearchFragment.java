package com.plbtw.misskeen_app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plbtw.misskeen_app.Activity.ActivityListSearch;
import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Model.IngredientObject;
import com.plbtw.misskeen_app.Model.Ingredients;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.RecipeDetails;
import com.plbtw.misskeen_app.Rest;
import com.plbtw.misskeen_app.Varconstant;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Paulina on 5/23/2017.
 */
public class SearchFragment extends android.support.v4.app.Fragment {
    private Button btnPlus,btnSearch;
    private LinearLayout dynamicLayout;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> ingredientList = new ArrayList<>();
    private List<Spinner> mSpiner = new ArrayList<>();
    private List<IngredientObject> listid = new ArrayList<>();
    ArrayList<Integer> temp = new ArrayList<>();
    public SearchFragment()
    {}
    public List<IngredientObject> mObject=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_search, container, false);
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        btnPlus = (Button) getView().findViewById(R.id.btnaddIngredient);
        btnSearch=(Button)getView().findViewById(R.id.btnsearch);
        dynamicLayout = (LinearLayout) getView().findViewById(R.id.dynamicLayout);
        getData();
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner = new Spinner(getActivity());
                //todo untuk membuat spinner kaya biasa, isiin adapter
                spinner.setAdapter(adapter);
                dynamicLayout.addView(spinner);
                mSpiner.add(spinner);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Spinner s:mSpiner)
                {
                    int id=findID(s.getSelectedItem().toString());
                    Log.wtf("id from spinner",id+"");
                    IngredientObject ingo = new IngredientObject();
//                    ingo.setId(id);
                    temp.add(id);
                }
                getDataResep();
            }
        });
    }
    public int findID(String mItem)
    {
        int id=0;
        for (IngredientObject i: mObject)
        {
            if(i.getNama().equalsIgnoreCase(mItem))
                id=i.getId();
        }
        return id;
    }
    public void getData() {
        Rest rest = Client.getClient().create(Rest.class);
        Call<List<IngredientObject>> call = rest.getAllIngredients(Varconstant.APIKEY);
        call.enqueue(new Callback<List<IngredientObject>>() {
            @Override
            public void onResponse(Call<List<IngredientObject>> call, Response<List<IngredientObject>> response) {
                Log.wtf("Response size", response.body().size() + "");
                for (int i = 0; i < response.body().size(); i++) {
                    ingredientList.add(response.body().get(i).getNama());
                }
                mObject.addAll(response.body());
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ingredientList);

            }

            @Override
            public void onFailure(Call<List<IngredientObject>> call, Throwable t) {
                Log.d("Error Get Bahan : ", t.toString());
            }
        });
    }
    private void getDataResep() {

        JSONArray jsonArray=new JSONArray(listid);
        //ing.setIngredientObject(listid);
        Rest rest = Client.getClient().create(Rest.class);
        Ingredients ig = new Ingredients();
        final List<RecipeDetails> recipeDetailList = new ArrayList<>();


        ig.setIngredientObject(temp);

        Call<List<Recipe>> call = rest.searchRecipe(ig, Varconstant.APIKEY);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                String resep="";
                for (int i = 0 ; i<response.body().size() ; i++)
                {
                    resep = resep+" "+response.body().get(i).getName()+" ";
                    RecipeDetails r = new RecipeDetails();
                    r.setRecipeid(response.body().get(i).getId());
                    r.setRecipename(response.body().get(i).getName());
                    r.setRecipedescription(response.body().get(i).getDescription());
                    r.setRecipethumbnail(response.body().get(i).getImage());
                    recipeDetailList.add(r);
                }

                SharedPreferences ListRecipeSharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor prefsEditor =  ListRecipeSharedPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(recipeDetailList);
                prefsEditor.putString("recipelist", json);
                prefsEditor.commit();
                Intent intent = new Intent(getContext(),ActivityListSearch.class);
                startActivity(intent);


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

//        JsonArrayRequest recipeReq = new JsonArrayRequest(Request.Method.POST,"http://ditoraharjo.co/misskeen/api/v1/search",jsonArray,
//                new com.android.volley.Response.Listener<JSONArray>() {
//                    public void onResponse(JSONArray response) {
//                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
//
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error Search : ", error.toString());
//                        Toast.makeText(getContext(), error.toString()+"", Toast.LENGTH_LONG).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String> params = new HashMap<String, String>();
//
//                int i=0;
//                for(String object: listid){
//                    params.put("ingredients["+(i++)+"]", object);
//                    // you first send both data with same param name as friendnr[] ....  now send with params friendnr[0],friendnr[1] ..and so on
//                }
//                return params;
//
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//
//                headers.put("api-key", "nN2BVe0vO6t42PO3xCqywJNF2jWZ59");
//                return headers;
//            }
//        };
//        AppController.getInstance().addToRequestQueue(recipeReq);
    }

}
