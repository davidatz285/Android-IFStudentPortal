package id.ac.unpar.ifstudentportal.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import id.ac.unpar.ifstudentportal.Model.Wrapper;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;
import com.google.android.material.button.MaterialButton;


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
        this.checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked)
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            else {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }


    @Override
    public void onClick(View v) {
        String npm = this.etEmail.getText().toString();
        boolean isMahasiswaIF = presenter.checkNPM(npm);
        if(isMahasiswaIF){
            String password = this.etPassword.getText().toString();
            this.login(npm,password);
        }else{
            Toast toast = Toast.makeText(this,"Bukan NPM Mahasiswa Teknik Informatika UNPAR",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void login(String npm, String password) {
        this.presenter.login(npm, password);
    }

    @Override
    public void switchToHomeActivity(Wrapper wrapper) {
        if(wrapper.getPhpSessId().length()!=0){
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
}
