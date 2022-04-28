package com.example.phamtruong.haui.thuy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phamtruong.haui.thuy.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button chup , load , link;
    SharedPreferences sharedPreferences;

    Uri muri;
//    public static ImageView img;
    private static final int MY_REQUEST_CODE = 10;
    private static final String TAG = MainActivity.class.getName();

    ActivityMainBinding binding;
    int Code_Camera = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        Data.link = sharedPreferences.getString("link","");

        binding.chup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Code_Camera);

//                Intent gal_open=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(gal_open,1);
            }
        });

        binding.load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.link = sharedPreferences.getString("link","");
                Data.uri=muri;
//                startActivity(new Intent(MainActivity.this,AnsActivity.class));

                callAPI_up_AVT();

            }
        });

        binding.chon.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        binding.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LinkActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==Code_Camera && resultCode==RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            binding.img.setImageBitmap(bitmap);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(binding.getRoot().getContext().getContentResolver(), bitmap, "Title", null);
            muri= Uri.parse(path);


            Bitmap bitmap1 = null;
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(binding.getRoot().getContext().getContentResolver(),muri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.img.setBackground(null);
            binding.img.setImageBitmap(bitmap1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //============================================================================================================
    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }


        if (binding.getRoot().getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }


    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select AVT"));
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG,"onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data==null){
                            return;
                        }
                        Uri uri = data.getData();
                        muri=uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(binding.getRoot().getContext().getContentResolver(),uri);
                            binding.img.setBackground(null);
                            binding.img.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

    );

    private void callAPI_up_AVT() {

//        onClickRequestPermission();


        String strRealPath = RealPathUtil.getRealPath(binding.getRoot().getContext(),muri);
        Log.e("Okla",strRealPath);

        File file = new File(strRealPath);

        RequestBody requestBodyimg = RequestBody.create(MediaType.parse("multipart/from-data") , file );
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image" , file.getName() , requestBodyimg);


        Api_Service.apiSever.post_img(multipartBody).enqueue(new Callback<Ans>() {
            @Override
            public void onResponse(Call<Ans> call, Response<Ans> response) {
                Intent intent = new Intent(MainActivity.this,AnsActivity.class);
                System.out.println(response);
                System.out.println(response.toString());
                System.out.println(response.body());
                System.out.println(response.headers());
                System.out.println(response.message());

                System.out.println("ok la1");
                System.out.println(call.toString());
                System.out.println("ok la2");

                Data.response = response;
                Data.uri = muri;
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Ans> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }

        });

    }
}