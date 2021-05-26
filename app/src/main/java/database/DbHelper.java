package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){super(context,"androidnangcao",null,1);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng loại thu chi
        String sql="CREATE TABLE KHOAHOC(makhoahoc integer primary key autoincrement,"+
                "tenmonhoc text, giangvien text,ngaybd Date,ngaykt Date)";
        db.execSQL(sql);
        sql="INSERT INTO KHOAHOC(tenmonhoc,giangvien,ngaybd,ngaykt) VALUES('Android Nâng Cao','kietlpt','2020-09-01','2020-09-30')";
        db.execSQL(sql);
        sql="INSERT INTO KHOAHOC(tenmonhoc,giangvien,ngaybd,ngaykt) VALUES('Dự án mẫu','kietlpt','2020-09-01','2020-09-30')";
        db.execSQL(sql);




        sql="CREATE TABLE QUANTRI(username text primary key, password text)";
        db.execSQL(sql);
        sql="INSERT INTO QUANTRI VALUES('admin','admin')";
        db.execSQL(sql);
        sql="INSERT INTO QUANTRI VALUES('admin2','123')";
        db.execSQL(sql);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists KHOAHOC");
        onCreate(db);

    }
}
