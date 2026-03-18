package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ImageActivity extends AppCompatActivity {
    Button btnChooseImage;
    ImageView imageView;
    ActivityResultLauncher<Intent>pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnChooseImage=findViewById(R.id.imageActivity_btn_chooseImage);
        imageView=findViewById(R.id.imageActivity_iv_image);
        pickImageLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result ->{
            if(result.getResultCode()==RESULT_OK&&result.getData()!=null){
                Uri image=result.getData().getData();
                if(image!=null){
                    try {
                        imageView.setImageURI(image);
                    }catch (Exception e){
                        Toast.makeText(this,"Failed to load,Exception:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
                });
       btnChooseImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openGallery();
           }
           private void openGallery(){
               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                   if (ContextCompat.checkSelfPermission(ImageActivity.this, Manifest.permission.READ_MEDIA_IMAGES)
                       != PackageManager.PERMISSION_GRANTED){
                       ActivityCompat.requestPermissions(ImageActivity.this,
                       new String[]{Manifest.permission.READ_MEDIA_IMAGES},100);
                       return;
                   }
               }
               else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                   if (ContextCompat.checkSelfPermission(ImageActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)
                   !=PackageManager.PERMISSION_GRANTED){
                       ActivityCompat.requestPermissions(ImageActivity.this,
                               new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                       return;
                   }
               }
               LaunchGalleryIntent();
           }
           public void LaunchGalleryIntent(){
               Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               pickImageLauncher.launch(i);
           }
       });
    }
}