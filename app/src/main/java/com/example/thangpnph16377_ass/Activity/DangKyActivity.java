package com.example.thangpnph16377_ass.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thangpnph16377_ass.DAO.NguoiDungDAO;
import com.example.thangpnph16377_ass.DTO.NguoiDungDTO;
import com.example.thangpnph16377_ass.R;

public class DangKyActivity extends AppCompatActivity {
    EditText txtUsername, txtEmail, txtPassword, txtConfirmPassword;
    Button btnSignup;
    NguoiDungDAO nguoiDungDAO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
        nguoiDungDAO = new NguoiDungDAO(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUsername.getText().toString();
                String email = txtEmail.getText().toString();
                String pass = txtPassword.getText().toString();
                String confirm = txtConfirmPassword.getText().toString();
                NguoiDungDTO nd = new NguoiDungDTO( user, pass, email, "");
                if (user.trim().isEmpty() || email.trim().isEmpty() || pass.trim().isEmpty() || confirm.trim().isEmpty()) {
                    Toast.makeText(DangKyActivity.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(confirm)) {
                        boolean checkuser = nguoiDungDAO.checkUser(user);
                        if (checkuser == false) {
                            boolean insert = nguoiDungDAO.insert(nd);
                            if (insert == true) {
                                Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DangKyActivity.this, "Người dùng đã tồn tại, vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DangKyActivity.this, "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}