package com.example.thangpnph16377_ass.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thangpnph16377_ass.Adapter.SanPhamAdapter;
import com.example.thangpnph16377_ass.DAO.SanPhamDAO;
import com.example.thangpnph16377_ass.DTO.SanPhamDTO;
import com.example.thangpnph16377_ass.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcsanpham;
    FloatingActionButton fladd;

    SanPhamDAO sanPhamDAO;
    SanPhamAdapter adapter;

    private ArrayList<SanPhamDTO> list = new ArrayList<SanPhamDTO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sanPhamDAO = new SanPhamDAO(this);

        rcsanpham = findViewById(R.id.rcsanpham);
        fladd = findViewById(R.id.fladd);

        list = sanPhamDAO.selectAll();
        adapter = new SanPhamAdapter(this, list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rcsanpham.setLayoutManager(layout);
        rcsanpham.setAdapter(adapter);

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogThem();
            }
        });
    }

    private void openDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText txtTenCV = view.findViewById(R.id.txtTenCV);
        EditText txtNoiDungCV = view.findViewById(R.id.txtNoiDungCV);
        EditText txtTrangThai = view.findViewById(R.id.txtTrangThai);
        EditText txtNgayBatDau = view.findViewById(R.id.txtNgayBatDau);
        EditText txtNgayKetThuc = view.findViewById(R.id.txtNgayKetThuc);
        Button btnThem = view.findViewById(R.id.btnThem);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tencv = txtTenCV.getText().toString();
                String noidungcv = txtNoiDungCV.getText().toString();
                String trangthai = txtTrangThai.getText().toString();
                String ngaybatdau = txtNgayBatDau.getText().toString();
                String ngayketthuc = txtNgayKetThuc.getText().toString();
                if (tencv.trim().isEmpty() || noidungcv.trim().isEmpty() || ngaybatdau.trim().isEmpty() || ngayketthuc.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int random = new Random().nextInt((1000 - 1) + 1) + 1;
                    SanPhamDTO sanPhamDTO = new SanPhamDTO("macv" + random, tencv, noidungcv, trangthai, ngaybatdau, ngayketthuc);
                    if (sanPhamDAO.insert(sanPhamDTO)) {
                        list.clear();
                        list.addAll(sanPhamDAO.selectAll());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Log.d("zzzzzzzzzzzzz", "onClick: Thêm thành công");
                    } else {
                        Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}