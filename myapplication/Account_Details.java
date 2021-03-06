package com.myapp.finance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                    startActivityForResult(intent,IMAGE_CODE);
                }
                else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    new sql(this).show("Permission","You need permission to set the profile pics","ok");
                }
                
            } else {
                startActivityForResult(intent,IMAGE_CODE);
            }

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
            startActivity(new Intent(Account_Details.this,Database.class));
            finish();
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(Account_Details.this,Database.class));
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
                String file_name = profilePicName(directory_name);
                new sql(this).show("Image Name","File name:"+file_name,"ok");
                String result = ImageStorage.profilePic(this,bitmap,directory_name,file_name);
                String PROFILE_PIC_NAME = sql.getData("Profile_Pic",this);
                Toast.makeText(this,"Image name is:"+PROFILE_PIC_NAME,Toast.LENGTH_LONG).show();
                File result1 = ImageStorage.getImage(this,directory_name,PROFILE_PIC_NAME);
                if(result.equals("success")) {
                    String name = result1.getAbsolutePath();
                    new sql(this).show("File path","Image name is:"+PROFILE_PIC_NAME,"ok");
                    profile_pic.setImageURI(Uri.parse(new File(name).toString()));
                    new sql(this).show("File object","File:"+new File(name).toString(),"ok");
                    new sql(this).show("Success", "Image stored Successfully", "Ok");
                }
                else
                    new sql(this).show("Failed",result,"Ok");

            } catch (IOException io) {
                new sql(this).show("Failed",io.toString(),"Ok");
            }
            catch (Exception ex){
                new sql(this).show("Failed",ex.toString(),"Ok");
            }
        }
    }

    public String profilePicName(String Image_Name){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000);
        String file_name = Image_Name+rand_int1;
        return file_name;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String TAG = "Permsission : ";
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            //Define the path you want
            File mFolder = new File(Environment.getExternalStorageDirectory(), "Folder_Name");
            if (!mFolder.exists()) {
                boolean b = mFolder.mkdirs();
            }
        }
    }
}
