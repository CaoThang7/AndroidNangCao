package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.DbHelper;
import model.KhoaHoc;



public class KhoaHocDAO {


    public static ArrayList<KhoaHoc> readAll(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DbHelper helper = new DbHelper(context);
        ArrayList<KhoaHoc> data = new ArrayList<>();


        //Tao databse
        SQLiteDatabase db = helper.getWritableDatabase();
        // Tao con tro = pointer( cursor) de lay du lieu

        Cursor cs=db.rawQuery("select * from KHOAHOC",null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {

            int makhoahoc=cs.getInt(0);
            String tenmonhoc=cs.getString(1);
            String giangvien=cs.getString(2);
            String ngaybd=cs.getString(3);
            String ngaykt=cs.getString(4);

            try {
                data.add(new KhoaHoc(makhoahoc,tenmonhoc,giangvien,sdf.parse(ngaybd),sdf.parse(ngaykt)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cs.moveToNext();

        }
        cs.close();
        return data;
    }
    public static ArrayList<KhoaHoc>getAll(Context context){

        ArrayList<KhoaHoc> ds=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cs=db.rawQuery("select * from KHOAHOC",null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){

            try {
                int mamonhoc = cs.getInt(0);
                String tenmonhoc = cs.getString(1);
                String giangvien = cs.getString(2);
                Date ngay = sdf.parse(cs.getString(3));
                Date ngayy = sdf.parse(cs.getString(4));
                KhoaHoc khoaHoc=new KhoaHoc(mamonhoc,tenmonhoc,giangvien,ngay,ngayy);
                ds.add(khoaHoc);

            }catch (Exception ex){
                ex.printStackTrace();
            }
            cs.moveToNext();

        }
        cs.close();
        return ds;
    }


    //delete
    public static boolean delete(Context context,Integer makhoahoc){
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
        int row=db.delete("KHOAHOC","makhoahoc=?",new String[]{String.valueOf(makhoahoc)});
        return row > 0;
    }
//
//
////    public int insert(KhoanThuChi t){
////
////    }
//
//
////    public static boolean create(Context context,KhoanThuChi tc){
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        DbHelper helper = new DbHelper(context);
////        SQLiteDatabase db=helper.getWritableDatabase();
////        ContentValues values=new ContentValues();
////        values.put("MaTc",tc.getMaTc());
////        values.put("TenKhoanTc",tc.getTenKhoanTc());
////        values.put("Ngay",sdf.format(tc.getNgay()));
////        values.put("Tien",tc.getTien());
////        values.put("GhiChu",tc.getGhiChu());
////        values.put("MaLoai",tc.getMaLoai());
////        long row=db.insert("KHOAN_TC",null,values);
////        return (row >0);
////
////    }
//
    public Boolean create(Context context, KhoaHoc khoaHoc){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
//        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenmonhoc",khoaHoc.getTenmonhoc());
        values.put("giangvien",khoaHoc.getGiangvien());
        values.put("ngaybd",sdf.format(khoaHoc.getNgaybd()));
        values.put("ngaykt",sdf.format(khoaHoc.getNgaykt()));
        db.insert("KHOAHOC",null,values);
        return true;
    }


//Update
    public Boolean update(Context context,KhoaHoc khoaHoc){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
//        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenmonhoc",khoaHoc.getTenmonhoc());
        values.put("giangvien",khoaHoc.getGiangvien());
        values.put("ngaybd",sdf.format(khoaHoc.getNgaybd()));
        values.put("ngaykt",sdf.format(khoaHoc.getNgaykt()));
        db.update("KHOAHOC",values,"makhoahoc=?",new String[]{String.valueOf(khoaHoc.getMakhoahoc())});
        return true;
    }


}
