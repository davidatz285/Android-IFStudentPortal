package id.ac.unpar.ifstudentportal.Presenter;

import id.ac.unpar.ifstudentportal.Model.Wrapper;
import id.ac.unpar.ifstudentportal.View.Fragment.IJadwalFragment;
import id.ac.unpar.ifstudentportal.View.Fragment.IPrasyaratFragment;
import id.ac.unpar.ifstudentportal.View.HomeActivity;
import id.ac.unpar.ifstudentportal.View.IHomeActivity;
import id.ac.unpar.ifstudentportal.View.ILoginActivity;
import id.ac.unpar.ifstudentportal.View.LoginActivity;
import id.ac.unpar.siamodels.Mahasiswa;

public class Presenter {
    protected HomeManager homeManager;
    protected LoginManager loginManager;
    protected HomeActivity homeActivity;
    protected LoginActivity  loginActivity;
    protected JadwalManager jadwalManager;
    protected PrasyaratManager prasyaratManager;

    public Presenter(ILoginActivity iLoginActivity) {
        this.loginManager = new LoginManager(iLoginActivity);
    }

    public Presenter(IHomeActivity iHomeActivity) {
        this.homeManager = new HomeManager(iHomeActivity);
    }

    public Presenter(IJadwalFragment iJadwalFragment) {
        this.jadwalManager = new JadwalManager(iJadwalFragment);
    }

    public Presenter(IPrasyaratFragment iPrasyaratFragment) {
        this.prasyaratManager = new PrasyaratManager(iPrasyaratFragment);
    }



    public void getNilaiMahasiswa(String phpSessId, String npm){
        this.homeManager.getNilaiMahasiswa(phpSessId,npm);
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
