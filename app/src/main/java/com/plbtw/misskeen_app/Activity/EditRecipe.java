package com.plbtw.misskeen_app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.plbtw.misskeen_app.Client;
import com.plbtw.misskeen_app.Model.IngredientObject;
import com.plbtw.misskeen_app.Model.Recipe;
import com.plbtw.misskeen_app.R;
import com.plbtw.misskeen_app.Rest;
import com.plbtw.misskeen_app.Varconstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plbtw.misskeen_app.R.id.dynamicLayout;

/**
 * Created by Paulina on 5/23/2017.
 */
public class EditRecipe extends AppCompatActivity {
    private EditText namaR;
    private EditText deskripsiR;
    private EditText caraR;
    private EditText porsiR;
    private EditText jumlahB;
    private EditText satuanB;
    private EditText deskripsiB;

    private Spinner bahanR;
    private Button btnBahan;
    private Button btnSimpan;
    private ImageView btnTambahGambar;
    private ArrayList<String> ingredientList=new ArrayList<>();
    private List<Spinner> mSpiner = new ArrayList<>();
    private List<EditText> mEdjml = new ArrayList<>();
    private List<EditText> mEdsat = new ArrayList<>();
    private List<EditText> mEddes = new ArrayList<>();
    public List<IngredientObject> mObject=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private  Bundle extras;
    private String recipeid;
    private LinearLayout dynamicLayout;
    private LayoutInflater inflater;
    private String apikey = "nN2BVe0vO6t42PO3xCqywJNF2jWZ59";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        namaR = (EditText) findViewById(R.id.etNama);
        deskripsiR = (EditText) findViewById(R.id.etDescription);
        bahanR = (Spinner) findViewById(R.id.etIngredient);
        caraR = (EditText) findViewById(R.id.etCooking);
        porsiR = (EditText) findViewById(R.id.etPortion);
/*        jumlahB = (EditText) findViewById(R.id.etAmount);
        satuanB = (EditText) findViewById(R.id.etSatuan);
        deskripsiB = (EditText) findViewById(R.id.etDeskripsiB);*/

        btnBahan = (Button) findViewById(R.id.btnaddIngredient);
        btnSimpan = (Button) findViewById(R.id.btnSubmit);
        btnTambahGambar = (ImageView) findViewById(R.id.ivAdd);
        dynamicLayout = (LinearLayout) findViewById(R.id.dynamicLayout);
        extras = new Bundle();
        extras = getIntent().getExtras();
        recipeid=extras.getString("recipeid");
        namaR.setText(extras.getString("name"));
        deskripsiR.setText(extras.getString("deskripsi"));
        caraR.setText(extras.getString("cara"));
//        bahanR.setextras.getString("bahan");
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
        btnBahan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //todo untuk membuat spinner kaya biasa, isiin adapter

                        inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View item = getLayoutInflater().inflate(R.layout.custom_create,null);
                        Spinner spinneritem = (Spinner) item.findViewById(R.id.spinner);
                        EditText edJml = (EditText)item.findViewById(R.id.jumlah);
                        EditText edSat = (EditText)item.findViewById(R.id.satuan);
                        EditText edDes = (EditText)item.findViewById(R.id.deskripsi);
                        spinneritem.setAdapter(adapter);
                        dynamicLayout.addView(item);
                        mSpiner.add(spinneritem);
                        mEdjml.add(edJml);
                        mEdsat.add(edSat);
                        mEddes.add(edDes);
                    }
                }
        );
        getData();

    }

    public void setmSpiner()
    {
        ArrayList<String> listbahan = getIntent().getStringArrayListExtra("listbahan");
        ArrayList<String> listjumlah = getIntent().getStringArrayListExtra("listjumlah");
        ArrayList<String> listsatuan = getIntent().getStringArrayListExtra("listsatuan");
        ArrayList<String> listdeskripsi = getIntent().getStringArrayListExtra("listdeskripsi");
        for (int i=0;i<listbahan.size();i++)
        {
            inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item = getLayoutInflater().inflate(R.layout.custom_create,null);
            Spinner spinneritem = (Spinner) item.findViewById(R.id.spinner);

            EditText edJml = (EditText)item.findViewById(R.id.jumlah);
            EditText edSat = (EditText)item.findViewById(R.id.satuan);
            EditText edDes = (EditText)item.findViewById(R.id.deskripsi);
            spinneritem.setAdapter(adapter);
            dynamicLayout.addView(item);
            mSpiner.add(spinneritem);
            mEdjml.add(edJml);
            mEdsat.add(edSat);
            mEddes.add(edDes);
            for(int j=0;j<ingredientList.size();j++)
            {
                if(listbahan.get(i).equalsIgnoreCase(ingredientList.get(j)))
                {
                    spinneritem.setSelection(j);
                    edJml.setText(listjumlah.get(i));
                    edSat.setText(listsatuan.get(i));
                    edDes.setText(listdeskripsi.get(i));
                }
            }
        }

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
                    IngredientObject object = new IngredientObject(response.body().get(i).getId(),response.body().get(i).getNama());
                    mObject.add(object);
                }



                adapter = new ArrayAdapter<String>(EditRecipe.this, android.R.layout.simple_spinner_dropdown_item, ingredientList);
                setmSpiner();

            }





            @Override
            public void onFailure(Call<List<IngredientObject>> call, Throwable t) {
                Log.d("Error Get Bahan : ", t.toString());
            }
        });
    }

    public void simpan()
    {
        Recipe recipe = new Recipe(recipeid,namaR.getText().toString(), deskripsiR.getText().toString(), caraR.getText().toString(), porsiR.getText().toString());
        Rest rest = Client.getClient().create(Rest.class);
        Call<Recipe> call = rest.editRecipe(recipe, Varconstant.APIKEY);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditRecipe.this);
                alertDialogBuilder.setTitle("Berhasil!");
                alertDialogBuilder.setMessage("Resep berhasil disimpan");
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
}
