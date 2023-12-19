package com.example.thangpnph16377_ass.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thangpnph16377_ass.DAO.NguoiDungDAO;
import com.example.thangpnph16377_ass.DTO.NguoiDungDTO;
import com.example.thangpnph16377_ass.R;

import java.util.ArrayList;

public class DangNhapActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogin;
    TextView txtSignup;
    NguoiDungDAO nguoiDungDAO;

    private ArrayList<NguoiDungDTO> listnd = new ArrayList<NguoiDungDTO>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignup = findViewById(R.id.txtSignup);

        nguoiDungDAO = new NguoiDungDAO(this);
        listnd = nguoiDungDAO.selectAll();

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUsername.getText().toString();
                String pass = txtPassword.getText().toString();
                if (user.trim().isEmpty() || pass.trim().isEmpty()) {
                    Toast.makeText(DangNhapActivity.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkLogin = nguoiDungDAO.checkLogin(user, pass);
                    if (checkLogin == true) {
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangNhapActivity.this, NavigationViewActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}