package id.ac.unpar.ifstudentportal.Presenter;

import id.ac.unpar.ifstudentportal.View.IHomeActivity;
import id.ac.unpar.ifstudentportal.View.ILoginActivity;

public class Presenter {
    protected HomeManager homeManager;
    protected LoginManager loginManager;


    public Presenter(ILoginActivity iLoginActivity) {
        this.loginManager = new LoginManager(iLoginActivity);
    }

    public Presenter(IHomeActivity iHomeActivity) {
        this.homeManager = new HomeManager(iHomeActivity);
    }

    public void getNilaiMahasiswa(String phpSessId, String npm){
        this.homeManager.getNilaiMahasiswa(phpSessId,npm);
    }

    public void login(String email, String npm) {
        this.loginManager.login(email,npm);
    }

    public void getMahasiswaInfo(String phpSessId, String npm) {
        this.homeManager.getMahasiswaInfo(phpSessId, npm);
    }

    public boolean checkNPM(String npm) {
        return this.loginManager.checkNPM(npm);
    }

    public void requestTahunSemester(String phpSessId, String npm) {
        this.homeManager.requestTahunSemester(phpSessId,npm);
    }

    public void logout() {
        this.homeManager.logout();
    }
}
