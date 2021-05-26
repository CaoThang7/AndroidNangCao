package com.example.assignmentandroidnangcao_ps12545_lycaothang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import dao.QuanTriDAO;
import model.User;


public class LoginActivity extends AppCompatActivity {
    LinearLayout ggg;
    public static User USER =null;
    QuanTriDAO qtDao;
    EditText etUsername,etPass;
    Button btDangNhap,btHuy,btnMy;
    CheckBox ckRem;
    SignInButton sign;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ggg = (LinearLayout) findViewById(R.id.ggg);
        ggg.setBackgroundResource(R.drawable.hinhnenlogin);




        qtDao=new QuanTriDAO(LoginActivity.this);
        //Tham chieu
        etUsername= findViewById(R.id.txtUsername);
        etPass= findViewById(R.id.txtPassword);
        btDangNhap= findViewById(R.id.btnDangNhap);
        btHuy= findViewById(R.id.btnHuy);
        ckRem=findViewById(R.id.checkbox);
        loadData();

//       btnMy.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent i=new Intent(Lab1btThemActivity.this,MainActivity.class);
//               startActivity(i);
//           }
//       });


        //Anh xa nut login gmail
        sign=findViewById(R.id.sign_in_button);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;

                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        //Dang nhap button
        btDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=etUsername.getText().toString();
                String pass=etPass.getText().toString();
                boolean check=ckRem.isChecked();
                User user=new User(username,pass);
                if(qtDao.checkLogin(user)){
                    luuTT(username,pass,check);
                    USER =user;
                    Toast.makeText(LoginActivity.this,"Đăng nhập thành công!!!",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(LoginActivity.this,"Đăng nhập thất bại!!!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private  void luuTT(String un,String pwd,boolean check){
        SharedPreferences preferences=getSharedPreferences("thongtin.dat",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if(check){
            editor.putString("username",un);
            editor.putString("password",pwd);
            editor.putBoolean("check",check);
        }else{
            editor.clear();
        }
        editor.commit();
    }

    private void loadData(){
        SharedPreferences pref=getSharedPreferences("thongtin.dat",MODE_PRIVATE);
        boolean check=pref.getBoolean("check",false);
        if(check){
            etUsername.setText(pref.getString("username",""));
            etPass.setText(pref.getString("password",""));
            ckRem.setChecked(check);

        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

}

