package com.example.assignmentandroidnangcao_ps12545_lycaothang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dao.QuanTriDAO;
import model.User;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText oldPwd,newPwd,rePwd;
    Button btDmk,btHuydoimk;
    QuanTriDAO qtDao;
    LinearLayout doimk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        doimk=findViewById(R.id.doimk);
        doimk.setBackgroundResource(R.drawable.doimatkhau);
        qtDao=new QuanTriDAO(DoiMatKhauActivity.this);

        oldPwd=findViewById(R.id.oldPwd);
        newPwd=findViewById(R.id.newPwd);
        rePwd=findViewById(R.id.rePwd);
        btDmk=findViewById(R.id.btDmk);
        btHuydoimk=findViewById(R.id.btnHuydoimk);

        btDmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old= oldPwd.getText().toString();
                String nPwd= newPwd.getText().toString();
                String re= rePwd.getText().toString();
                String un= LoginActivity.USER.getUsername();
                if(!qtDao.checkOldPWD(old)){
                    Toast.makeText(DoiMatKhauActivity.this,"Mật khẩu cũ không đúng!!!",Toast.LENGTH_SHORT).show();
                }else{
                    if(!nPwd.equals(re)){
                        Toast.makeText(DoiMatKhauActivity.this,"Mật khẩu nhập lại phải trùng mật khẩu mới!!!",Toast.LENGTH_SHORT).show();
                    }else{
                        if(qtDao.updatePwd(new User(un,nPwd))){
                            Toast.makeText(DoiMatKhauActivity.this,"Cập nhật thành công!!!",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(DoiMatKhauActivity.this,MainActivity.class);
                            startActivity(i);

                            LoginActivity.USER.setPassword(nPwd);
                        }else{
                            Toast.makeText(DoiMatKhauActivity.this,"Thất bại!!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        btHuydoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DoiMatKhauActivity.this,MainActivity.class);
                startActivity(i);
            }
        });



    }
}
