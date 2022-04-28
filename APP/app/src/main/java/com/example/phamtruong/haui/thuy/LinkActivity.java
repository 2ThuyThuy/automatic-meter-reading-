package com.example.phamtruong.haui.thuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phamtruong.haui.thuy.R;

public class LinkActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText text ;
    Button button;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        text = findViewById(R.id.edit);
        button = findViewById(R.id.add);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LinkActivity.this,MainActivity.class));
            }
        });

        text.setText(sharedPreferences.getString("link",""));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("link",text.getText().toString());
                Data.link = sharedPreferences.getString("link","");
                editor.commit();
            }
        });


    }
}