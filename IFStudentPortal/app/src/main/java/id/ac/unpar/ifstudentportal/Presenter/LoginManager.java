package id.ac.unpar.ifstudentportal.Presenter;

import id.ac.unpar.ifstudentportal.Model.Scrapper;
import id.ac.unpar.ifstudentportal.Model.Wrapper;
import id.ac.unpar.ifstudentportal.View.ILoginActivity;

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

    public void login(String npm, String password) {
        this.scrapper.login(npm, password);
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