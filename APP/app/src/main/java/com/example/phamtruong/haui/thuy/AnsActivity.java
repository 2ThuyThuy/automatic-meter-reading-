package com.example.phamtruong.haui.thuy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phamtruong.haui.thuy.R;

import org.json.JSONObject;

import java.io.IOException;

public class AnsActivity extends AppCompatActivity {

    TextView textView;
    Button back;
    ImageView img ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chup);

        textView = findViewById(R.id.text);
        img = findViewById(R.id.img);
        back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnsActivity.this,MainActivity.class));
            }
        });

        Ans ans1 = Data.response.body();
        System.out.println(ans1);
        String ans = Data.response.toString();
//        String ans ="{\"success\": true, \"predict\": {\"ans\": \"06985\", \"position\": \"1122 1735 1641 1853\"}}";
        System.out.println(ans);

        String so_nuoc="";
        int i=0;
        aa:for (i=0;i<ans.length();i++){
            if (ans.charAt(i) >= '0' && ans.charAt(i) <= '9' ){
                so_nuoc+=ans.charAt(i);
                if (ans.charAt(i+1) < '0' || ans.charAt(i+1) > '9' ){
                    break aa;
                }
            }
        }

        textView.setText(so_nuoc);

        System.out.println(ans);

        String toado_s ="";
        bb:for (i=i+1;i<ans.length();i++){
            if (ans.charAt(i) >= '0' && ans.charAt(i) <= '9' ){
                for (int j=i;j<ans.length();j++){
                    if (ans.charAt(j)=='\"'){
                        break bb;
                    }
                    toado_s+=ans.charAt(j);
                }
            }
        }

        System.out.println(toado_s);

        String[] str = toado_s.split(" ");
        Integer[] toa_do = new Integer[4];
        toa_do[0] = Integer.parseInt(str[0]);
        toa_do[1] = Integer.parseInt(str[1]);
        toa_do[2] = Integer.parseInt(str[2]);
        toa_do[3] = Integer.parseInt(str[3]);




        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Data.uri);
            img.setBackground(null);
            img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(img.getWidth() + " " + img.getHeight());
//
//        Bitmap bmp=Bitmap.createBitmap(200,300,Bitmap.Config.RGB_565);
//        Canvas cnvs=new Canvas(bmp);
//        //img.setImageBitmap(bmp);
//
//        Paint paint=new Paint();
//        paint.setColor(Color.RED);
//
//        String[] filePath={MediaStore.Images.Media.DATA};
//        Cursor cur=getContentResolver().query(Data.uri,filePath,null,null,null );
//        cur.moveToFirst();
//        int colIndex=cur.getColumnIndex(filePath[0]);
//        String picPath=cur.getString(colIndex);
//        cur.close();
//        cnvs.drawRect(toa_do[0], toa_do[1],toa_do[2],toa_do[3] , paint);
//
//
//        img.setImageBitmap(bmp);
//        img.setImageBitmap(BitmapFactory.decodeFile(picPath));



    }
}