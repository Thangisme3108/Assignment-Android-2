package com.example.thangpnph16377_ass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thangpnph16377_ass.DTO.SanPhamDTO;
import com.example.thangpnph16377_ass.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    String TAG = "zzzzzzzzzzz";
    private final DbHelper dbHelper;
    SQLiteDatabase database;

    public SanPhamDAO(Context context) {
         dbHelper = new DbHelper(context);
         database=dbHelper.getWritableDatabase();
    }

    public ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM sanpham", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    SanPhamDTO sp = new SanPhamDTO();
                    sp.setMacv(cursor.getString(0));
                    sp.setName(cursor.getString(1));
                    sp.setContent(cursor.getString(2));
                    sp.setStatus(cursor.getString(3));
                    sp.setStart(cursor.getString(4));
                    sp.setEnds(cursor.getString(5));
                    list.add(sp);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi", e);
        }
        return list;
    }



    public boolean insert(SanPhamDTO sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", sp.getName());
        values.put("content", sp.getContent());
        values.put("status", sp.getStatus());
        values.put("start", sp.getStart());
        values.put("ends", sp.getEnds());

        long row = db.insert("sanpham", null, values);
        return (row > 0);
    }

    public int delete(SanPhamDTO sp) {

        int row = database.delete("sanpham", "id=?", new String[]{String.valueOf(sp.getMacv())});
        return row;
    }

    public boolean update(SanPhamDTO sp) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", sp.getName());
        values.put("content", sp.getContent());
        values.put("status", sp.getStatus());
        values.put("start", sp.getStart());
        values.put("ends", sp.getEnds());

        long row = db.update("sanpham", values, "id=?", new String[]{String.valueOf(sp.getMacv())});
        return (row > 0);
    }
}
