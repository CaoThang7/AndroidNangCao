package adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignmentandroidnangcao_ps12545_lycaothang.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dao.KhoaHocDAO;
import model.KhoaHoc;


public class    khoahocAdapter extends RecyclerView.Adapter<khoahocAdapter.KhoanTCHolder> {
    Activity context;
    ArrayList<KhoaHoc> list;
    KhoaHocDAO dao;
    RecyclerView rcv;
    khoahocAdapter adapter;
    KhoaHoc khoaHoc;
    EditText ettenmonhoc,etgiangvien,etngaybd,etngaykt;
//    Spinner spnn;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    public khoahocAdapter(Activity context, ArrayList<KhoaHoc>list){
        this.context=context;
        this.list=list;

    }



    @NonNull
    @Override
    public KhoanTCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view=inflater.inflate(R.layout.khoahoc,parent,false);

        return (new KhoanTCHolder(view));

    }

    @Override
    public void onBindViewHolder(@NonNull final KhoanTCHolder holder, final int position) {
        holder.tvtenmonhoc.setText(list.get(position).getTenmonhoc());
        holder.tvgiangvien.setText(list.get(position).getGiangvien());
        holder.tvngaybd.setText(sdf.format(list.get(position).getNgaybd()));
        holder.tvngaykt.setText(sdf.format(list.get(position).getNgaykt()));



        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(position);
            }
        });





        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Integer MaTc =list.get(position).getMakhoahoc();

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắn chắn xoá không?");
                builder.setTitle("Xoá");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(KhoaHocDAO.delete(context,MaTc)){
                            Toast.makeText(context," thành công!!!",
                                    Toast.LENGTH_LONG).show();
                            list.remove(position);
                            notifyDataSetChanged();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(context," thất bại!!!",
                            Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();




//                if(KhoanThuChiDAO.delete(context,MaTc)){
//                    Toast.makeText(context," thành công!!!",
//                            Toast.LENGTH_LONG).show();
//                    list.remove(position);
//                    notifyDataSetChanged();
//
//                }else {
//                    Toast.makeText(context," thất bại!!!",
//                            Toast.LENGTH_LONG).show();
//                }

            }


        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }



    public class KhoanTCHolder extends RecyclerView.ViewHolder{
        TextView tvtenmonhoc,tvgiangvien,tvngaybd,tvngaykt;
        ImageView ivEdit,ivDel;
//        FloatingActionButton btInsert;
        CardView card;
//         RecyclerView card;

        public KhoanTCHolder(@NonNull View item) {
            super(item);
            tvtenmonhoc=item.findViewById(R.id.tvtenmonhoc);
            tvgiangvien=item.findViewById(R.id.tvgiangvien);
            tvngaybd=item.findViewById(R.id.tvngaybd);
            tvngaykt=item.findViewById(R.id.tvngaykt);
            ivEdit=item.findViewById(R.id.btnEdit);
            ivDel=item.findViewById(R.id.btnDel);
//            btInsert=item.findViewById(R.id.fbInsertLop);
            card=item.findViewById(R.id.khoahoc_item);
        }
    }

    public void dialog(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = context.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.edit_khoahoc, null);
        ettenmonhoc = v.findViewById(R.id.ettenmonhoc);
        etgiangvien = v.findViewById(R.id.etgiangvien);

         etngaybd= v.findViewById(R.id.edngaybd);
        etngaykt = v.findViewById(R.id.edngaykt);
//        spnn=v.findViewById(R.id.spn);

        ettenmonhoc.setText(list.get(position).getTenmonhoc());
        etgiangvien.setText(list.get(position).getGiangvien());
        etngaybd.setText(sdf.format(list.get(position).getNgaybd()));
        etngaykt.setText(sdf.format(list.get(position).getNgaykt()));



//        final ArrayAdapter adapterLop = new ArrayAdapter(context,
//                android.R.layout.simple_spinner_dropdown_item,list);
//        spnn.setAdapter(adapterLop);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        try {
                            int mamonhoc = list.get(position).getMakhoahoc();
                            String tenmonhoc = ettenmonhoc.getText().toString();
                            String giangvien = etgiangvien.getText().toString();
                            Date date = sdf.parse(String.valueOf(etngaybd.getText()));
                            Date datee = sdf.parse(String.valueOf(etngaykt.getText()));
//
//                            KhoaHoc tc=(KhoanThuChi) spnn.getSelectedItem();
//                            int maloai=tc.getMaLoai();
//                            spnn.setAdapter(adapterLop);
                            khoaHoc = new KhoaHoc(mamonhoc,tenmonhoc,giangvien,date,datee);
                            KhoaHocDAO khoaHocdao = new KhoaHocDAO();
                            if(khoaHocdao.update(context,khoaHoc)){
                                khoaHoc= new KhoaHoc(mamonhoc,tenmonhoc,giangvien,date,datee);
                                list.set(position,khoaHoc);
                                notifyDataSetChanged();
                                Toast.makeText(context,"Thành công!!!",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(context,"Lỗi ngày",Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();


//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = context.getLayoutInflater();
//        View view = inflater.inflate(R.layout.khoantc_item2, null);
//        builder.setView(view);
//        edtngay = view.findViewById(R.id.tvNgay);
//        edtten = view.findViewById(R.id.tvtenkhoantc);
//        edtmoney = view.findViewById(R.id.tvTien);
//        edtghichu = view.findViewById(R.id.tvGhiChu);
//        Button btThem = view.findViewById(R.id.btnThemSv);
//        rcv = view.findViewById(R.id.rcvKhoanTC);
//        KhoanThuChiDAO daoLH = new KhoanThuChiDAO();
////        ArrayList<KhoanThuChi> dsLop = new ArrayList<>();
//////        dsLop = daoLH.readAll();
//////        ArrayAdapter adapterLop = new ArrayAdapter(SinhVienActivity.this,
//////                android.R.layout.simple_spinner_item, dsLop);
//////        spnLop.setAdapter(adapterLop);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        rcv.setLayoutManager(layoutManager);
//        list = new ArrayList<>();
//
//        list = KhoanThuChiDAO.readAll(context, "Thu");
//        adapter = new KhoanTCRCVAdapter(context, list);
//        rcv.setAdapter(adapter);
//        final AlertDialog dialog = builder.create();
//        btThem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String ten = edtten.getText().toString();
//                String ns = edtngay.getText().toString();
//                String tien = edtmoney.getText().toString();
//                String ghichu = edtghichu.getText().toString();
//
//                try {
//                    int matc = list.get(position).getMaTc();
//                    KhoanThuChi khoanthuchi = new KhoanThuChi(matc,ten, sdf.parse(ns), tien, ghichu);
//                    if(dao.create(context,khoanthuchi)){
//                        Toast.makeText(context,
//                                "Thêm thành công!!!!!!", Toast.LENGTH_LONG).show();
//                        list.clear();
//                        list.addAll(dao.readAll(context, String.valueOf(list)));
//                        adapter.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }else{
//                        Toast.makeText(context,
//                                "Thất bại!!!!!!", Toast.LENGTH_LONG).show();
//                    }
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//
//        dialog.show();


    }

    }

