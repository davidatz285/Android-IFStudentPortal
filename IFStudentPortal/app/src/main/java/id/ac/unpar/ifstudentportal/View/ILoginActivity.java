package id.ac.unpar.ifstudentportal.View;

import android.content.Context;

import id.ac.unpar.ifstudentportal.Model.Wrapper;

public interface ILoginActivity {
    void login(String email,String npm);
    void switchToHomeActivity(Wrapper wrapper);
    Context getContext();
}
