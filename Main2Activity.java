package com.example.valora_tu_compra_gto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageButton = (ImageButton) findViewById(R.id.imageButton5);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.imageButton5):
                Intent intent = new Intent(this, Main5Activity.class);
                startActivity(intent);
                break;
        }
    }
}
