package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.assignmentandroidnangcao_ps12545_lycaothang.LoginActivity;

import database.DbHelper;
import model.User;

//import com.example.lt15303_ps12545_lycaothang_assignmentgd1.Lab1btThemActivity;
//import com.example.lt15303_ps12545_lycaothang_assignmentgd1.databse.DbHelper;
//import com.example.lt15303_ps12545_lycaothang_assignmentgd1.model.User;

public class QuanTriDAO {
    DbHelper helper;
    public QuanTriDAO(Context context){
        helper=new DbHelper(context);
    }

    public boolean checkLogin(User user){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cs =db.rawQuery("SELECT * FROM QUANTRI WHERE username=? AND password=?",
                new String[]{user.getUsername(),user.getPassword()});
        if(cs.getCount()<=0){
            return false;
        }
        return true;
    }
    public boolean checkOldPWD(String oldPwd){
        //Lay ra username ->>> check trong db xem pass cua username dung hay ko?
        String pwd= LoginActivity.USER.getPassword();
        if(!oldPwd.equals(pwd)){
            return false;
        }

        return true;
    }

    public boolean updatePwd(User user){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password",user.getPassword());
        int row=db.update("QUANTRI",values,"username=?",new String[]{user.getUsername()});

        return row>0;
    }

}
