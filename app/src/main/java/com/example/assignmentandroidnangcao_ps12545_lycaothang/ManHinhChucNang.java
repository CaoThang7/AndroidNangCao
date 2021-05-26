package com.example.assignmentandroidnangcao_ps12545_lycaothang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Permission;

public class ManHinhChucNang extends AppCompatActivity {
     LinearLayout ggg;
     EditText edttitle,edtdescription,edturl;
     Button btnsharelink,btnshareimg,btnpickvideo,btnsharevideo;
     ImageView imageView;
     VideoView videoView;
     ShareDialog shareDialog;
     ShareLinkContent shareLinkContent;
     public static  int Select_Image=1;
//     public static  int Pick_video=2;
//     Uri selectvideo;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chuc_nang);
        ggg = (LinearLayout) findViewById(R.id.manhinhchucnang);
        ggg.setBackgroundResource(R.drawable.hinhnenlogin);

        //Anh xa cac chuc nang
//        edttitle=findViewById(R.id.edittexttitle);
//        edtdescription=findViewById(R.id.edittextdescription);
        edturl=findViewById(R.id.edittexturl);
        btnsharelink=findViewById(R.id.btnShareLink);
//        btnpickvideo=findViewById(R.id.btnPick);
//        btnsharevideo=findViewById(R.id.buttonsharevideo);
        btnshareimg=findViewById(R.id.buttonshareimg);
        imageView=findViewById(R.id.img);
//        videoView=findViewById(R.id.videoView);

        shareDialog=new ShareDialog(ManHinhChucNang.this);
        btnsharelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareLinkContent=new ShareLinkContent.Builder()
//                            .setContentTitle(edttitle.getText().toString())
//                            .setContentDescription(edtdescription.getText().toString())
                            .setContentUrl(Uri.parse(edturl.getText().toString()))
                            .build();
                }
                shareDialog.show(shareLinkContent);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Select_Image);
//               Intent intent=new Intent();
//               intent.setType("image/*");
//               intent.setAction(Intent.ACTION_GET_CONTENT);
//               startActivityForResult(Intent.createChooser(intent,"Pick an image"),GALLERY_REQUEST_CODE);
            }
        });

        btnshareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePhoto photo=new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content=new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });

//        btnpickvideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_PICK);
//                intent.setType("video/*");
//                startActivityForResult(intent,Pick_video);
//            }
//        });
//        btnsharevideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ShareVideo shareVideo=null;
////                shareVideo =new ShareVideo.Builder()
////                        .setLocalUrl(selectvideo)
////                        .build();
////                ShareVideoContent content=new ShareVideoContent.Builder()
////                        .setVideo(shareVideo)
////                        .build();
////                shareDialog.show(content);
////                videoView.stopPlayback();
//
//                SharePhoto photo=new SharePhoto.Builder()
//                        .setBitmap(bitmap)
//                        .build();
//                SharePhotoContent content=new SharePhotoContent.Builder()
//                        .addPhoto(photo)
//                        .build();
//                shareDialog.show(content);
//            }
//        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Select_Image && resultCode ==RESULT_OK){
            try {
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                 bitmap= BitmapFactory.decodeStream(inputStream);
                 imageView.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
//        if(requestCode == Pick_video && resultCode == RESULT_OK){
//            selectvideo=data.getData();
//            videoView.setVideoURI(selectvideo);
//            videoView.start();
//        }

        super.onActivityResult(requestCode, resultCode, data);
    }




}
