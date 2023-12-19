package com.example.thangpnph16377_ass.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thangpnph16377_ass.DAO.SanPhamDAO;
import com.example.thangpnph16377_ass.DTO.SanPhamDTO;
import com.example.thangpnph16377_ass.Activity.MainActivity;
import com.example.thangpnph16377_ass.NotifyConfig;
import com.example.thangpnph16377_ass.R;

import java.util.ArrayList;
import java.util.Date;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.viewHolder>{
    private final Context context;
    private final ArrayList<SanPhamDTO> list;

    public SanPhamAdapter(Context context, ArrayList<SanPhamDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cv, null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.txtten.setText(list.get(position).getName());
        holder.txtnoidung.setText(list.get(position).getContent());
        holder.txttrangthai.setText(list.get(position).getStatus());
        holder.txtngaybatdau.setText(list.get(position).getStart());
        holder.txtngayketthuc.setText(list.get(position).getEnds());
        SanPhamDTO sanPhamDTO = list.get(position);

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("zzzzzzzzz", "onClick: Click Delete");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bán có muốn xoá không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
                        int kq = sanPhamDAO.delete(sanPhamDTO);
                        if (kq ==1 ) {
                            list.clear();
                            list.addAll(sanPhamDAO.selectAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            GuiThongBaoDeleteThanhCong();
                        } else {
                            Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialogsua(sanPhamDTO);
            }
        });
    }


    public void opendialogsua(SanPhamDTO sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText txt_TenCV = view.findViewById(R.id.txt_TenCV);
        EditText txt_NoiDungCV = view.findViewById(R.id.txt_NoiDungCV);
        EditText txt_TrangThai = view.findViewById(R.id.txt_TrangThai);
        EditText txt_NgayBatDau = view.findViewById(R.id.txt_NgayBatDau);
        EditText txt_NgayKetThuc = view.findViewById(R.id.txt_NgayKetThuc);

        Button btn_Update = view.findViewById(R.id.btn_Update);
        txt_TenCV.setText(sp.getName());
        txt_NoiDungCV.setText(sp.getContent());
        txt_TrangThai.setText(sp.getStatus());
        txt_NgayBatDau.setText(sp.getStart());
        txt_NgayKetThuc.setText(sp.getEnds());

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = txt_TenCV.getText().toString();
                String noidung = txt_NoiDungCV.getText().toString();
                String trangthai = txt_TrangThai.getText().toString();
                String batdau = txt_NgayBatDau.getText().toString();
                String ketthuc = txt_NgayKetThuc.getText().toString();

                if (ten.trim().isEmpty() || noidung.trim().isEmpty() || trangthai.trim().isEmpty() || batdau.trim().isEmpty() || ketthuc.trim().isEmpty()) {
                    Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    sp.setName(ten);
                    sp.setContent(noidung);
                    sp.setStatus(trangthai);
                    sp.setStart(batdau);
                    sp.setEnds(ketthuc);
                    SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
                    if (sanPhamDAO.update(sp)) {
                        list.clear();
                        list.addAll(sanPhamDAO.selectAll());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        GuiThongBaoUpdateThanhCong();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                        Log.d("zzzzzzzzzzzzz", "onClick: ");
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void GuiThongBaoUpdateThanhCong() {
        try {
            Intent intentDemo = new Intent(context, MainActivity.class);
            intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intentDemo.putExtra("duLieu", "Nội dung gửi từ Notify của main vào activity msg .... ");

            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intentDemo,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

//            Bitmap anh = BitmapFactory.decodeResource(getResources(), R.drawable.fpt);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Tạo NotificationChannel nếu chưa tồn tại
                NotificationChannel channel = new NotificationChannel(
                        NotifyConfig.CHANEL_ID,
                        "Sửa công việc",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("Mô tả");
                notificationManagerCompat.createNotificationChannel(channel);
            }

            Notification customNotification = new NotificationCompat.Builder(context, NotifyConfig.CHANEL_ID)
                    .setSmallIcon(android.R.drawable.ic_delete)
                    .setContentTitle("Thông báo")
                    .setContentText("Bạn đã sửa công việc thành công ^^")
                    .setContentIntent(resultPendingIntent)
//                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(anh).bigLargeIcon(anh))
//                    .setLargeIcon(anh)
//                    .setColor(Color.RED)
//                    .setChannelId(NotifyConfig.CHANEL_ID) // Chỉ định NotificationChannel cho thông báo
                    .setAutoCancel(true)
                    .build();

            int id_notify = (int) new Date().getTime();
            notificationManagerCompat.notify(id_notify, customNotification);


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Có lỗi xảy ra khi tạo thông báo!" + e, Toast.LENGTH_SHORT).show();
            Log.d("zzzzzzzzzzz", "GuiThongBao: lỗi" + e);
        }
    }

    public void GuiThongBaoDeleteThanhCong() {
        try {
            Intent intentDemo = new Intent(context, MainActivity.class);
            intentDemo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intentDemo.putExtra("duLieu", "Đã delete");

            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intentDemo,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

//            Bitmap anh = BitmapFactory.decodeResource(getResources(), R.drawable.fpt);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Tạo NotificationChannel nếu chưa tồn tại
                NotificationChannel channel = new NotificationChannel(
                        NotifyConfig.CHANEL_ID,
                        "Sửa công việc",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("Mô tả");
                notificationManagerCompat.createNotificationChannel(channel);
            }

            Notification customNotification = new NotificationCompat.Builder(context, NotifyConfig.CHANEL_ID)
                    .setSmallIcon(android.R.drawable.ic_delete)
                    .setContentTitle("Thông báo khẩn cấp")
                    .setContentText("Bạn đã xoá công việc thành công ^^")
                    .setContentIntent(resultPendingIntent)
//                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(anh).bigLargeIcon(anh))
//                    .setLargeIcon(anh)
//                    .setColor(Color.RED)
//                    .setChannelId(NotifyConfig.CHANEL_ID) // Chỉ định NotificationChannel cho thông báo
                    .setAutoCancel(true)
                    .build();

            int id_notify = (int) new Date().getTime();
            notificationManagerCompat.notify(id_notify, customNotification);


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Có lỗi xảy ra khi tạo thông báo!" + e, Toast.LENGTH_SHORT).show();
            Log.d("zzzzzzzzzzz", "GuiThongBao: lỗi" + e);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("zzzzzz", "getItemCount: " + list.size());
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView txtten, txtnoidung, txttrangthai, txtngaybatdau, txtngayketthuc, btnupdate, btndelete;
        ImageView imghinh;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txtten = itemView.findViewById(R.id.txtten);
            txtnoidung = itemView.findViewById(R.id.txtnoidung);
            txttrangthai = itemView.findViewById(R.id.txttrangthai);
            txtngaybatdau = itemView.findViewById(R.id.txtngaybatdau);
            txtngayketthuc = itemView.findViewById(R.id.txtngayketthuc);
            btnupdate = itemView.findViewById(R.id.btnupdate);
            btndelete = itemView.findViewById(R.id.btndelete);
            imghinh = itemView.findViewById(R.id.imghinh);
        }
    }
}
