package com.plbtw.misskeen_app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Helper.PrefHelper;
import com.plbtw.misskeen_app.Model.User;
import com.plbtw.misskeen_app.Model.UserObject;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.RecipeDetails;
import com.plbtw.misskeen_app.Rest;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Paulina on 5/15/2017.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;

    private Button btnLogin;
    private Button btnRegis;
    private String apikey = "nN2BVe0vO6t42PO3xCqywJNF2jWZ59";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegis = (Button)findViewById(R.id.btnRegis);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("userdata", "");
        UserObject u = gson.fromJson(json, UserObject.class);
        if(u!=null)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

    }

    public void buttonRegister(View v)
    {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    public void buttonLogin(View v)
    {
        if(editUsername.getText().toString().trim().length() == 0 || editPassword.getText().toString().trim().length() == 0)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
            alertDialogBuilder.setTitle("Gagal Login!");
            alertDialogBuilder.setMessage("Harap username dan password diisi");
            alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else
        {
            UserObject user = new UserObject(editUsername.getText().toString(), editPassword.getText().toString());
            Rest rest = Client.getClient().create(Rest.class);
            Call<User> call = rest.getLogin(user, apikey);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    if(response.body().getStatus().equals("true"))
                    {
                        UserObject u = new UserObject();
                        u=response.body().getUser();
                        SharedPreferences ListRecipeSharedPrefs = PreferenceManager
                                .getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor prefsEditor =  ListRecipeSharedPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(u);
                        prefsEditor.putString("userdata", json);
                        prefsEditor.commit();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                    else
                    if(response.body().getStatus().equals("false"))
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertDialogBuilder.setMessage(response.body().getInfo());
                        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    if(isOnline()) {
                        Log.d("Error Login : ", t.toString());
                    }
                    else
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertDialogBuilder.setMessage("Koneksi internet tidak tersedia");
                        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            });

        }
    }
    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
