package model;

import java.util.Date;

public class KhoaHoc  {
   private int makhoahoc;
   private String tenmonhoc;
   private String giangvien;
   private Date ngaybd;
    private Date ngaykt;



    public KhoaHoc(int makhoahoc, String tenmonhoc, String giangvien, Date ngaybd, Date ngaykt) {
        this.makhoahoc = makhoahoc;
        this.tenmonhoc = tenmonhoc;
        this.giangvien = giangvien;
        this.ngaybd = ngaybd;
        this.ngaykt = ngaykt;
    }

    public KhoaHoc(String ten, String Giangvien, Date parse, Date parse1) {
        tenmonhoc=ten;
        giangvien=Giangvien;
        ngaybd=parse;
        ngaykt=parse1;
    }


    public int getMakhoahoc() {
        return makhoahoc;
    }

    public void setMakhoahoc(int makhoahoc) {
        this.makhoahoc = makhoahoc;
    }

    public String getTenmonhoc() {
        return tenmonhoc;
    }

    public void setTenmonhoc(String tenmonhoc) {
        this.tenmonhoc = tenmonhoc;
    }

    public String getGiangvien() {
        return giangvien;
    }

    public void setGiangvien(String giangvien) {
        this.giangvien = giangvien;
    }

    public Date getNgaybd() {
        return ngaybd;
    }

    public void setNgaybd(Date ngaybd) {
        this.ngaybd = ngaybd;
    }

    public Date getNgaykt() {
        return ngaykt;
    }

    public void setNgaykt(Date ngaykt) {
        this.ngaykt = ngaykt;
    }

}


