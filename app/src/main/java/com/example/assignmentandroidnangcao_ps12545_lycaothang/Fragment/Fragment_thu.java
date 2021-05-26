package com.example.assignmentandroidnangcao_ps12545_lycaothang.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentandroidnangcao_ps12545_lycaothang.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adapter.khoahocAdapter;
import dao.KhoaHocDAO;
import model.KhoaHoc;

public class Fragment_thu extends Fragment {
    FloatingActionButton btInsert;
    RecyclerView rcv;
    khoahocAdapter adapter;
    ArrayList<KhoaHoc> list;
    KhoaHocDAO dao;
    KhoaHoc khoaHoc;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    ImageView setLichone,setLichtwo;
    DatePickerDialog datePickerDialog;
    Calendar lich=Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thu,container,false);
        rcv = view.findViewById(R.id.rcvkhoahoc);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        list=KhoaHocDAO.readAll(getContext());
        adapter = new khoahocAdapter(getActivity(), list);
        rcv.setAdapter(adapter);



        btInsert=view.findViewById(R.id.btnInsert);

        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext()," Thanh Cong!!!",
//                        Toast.LENGTH_LONG).show();
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.insert_khoahoc);
                final EditText tilTen=dialog.findViewById(R.id.edtmonhoc);
                final EditText tilGiangvien=dialog.findViewById(R.id.edtgiangvien);
                final EditText tilNgay=dialog.findViewById(R.id.edtngaybd);
                final EditText tilNgayy=dialog.findViewById(R.id.edtngaykt);

                Button btnThem=dialog.findViewById(R.id.btnThem);
                Button btnHuy=dialog.findViewById(R.id.btnHuy);
//                 final ImageView setNgay=dialog.findViewById(R.id.ggg);
//                final Button setNgay=dialog.findViewById(R.id.ggg);


                setLichone=dialog.findViewById(R.id.datengaybd);
                setLichtwo=dialog.findViewById(R.id.datengaykt);








                setLichone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


//                Toast.makeText(TimePickerDialogActivity.this,dayOfMonth + "/" +(monthOfYear+1) + "/" +year
//                        ,Toast.LENGTH_SHORT).show();



//                             tilNgay.getText().toString();
                                tilNgay.setText(year + "-" +(monthOfYear+1) + "-" +dayOfMonth);

//                timeDiaLog.setText(dayOfMonth+monthOfYear+year);




                            }
                        },
                                lich.get(Calendar.YEAR),
                                lich.get(Calendar.MONTH),
                                lich.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }

                });

                setLichtwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


//                Toast.makeText(TimePickerDialogActivity.this,dayOfMonth + "/" +(monthOfYear+1) + "/" +year
//                        ,Toast.LENGTH_SHORT).show();



//                             tilNgay.getText().toString();
                                tilNgayy.setText(year + "-" +(monthOfYear+1) + "-" +dayOfMonth);

//                timeDiaLog.setText(dayOfMonth+monthOfYear+year);




                            }
                        },
                                lich.get(Calendar.YEAR),
                                lich.get(Calendar.MONTH),
                                lich.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    }

                });

//
////                tilTen.setText("asdas");
////                Date date = new Date();
////                tilNgay.setText(sdf.format(date));
////                tilMoney.setText("21321");
////                tilGhiChu.setText("sads");


                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            String ten=tilTen.getText().toString();
                            String giangvien=tilGiangvien.getText().toString();
                            String ngay=tilNgay.getText().toString();
                            Log.d("asv",ngay);
                            String ngayy=tilNgayy.getText().toString();
//                        int idLoai = (int) spn.getSelectedItem();

//                            Log.d("asv",String.valueOf(idLoai));
                            khoaHoc = new KhoaHoc(ten,giangvien,sdf.parse(ngay),sdf.parse(ngayy));
                            KhoaHocDAO khoaHocDAO = new KhoaHocDAO();
                            if(khoaHocDAO.create(getContext(),khoaHoc)){
                                Toast.makeText(getContext(),
                                        "Thêm thành công!!!!!!", Toast.LENGTH_LONG).show();
                                list.clear();

                                list.addAll(KhoaHocDAO.getAll(getContext()));
//                                Toast.makeText(getContext(),
//                                        "Thất bại!!!!!!"+list.get(1).getTien(), Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(getContext(),
                                        "Thất bại!!!!!!", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception ex){
                            Toast.makeText(getContext(),
                                    "Lỗi!!!!!!", Toast.LENGTH_LONG).show();
                            ex.printStackTrace();
                        }

                    }
                });
                dialog.setCancelable(false);
                dialog.show();

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });


        return view;
    }
}
