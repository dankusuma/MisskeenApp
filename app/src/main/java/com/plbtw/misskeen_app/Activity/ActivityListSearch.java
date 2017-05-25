package com.plbtw.misskeen_app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.RecipeDetails;
import com.plbtw.misskeen_app.RecipeListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActivityListSearch extends AppCompatActivity {

    List<RecipeDetails> recipes;
    private ListView listView;
    private RecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipes=new ArrayList<>();
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ActivityListSearch.this);
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("recipelist", "");
            Type type = new TypeToken<List<RecipeDetails>>(){}.getType();
            recipes= gson.fromJson(json, type);
            listView = (ListView) findViewById(R.id.recipelist);
            Toast.makeText(ActivityListSearch.this,recipes.get(0).getRecipename(), Toast.LENGTH_LONG).show();
        adapter = new RecipeListAdapter(ActivityListSearch.this, recipes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipeid = ((TextView) view.findViewById(R.id.recipeid)).getText().toString();
                Intent in = new Intent(ActivityListSearch.this, com.plbtw.misskeen_app.Activity.RecipeDetail.class);
                in.putExtra("recipeid",recipeid);
                startActivity(in);
            }
        });
    }
}
