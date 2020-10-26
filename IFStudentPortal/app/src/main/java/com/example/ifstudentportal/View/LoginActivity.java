package com.example.ifstudentportal.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.ifstudentportal.Model.Wrapper;
import com.example.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;
import com.google.android.material.button.MaterialButton;

import id.ac.unpar.siamodels.Mahasiswa;

public class LoginActivity extends AppCompatActivity implements ILoginActivity, View.OnClickListener {
    private EditText etPassword;
    private EditText etEmail;
    private AppCompatCheckBox checkbox;
    private MaterialButton btnLogin;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        this.checkbox = findViewById(R.id.checkbox);
        this.etPassword = findViewById(R.id.et_password);
        this.etEmail = findViewById(R.id.et_email);
        this.btnLogin = findViewById(R.id.btn_login);
        this.btnLogin.setOnClickListener(this);
        this.presenter = new Presenter(this);
        this.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // show password
                if (isChecked)
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else {
                    // hide password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        String npm = this.etEmail.getText().toString();
        boolean isMahasiswaIF = presenter.checkNPM(npm);
        if(isMahasiswaIF){
            String email = npm.concat("@student.unpar.ac.id");
            String password = this.etPassword.getText().toString();
            Log.d("email",email);
            Log.d("pw",password);
            this.login(email,password);
        }else{
            Toast toast = Toast.makeText(this,"Bukan NPM Mahasiswa Teknik Informatika UNPAR",Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public void login(String email, String npm) {
        this.presenter.login(email, npm);

    }

    @Override
    public void switchToHomeActivity(Wrapper wrapper) {
        //Log.d("info",phpSessId);
        if(wrapper.getPhpSessId()!=null){
            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
            i.putExtra("phpSessId",wrapper.getPhpSessId());
            i.putExtra("mhs", wrapper.getMahasiswa());
            startActivity(i);
            finish();
        }else{
            Toast toast = Toast.makeText(this,"Email / Password Salah!",Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mahasiswa) {

    }



}
