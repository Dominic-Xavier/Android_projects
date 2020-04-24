package com.myapp.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class Register extends AppCompatActivity {

    Button b1, b2;
    EditText t1, t2, t3,t4;
    String s1,s2,s3,s4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        b1 =  findViewById(R.id.create);
        b2 =  findViewById(R.id.cancel);
        t1 =  findViewById(R.id.user);
        t2 =  findViewById(R.id.pass);
        t3 =  findViewById(R.id.repass);
        t4 =  findViewById(R.id.mob_num);

        b1.setOnClickListener((v) -> {
            s1 = t1.getText().toString();
            s2 = t2.getText().toString();
            s3 = t3.getText().toString();
            s4 = t4.getText().toString();
            int phone_number = s4.length();

                if (!s2.equals(s3) || s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty() || phone_number!=10) {
                    new sql(this).show("Error","Password Mismatch or empty values or Invalid number","Ok");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                } else {
                        String url = "http://192.168.1.9/register.php";
                        new Background(this).execute(s1,s2,s4,url);
                    }
        });

        b2.setOnClickListener((v)-> {
                startActivity(new Intent(Register.this,login.class));
                finish();
            });
    }

    public void onBackPressed() {
        finish();
        startActivity(new Intent(Register.this,login.class));
    }
}