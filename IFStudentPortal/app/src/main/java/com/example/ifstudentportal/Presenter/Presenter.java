package com.example.ifstudentportal.Presenter;

import com.example.ifstudentportal.Model.Wrapper;
import com.example.ifstudentportal.View.Fragment.IJadwalFragment;
import com.example.ifstudentportal.View.HomeActivity;
import com.example.ifstudentportal.View.IHomeActivity;
import com.example.ifstudentportal.View.ILoginActivity;
import com.example.ifstudentportal.View.LoginActivity;

import java.util.List;

import id.ac.unpar.siamodels.JadwalKuliah;

public class Presenter {
    protected HomeManager homeManager;
    protected LoginManager loginManager;
    protected HomeActivity homeActivity;
    protected LoginActivity  loginActivity;
    protected JadwalManager jadwalManager;

    public Presenter(ILoginActivity iLoginActivity) {
        this.loginManager = new LoginManager(iLoginActivity);
    }

    public Presenter(IHomeActivity iHomeActivity) {
        this.homeManager = new HomeManager(iHomeActivity);
    }

    public Presenter(IJadwalFragment iJadwalFragment) {
        this.jadwalManager = new JadwalManager(iJadwalFragment);
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    public void setLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginManager.setiLoginActivity(loginActivity);
    }

    public void login(String email, String npm) {
        this.loginManager.login(email,npm);
    }

    public void getMahasiswaInfo(String phpSessId, String npm) {
        this.homeManager.getMahasiswaInfo(phpSessId, npm);
    }

    public void switchToHomeActivity(Wrapper wrapper) {
        this.loginManager.switchToHomeActivity(wrapper);
    }
    public void getListJadwal(String phpSessId){
        this.jadwalManager.getListJadwal(phpSessId);
    }
    public boolean checkNPM(String npm) {
        return this.loginManager.checkNPM(npm);
    }
}
