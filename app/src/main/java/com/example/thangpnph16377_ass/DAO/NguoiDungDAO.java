package com.example.thangpnph16377_ass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thangpnph16377_ass.DTO.NguoiDungDTO;
import com.example.thangpnph16377_ass.Database.DbHelper;

import java.util.ArrayList;

public class NguoiDungDAO {
    String TAG = "zzzzzzzzzzzzzzz";
    private final DbHelper dbHelper;

    public NguoiDungDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<NguoiDungDTO> selectAll() {
        ArrayList<NguoiDungDTO> listnd = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM nguoidung", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    NguoiDungDTO nd = new NguoiDungDTO();
                    nd.setUsername(cursor.getString(0));
                    nd.setPassword(cursor.getString(1));
                    nd.setEmail(cursor.getString(2));
                    nd.setFullname(cursor.getString(3));
                    listnd.add(nd);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi", e);
        }
        return listnd;
    }

    // Check hàm user
    public boolean checkUser(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM nguoidung where username = ?", new String[]{username});
        int row = cs.getCount();
        return (row > 0);
    }

    // Check hàm login
    public boolean checkLogin (String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM nguoidung where username = ? and password = ?", new String[]{username, password});
        int row = cs.getCount();
        return (row > 0);
    }

    public boolean insert (NguoiDungDTO nd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", nd.getUsername());
        values.put("password", nd.getPassword());
        values.put("email", nd.getEmail());
        values.put("fullname", nd.getFullname());

        long row = db.insert("nguoidung", null, values);
        return (row > 0);
    }

    public boolean update(NguoiDungDTO nd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();// đưa dữ liệu vào database
        values.put("username",nd.getUsername());
        values.put("password", nd.getPassword()); // đưa dữ liệu vào cột tieude
        values.put("email",nd.getEmail());
        values.put("fullname",nd.getFullname());

        // nếu add dữ liệu thành công thì trả về giá trị tương ứng với số dòng được chèn vào
        long row = db.update("nguoidung", values, "username=?", new String[]{String.valueOf(nd.getUsername())});
        return (row > 0);
    }

    public boolean delete (NguoiDungDTO nd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("nguoidung", "username=?", new String[]{String.valueOf(nd)});
        return (row > 0);
    }

}
