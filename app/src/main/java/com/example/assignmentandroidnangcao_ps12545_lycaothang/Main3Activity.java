package com.example.assignmentandroidnangcao_ps12545_lycaothang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main3Activity extends AppCompatActivity {
    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    Button btndangxuat,btnchucnang;
    TextView txtname,txtemail,txtfirstname;
    CallbackManager callbackManager;
    String email,name,firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Gui len sever loi nhan & nhan lai tu app thong qua onActivityResult
        callbackManager=CallbackManager.Factory.create();
        setContentView(R.layout.activity_main3);
        //Goi ham anh xa vao main
        Anhxa();

        //set cac button
        btnchucnang.setVisibility(View.INVISIBLE);
        btndangxuat.setVisibility(View.INVISIBLE);
        txtemail.setVisibility(View.INVISIBLE);
        txtname.setVisibility(View.INVISIBLE);
        txtfirstname.setVisibility(View.INVISIBLE);
        //xin q doc
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        setLogin_Button();
        setLogout_Button();
        ChuyenManHinh();


        //add app fb vao android
        try {
            PackageInfo info = null;
            try {
                info = getPackageManager().getPackageInfo(
                        "com.example.assignmentandroidnangcao_ps12545_lycaothang",//Insert your own package name.
                        PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {

        }
    }

    //Nut button Chuyen activity cac chuc nang
    private void ChuyenManHinh() {
        btnchucnang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main3Activity.this,ManHinhChucNang.class);
                startActivity(intent);
            }
        });

    }

    //Su kien dang xuat button
    private void setLogout_Button() {
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                //INVISIBLE là ẩn đi,VISIBLE là hiện lên
                //mờ đi mà hiện lên phía trước
                btndangxuat.setVisibility(View.INVISIBLE);
                btnchucnang.setVisibility(View.INVISIBLE);
                txtemail.setVisibility(View.INVISIBLE);
                txtname.setVisibility(View.INVISIBLE);
                txtfirstname.setVisibility(View.INVISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                //set text lai de khi dang nhap lai ko su dung json cua tai khoan truoc
                txtemail.setText("");
                txtname.setText("");
                txtfirstname.setText("");
                profilePictureView.setProfileId(null);

            }
        });
    }

    //su kien login button
    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            //Login thanh cong
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                //Visible hien len
                btnchucnang.setVisibility(View.VISIBLE);
                btndangxuat.setVisibility(View.VISIBLE);
                txtemail.setVisibility(View.VISIBLE);
                txtname.setVisibility(View.VISIBLE);
                txtfirstname.setVisibility(View.VISIBLE);
                //lay du lieu ve
                result();

            }
            //Huy
            @Override
            public void onCancel() {

            }
            //Login that bai
            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON",response.getJSONObject().toString());
                try {
                    //Cat chuoi tring khai bao ben tren
                    //lay cac thuoc tinh,goi name,email,firstname tu logcat JSON
                    email= object.getString("email");
                    name=object.getString("name");
                    firstname=object.getString("first_name");
                    //Gan hinh anh cho phan phia tren da khai bao
//                    profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                    profilePictureView.setProfileId("800018260816387");
//                    String userId=object.getString("id");
//                    // lay avatar
//                    profilePictureView.setProfileId(userId);

                    //gan email,name,firstname vao lai textview
                    txtemail.setText(email);
                    txtname.setText(name);
                    txtfirstname.setText(firstname);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Xin quyen cho phep cung cap thong tin
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,first_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    public void Anhxa(){
        profilePictureView=(ProfilePictureView)findViewById(R.id.imageprofilepictureview);
        loginButton=(LoginButton)findViewById(R.id.login_button);
        btnchucnang=(Button)findViewById(R.id.btnchucnang);
        btndangxuat=(Button)findViewById(R.id.buttondangxuat);
        txtemail=(TextView)findViewById(R.id.textviewemail);
        txtname=(TextView)findViewById(R.id.textviewname);
        txtfirstname=(TextView)findViewById(R.id.textviewfirstname);

    }

    //Ham onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
