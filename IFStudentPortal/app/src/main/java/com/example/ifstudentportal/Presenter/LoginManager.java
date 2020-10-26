package com.example.ifstudentportal.Presenter;

import com.example.ifstudentportal.Model.Scrapper;
import com.example.ifstudentportal.Model.Wrapper;
import com.example.ifstudentportal.View.ILoginActivity;

public class LoginManager {
    private ILoginActivity iLoginActivity;
    protected Scrapper scrapper;

    public LoginManager() {
        this.scrapper = new Scrapper(iLoginActivity.getContext(), this);
    }

    public LoginManager(ILoginActivity iLoginActivity) {
        this.iLoginActivity = iLoginActivity;
        this.scrapper = new Scrapper(iLoginActivity.getContext(), this);
    }

    public void setiLoginActivity(ILoginActivity iLoginActivity) {
        this.iLoginActivity = iLoginActivity;
    }
//    public LoginPresenter(IHomeActivity iHomeActivity) {
//        this.iHomeActivity = iHomeActivity;
//        this.scrapper = new Scrapper(iHomeActivity.getContext(),this);
//    }

    public void login(String email, String npm) {
        this.scrapper.login(email, npm);
    }

    public void switchToHomeActivity(Wrapper wrapper) {
        this.iLoginActivity.switchToHomeActivity(wrapper);
    }

    public boolean checkNPM(String npm) {
        boolean isMahasiswaIF = false;
        if (npm.startsWith("20")) {
            if (npm.substring(4, 6).equals("73")) {
                isMahasiswaIF = true;
            }

        } else if (npm.startsWith("618")) {
            isMahasiswaIF = true;
        } else {
            isMahasiswaIF = false;
        }
        return isMahasiswaIF;
    }
}