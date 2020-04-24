package com.myapp.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

@RequiresApi(api = Build.VERSION_CODES.P)
public class Account_Details extends AppCompatActivity {

    Button quit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);

        quit = findViewById(R.id.quit);

        quit.setOnClickListener((v)->{
            finish();
           startActivity(new Intent(Account_Details.this,Database.class));
        });

    }
}
