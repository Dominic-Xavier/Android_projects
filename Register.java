package com.myapp.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class Register extends AppCompatActivity {

    Button b1, b2;
    TextView t1, t2, t3;
    String s1,s2,s3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        b1 = (Button) findViewById(R.id.create);
        b2 = (Button) findViewById(R.id.cancel);
        t1 = (TextView) findViewById(R.id.user);
        t2 = (TextView) findViewById(R.id.pass);
        t3 = (TextView) findViewById(R.id.repass);
        AlertDialog.Builder b = new AlertDialog.Builder(Register.this);

        b1.setOnClickListener((v) -> {
            String s1 = t1.getText().toString();
            String s2 = t2.getText().toString();
            String s3 = t3.getText().toString();


                if (!s2.equals(s3) || s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                    b.setMessage("Password mismatch or Empty values")
                            .setPositiveButton("Ok", null);
                    AlertDialog al = b.create();
                    al.show();
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                } else {
                        String url = "http://192.168.1.9/register.php";
                        new Background(this).execute(s1,s2,url);
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