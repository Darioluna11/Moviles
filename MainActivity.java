package com.example.valora_tu_compra_gto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton,imageButton2,imageButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        imageButton2=(ImageButton)findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);
        imageButton3=(ImageButton)findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()) {
            case R.id.imageButton:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.imageButton2:
                Intent intent1 = new Intent(this, Main3Activity.class);
                startActivity(intent1);
                break;
            case R.id.imageButton3:
                Intent intent2 = new Intent(this, Main4Activity.class);
                startActivity(intent2);
                break;
        }

}
}
