package com.example.valora_tu_compra_gto;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.app.Activity.*;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    ImageButton imageButton4;
    private static final int CAMERA_REQUEST = 1888;
    ImageView foto;
    private EditText editText, editText2, editText3, editText4;
    private final String ruta_fotos = Environment.getExternalStorageDirectory().getPath() + "/ValoraTuCompra/fotosproductos/";
    private  File file = new File(ruta_fotos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        foto=(ImageView)findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] productos = new String[]{"Select an item...","Ropa","Zapatos","Electronica"};
        final List<String> categoria = new ArrayList<>(Arrays.asList(productos));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categoria) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setError("Selecciona una categoria");
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

    imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        file.mkdirs();
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filess = ruta_fotos + getCode()+".jpg";
                String Ruta = filess.toString();
                File mi_foto= new File(filess);
                try {
                    mi_foto.createNewFile();

                }
                catch (IOException ex)
                {
                    Log.e("ERROR", "ERROR:" + ex);
                }
                 String uri = Uri.fromFile(mi_foto).toString();
                Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(cameraintent,CAMERA_REQUEST);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            loadImageFromFile();
            Uri selectedImageUri = data.getData();
            String uri = getRealPathFromURI(selectedImageUri);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource();
        }
    }

        public String getRealPathFromURI(Uri uri) {
            String[] projection = { MediaStore.Images.Media.DATA };
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

    public void loadImageFromFile(){
        foto.setVisibility(View.VISIBLE);
        int targetW = foto.getWidth();
        int targetH = foto.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(String.valueOf(file), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(file), bmOptions);
        foto.setImageBitmap(bitmap);
    }

    public void altas(View view){

        SQLiteOpenHelper pt = new SQLiteHelper(this, "DBproducto", null,1);
        SQLiteDatabase db = pt.getWritableDatabase();
        String nombre = editText.getText().toString();
        String precio = editText2.getText().toString();
        String lugar = editText3.getText().toString();
        String descripcion= editText4.getText().toString();
        String categoria =String.valueOf(spinner.getSelectedItem());
        String fotos = Uri.fromFile(file).toString();
        if (TextUtils.isEmpty(nombre)){
            editText.setError("Obligatorio");
        }
        if (TextUtils.isEmpty(precio)){
            editText.setError("Obligatorio");
        }
        if (TextUtils.isEmpty(lugar)){
            editText.setError("Obligatorio");
        }
        if (TextUtils.isEmpty(descripcion)){
            editText.setError("Obligatorio");
        }

        else {
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("precio", precio);
            registro.put("lugar", lugar);
            registro.put("descripcion", descripcion);
            registro.put("categoria",categoria);
            registro.put("fotos",fotos);
            db.insert("productos", null, registro);
            db.close();
            editText.setText("");
            editText2.setText("");
            editText3.setText("");
            editText4.setText("");
            Toast.makeText(this, "Se cargaron los datos del producto", Toast.LENGTH_SHORT).show();
        }
    }

@SuppressLint("SimpleDateFormat")
    private String getCode()
{
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyymmddhhmmss");
    String date = dateFormat.format(new Date());
    String photoCode="pic_" + date;
    return photoCode;
}


    @Override
    public void onClick(View v) {

    }
}


