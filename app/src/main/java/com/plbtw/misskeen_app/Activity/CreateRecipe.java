package com.plbtw.misskeen_app.Activity;

import android.content.Context;
import android.content.DialogInterface;
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

public class CreateRecipe extends AppCompatActivity {
    private EditText namaR;
    private EditText deskripsiR;
    private EditText caraR;
    private EditText porsiR;
    private EditText jumlahB;
    private EditText satuanB;
    private EditText deskripsiB;
    private LinearLayout dynamicLayout;
    private LayoutInflater inflater;
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
    ArrayList<Integer> temp = new ArrayList<>();
    ArrayList<String> tempnama = new ArrayList<>();
    ArrayList<String> tempjml = new ArrayList<>();
    ArrayList<String> tempsat = new ArrayList<>();
    ArrayList<String> tempdes = new ArrayList<>();
    private ArrayAdapter<String> adapter;

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

        satuanB = (EditText) findViewById(R.id.etSatuan);

        dynamicLayout = (LinearLayout) findViewById(R.id.dynamicLayout);

        btnBahan = (Button) findViewById(R.id.btnaddIngredient);
        btnSimpan = (Button) findViewById(R.id.btnSubmit);
        btnTambahGambar = (ImageView) findViewById(R.id.ivAdd);
        btnTambahGambar.setVisibility(View.GONE);

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
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSimpan(v);
            }
        });
        getData();
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
                adapter = new ArrayAdapter<String>(CreateRecipe.this, android.R.layout.simple_spinner_dropdown_item, ingredientList);

            }

            @Override
            public void onFailure(Call<List<IngredientObject>> call, Throwable t) {
                Log.d("Error Get Bahan : ", t.toString());
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

    public void buttonSimpan(View v)
    {
        for(Spinner s:mSpiner)
        {
            int id=findID(s.getSelectedItem().toString());
            Log.wtf("id from spinner",id+"");
            temp.add(id);
            String nama = s.getSelectedItem().toString();
            tempnama.add(nama);
        }
        for(EditText edjml:mEdjml)
        {
            String textjml = edjml.getText().toString();
            tempjml.add(textjml);
        }
        for(EditText edsat:mEdsat)
        {
            String textsat = edsat.getText().toString();
            tempsat.add(textsat);
        }
        for(EditText eddes:mEddes)
        {
            String textdes = eddes.getText().toString();
            tempdes.add(textdes);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateRecipe.this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder.setMessage("Yakin akan menyimpan resep ini?");
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Rest rest = Client.getClient().create(Rest.class);

                for (int i=0;i<tempjml.size();i++)
                {
                    int id = temp.get(i);
                    String jml = tempjml.get(i);
                    String sat = tempsat.get(i);
                    String des = tempdes.get(i);
                    //String nama = tempnama.get(i);
                    IngredientObject object = new IngredientObject(id,jml,sat,des);
                    object.setId(id);
                    mObject.add(object);
                }

                //Recipe recipe = new Recipe("Nasi Goreng", "Nasi goreng kecap, mudah, cepat, enak", "1. Panaskan minyak 2. Masukkan nasi putih 3. Tambahkan kecap dan daging ayam", "1", mObject , null);

                Recipe recipe = new Recipe(namaR.getText().toString(), deskripsiR.getText().toString(), caraR.getText().toString(), porsiR.getText().toString(),mObject, "");
                Call<Recipe> call = rest.createRecipe(recipe,Varconstant.APIKEY);
                call.enqueue(new Callback<Recipe>() {
                    @Override
                    public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                        //tersimpan
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateRecipe.this);
                        alertDialogBuilder.setTitle("Berhasil!");
                        alertDialogBuilder.setMessage("Resep berhasil disimpan");
                        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Recipe> call, Throwable t) {
                        Log.d("Error Create Resep : ", t.toString());
                    }
                });
            }
        });
        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
