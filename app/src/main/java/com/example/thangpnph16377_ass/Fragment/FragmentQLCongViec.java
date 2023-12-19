package com.example.thangpnph16377_ass.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thangpnph16377_ass.Activity.MainActivity;
import com.example.thangpnph16377_ass.Adapter.SanPhamAdapter;
import com.example.thangpnph16377_ass.DAO.SanPhamDAO;
import com.example.thangpnph16377_ass.DTO.SanPhamDTO;
import com.example.thangpnph16377_ass.NotifyConfig;
import com.example.thangpnph16377_ass.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class FragmentQLCongViec extends Fragment {

    RecyclerView rccongviec;
    FloatingActionButton fladd;
    SanPhamDAO spdao;
    SanPhamAdapter adapter;
    private ArrayList<SanPhamDTO> list = new ArrayList<SanPhamDTO>();

    public FragmentQLCongViec() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_q_l_cong_viec, container, false);

        rccongviec = view.findViewById(R.id.rccongviec);
        fladd = view.findViewById(R.id.fladd);

        spdao = new SanPhamDAO(getActivity());
        list = spdao.selectAll();

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        rccongviec.setLayoutManager(layout);

        adapter = new SanPhamAdapter(getActivity(), list);
        rccongviec.setAdapter(adapter);

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogThem();
            }
        });

        return view;
    }

    public void openDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    Toast.makeText(getActivity(), "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int random = new Random().nextInt((1000 - 1) + 1) + 1;
                    SanPhamDTO sanPhamDTO = new SanPhamDTO("macv" + random, tencv, noidungcv, trangthai, ngaybatdau, ngayketthuc);
                    if (spdao.insert(sanPhamDTO)) {
                        list.clear();
                        list.addAll(spdao.selectAll());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Log.d("zzzzzzzzzzzzz", "onClick: Thêm thành công");
                        GuiThongBaoThemThanhCong();
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void GuiThongBaoThemThanhCong() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("duLieu", "Đã thêm thành công !");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fpt);

        Notification notification = new NotificationCompat.Builder(
                getActivity(), NotifyConfig.CHANEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle("Thông báo")
                .setContentText("Bạn đã thêm thành công")
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(bitmap))
                .setLargeIcon(bitmap)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(getActivity());

        if (ActivityCompat.checkSelfPermission(
                getActivity(),
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // chưa được ấp quyền: Gọi activity xin quyền
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 99999);// phải dùng manifest trong android chứ không được dùng manifest của pakeer
            return;
        }
        int id_notify = (int) new Date().getTime();

        notificationManagerCompat.notify(id_notify, notification);
    }


}