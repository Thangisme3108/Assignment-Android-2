package com.example.thangpnph16377_ass.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String Db_name = "QLSP";

    public DbHelper( Context context ) {
        super(context, Db_name, null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tb_nguoidung = "CREATE TABLE nguoidung (username text primary key, password text, email text, fullname text)";
        sqLiteDatabase.execSQL(tb_nguoidung);

        String tb_sanpham = "CREATE TABLE sanpham (id integer primary key autoincrement, name text , content text, status text, start text, ends text)";
        sqLiteDatabase.execSQL(tb_sanpham);

        String data = "INSERT INTO sanpham VALUES (0, 'Công việc 1', 'Nội dung 1', '0', '20/11/2023', '30/11/2023')," +
                "(1, 'Công việc 2', 'Nội dung 2', '1', '21/11/2023', '05/12/2023')," +
                "(2, 'Công việc 3', 'Nội dung 3', '-1', '10/11/1023', '15/11/2023') ";
        sqLiteDatabase.execSQL(data);
//        String insertData = "INSERT INTO sanpham (name, content, status, start, ends) VALUES ('Tên sản phẩm 1', 'Nội dung sản phẩm 1', 'Hoạt động', '2023-01-01', '2023-12-31')";
//        sqLiteDatabase.execSQL(insertData);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS sanpham");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS nguoidung");
            onCreate(sqLiteDatabase);
        }
    }
}
