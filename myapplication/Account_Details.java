package com.myapp.finance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Account_Details extends AppCompatActivity {

    Button quit;
    TextView name,id,mobile_number;
    ImageButton profile_button;
    ImageView profile_pic;
    final int IMAGE_CODE = 1;
    Uri image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
        String u_id="",Name="",mob_num="";

        Intent in = getIntent();
        String data = in.getStringExtra("Jsondata");

        System.out.println("Account Details:"+data);

        name = findViewById(R.id.Name);
        id = findViewById(R.id.U_id);
        mobile_number = findViewById(R.id.Mobile_number);
        quit = findViewById(R.id.quit);
        profile_pic = findViewById(R.id.profile_pic);
        profile_button = findViewById(R.id.profile_pic_button);

        profile_button.setOnClickListener((v)-> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,IMAGE_CODE);
        });

        try {
            JSONArray jrr = new JSONArray(data);
            for(int i=0;i<jrr.length();i++){
                JSONObject arr = jrr.getJSONObject(i);
                u_id = arr.getString("id");
                Name = arr.getString("Username");
                mob_num = arr.getString("Mobile_Number");
                id.setText(u_id);
                name.setText(Name);
                mobile_number.setText(mob_num);
            }
        } catch (JSONException e) {
            new sql(this).show("Error",e.toString(),"Ok");
        }

        quit.setOnClickListener((v)->{
            finish();
        });
    }

    public void onBackPressed(){
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_CODE && resultCode==RESULT_OK && null!=data){ //null!=data Can we give condition like this?
            try {
                image = data.getData();
                ImageDecoder.Source profile_picture = ImageDecoder.createSource(this.getContentResolver(), image);
                Bitmap bitmap = ImageDecoder.decodeBitmap(profile_picture);
                String directory_name = sql.getData("User_name",this);
                String file_name = directory_name;
                String result = ImageStorage.saveInternalStorage(this,bitmap,directory_name,file_name);
                File result1 = ImageStorage.getImage(this,file_name,directory_name);
                if(result.equals("success")) {
                    String file = result1.getPath();
                    Bitmap bit = BitmapFactory.decodeFile(file);
                    profile_pic.setImageURI(image);
                    new sql(this).show("Success", "Image stored Successfully", "Ok");
                }
                else
                    new sql(this).show("Failed",result,"Ok");

            } catch (IOException e) {
                new sql(this).show("Failed",e.toString(),"Ok");
            }
            catch (Exception ex){
                new sql(this).show("Failed",ex.toString(),"Ok");
            }
        }
    }
}
