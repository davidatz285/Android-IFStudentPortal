package id.ac.unpar.ifstudentportal.Model;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import id.ac.unpar.ifstudentportal.Presenter.HomeManager;
import id.ac.unpar.ifstudentportal.Presenter.LoginManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.ScriptableObject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import id.ac.unpar.siamodels.Dosen;
import id.ac.unpar.siamodels.JadwalKuliah;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.MataKuliahFactory;
import id.ac.unpar.siamodels.TahunSemester;

import javax.script.ScriptException;

public class Scrapper {
    private String phpSessId;
    private Mahasiswa mahasiswa;
    protected Context context;
    protected HomeManager homeManager;
    protected LoginManager loginManager;
    private ProgressDialog dialog;

    private final String BASE_URL = "https://studentportal.unpar.ac.id/";
    private final String LOGIN_URL = BASE_URL + "C_home/sso_login";
    private final String SSO_URL = "https://sso.unpar.ac.id/login";
    private final String JADWAL_URL = BASE_URL + "jadwal/kuliah";
    private final String NILAI_URL = BASE_URL + "nilai";
    private final String TOEFL_URL = BASE_URL + "nilai/toefl";
    private final String LOGOUT_URL = BASE_URL + "logout";
    private final String HOME_URL = BASE_URL + "home";

    public Scrapper(Context context, HomeManager homeManager) {
        this.context = context;
        this.homeManager = homeManager;
        this.dialog = new ProgressDialog(context);
    }

    public Scrapper(Context context, LoginManager loginManager) {
        this.context = context;
        this.loginManager = loginManager;
        this.dialog = new ProgressDialog(context);
    }

    public void login(String npm, String password) {
        new Login().execute(npm, password);
    }

    public void getMahasiswaInfo(String phpSessId, String npm) {
        new GetMahasiswaInfo().execute(phpSessId, npm);
    }

    public void getNilaiMahasiswa(String phpSessId, String npm){
        new GetNilaiMahasiswa().execute(phpSessId,npm);
    }

    public void requestTahunSemester(String phpSessId, String npm) {
        new RequestTahunSemester().execute(phpSessId,npm);
    }

    public void logout() {
        new Logout().execute();
    }

    private class Login extends AsyncTask<String, Void, Wrapper> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Logging in, please wait...");
            dialog.show();
        }

        @Override
        protected Wrapper doInBackground(String... params) {
            String result;
            mahasiswa = new Mahasiswa(params[0]);
            Wrapper wrapper = new Wrapper("",0,mahasiswa);
            try {
                Connection.Response resp = Jsoup.connect(LOGIN_URL).data("Submit", "Login").method(Connection.Method.POST).execute();
                Document doc = resp.parse();
                String lt = doc.select("input[name=lt]").val();
                String execution = doc.select("input[name=execution]").val();
                String jsessionid = resp.cookie("JSESSIONID");
                /* SSO LOGIN */
                Connection loginConn = Jsoup.connect(SSO_URL + ";jsessionid=" + jsessionid + "?service=" + LOGIN_URL);
                loginConn.cookies(resp.cookies());
                String email = params[0].concat("@student.unpar.ac.id");
                loginConn.data("username", email);
                loginConn.data("password", params[1]);
                loginConn.data("lt", lt);
                loginConn.data("execution", execution);
                loginConn.data("_eventId", "submit");
                loginConn.data("submit", "");
                loginConn.validateTLSCertificates(false);
                loginConn.method(Connection.Method.POST);
                resp = loginConn.execute();
                int respCode = resp.statusCode();
                if (resp.body().contains(params[0])) {
                    Map<String, String> phpsessid = resp.cookies();
                    result = phpsessid.get("ci_session");
                    phpSessId = result;
                } else {
                    phpSessId = "";
                }
                wrapper.setMahasiswa(mahasiswa);
                wrapper.setPhpSessId(phpSessId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return wrapper;
        }

        @Override
        protected void onPostExecute(Wrapper wrapper) {
            super.onPostExecute(wrapper);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            loginManager.switchToHomeActivity(wrapper);
        }
    }

    private class GetMahasiswaInfo extends AsyncTask<String, String, Mahasiswa> {
        @Override
        protected Mahasiswa doInBackground(String... params) {
            mahasiswa = new Mahasiswa(params[1]);
            try {
                Connection.Response resp = Jsoup.connect(HOME_URL).cookie("ci_session", params[0]).method(Connection.Method.GET).execute();
                Document doc = resp.parse();
                String nama = doc.select("div[class=namaUser d-none d-lg-block mr-3]").text();
                mahasiswa.setNama(nama.substring(0, nama.indexOf(mahasiswa.getEmailAddress())));
                Element photo = doc.select("img[class=img-fluid fotoProfil]").first();
                String photoPath = photo.attr("src");
                mahasiswa.setPhotoPath(photoPath);
                List<JadwalKuliah> jadwalKuliahList = requestJadwal(params[0]);
                mahasiswa.setJadwalKuliahList(jadwalKuliahList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mahasiswa;
        }

        public List<JadwalKuliah> requestJadwal(String phpsessid) throws IOException {
            Connection.Response resp = Jsoup.connect(JADWAL_URL).cookie("ci_session", phpsessid).method(Connection.Method.GET).execute();
            Document doc = resp.parse();
            Elements jadwalTable = doc.select("table[class=table table-responsive table-hover d-md-table ]");
            List<JadwalKuliah> jadwalList = new ArrayList<JadwalKuliah>();

            /* Kuliah */
            if (jadwalTable.size() > 0) {
                Elements tableKuliah = jadwalTable.get(0).select("tbody tr");
                String kode = new String();
                String nama = new String();
                for (Element elem : tableKuliah) {
                    if (elem.className().contains("")) {
                        if (!(elem.child(2).text().isEmpty() && elem.child(4).text().isEmpty())) {
                            kode = elem.child(2).text();
                            nama = elem.child(4).text();
                        }
                        MataKuliah currMk = MataKuliahFactory.getInstance().createMataKuliah(kode,
                                Integer.parseInt(elem.child(5).text()), nama);
                        try {
                            String kelasString = elem.child(6).text();
                            String hariString = elem.child(0).text();
                            String waktuString = elem.child(1).text();
                            if (hariString != null & hariString.length() != 0
                                    && waktuString != null & waktuString.length() != 0) {
                                jadwalList.add(
                                        new JadwalKuliah(currMk, kelasString.length() == 0 ? null : kelasString.charAt(0),
                                                new Dosen(null, elem.child(7).text()), hariString, waktuString,
                                                elem.child(3).text()));
                            }
                        } catch (IndexOutOfBoundsException e) {
                            // void. do not add jadwal.
                        }
                    }
                }
            }
            return jadwalList;
        }

        @Override
        protected void onPostExecute(Mahasiswa mahasiswa) {
            super.onPostExecute(mahasiswa);
            homeManager.displayMahasiswaInfo(mahasiswa);
        }
    }

    private class GetNilaiMahasiswa extends AsyncTask<String, String, Mahasiswa>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Getting data mahasiswa, please wait..");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Mahasiswa mahasiswa) {
            super.onPostExecute(mahasiswa);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            homeManager.setMahasiswaForFragment(mahasiswa);
        }

        @Override
        protected Mahasiswa doInBackground(String... params) {
            Mahasiswa mahasiswa = new Mahasiswa(params[1]);
            try {
                requestNilaiTOEFL(params[0], mahasiswa);
                requestNilai(params[0],mahasiswa);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mahasiswa;
        }

        public Mahasiswa requestNilai(String phpsessid, Mahasiswa logged_mhs) throws IOException {
            Connection.Response resp = Jsoup.connect(NILAI_URL).cookie("ci_session", phpsessid).method(Connection.Method.POST).execute();
            Document doc = resp.parse();
            Elements dropdownSemester = doc.select("#dropdownSemester option");
            ArrayList<String> listSemester = new ArrayList<String>();
            for (Element semester : dropdownSemester) {
                listSemester.add(semester.attr("value"));
            }
            for (int i = 0; i < listSemester.size(); i++) {
                try {
                    String[] thn_sem = listSemester.get(i).split("-");
                    String thn = thn_sem[0];
                    String sem = thn_sem[1];
                    Connection.Response response = Jsoup.connect(NILAI_URL + "/" + thn + "/" + sem).cookie("ci_session", phpsessid).method(Connection.Method.POST).execute();
                    Document document = response.parse();
                    Elements scripts = document.select("script");
                    for (Element script: scripts) {
                        String scriptHTML = script.html();
                        if (scriptHTML.contains("var data_mata_kuliah = [];") && scriptHTML.contains("var data_angket = [];")) {
                            String scriptDataMataKuliah = scriptHTML.substring(scriptHTML.indexOf("var data_mata_kuliah = [];"), scriptHTML.indexOf("var data_angket = [];"));
                            javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager().getEngineByName("rhino");
                            engine.eval(scriptDataMataKuliah);
                            ScriptableObject data = (ScriptableObject) engine.get("data_mata_kuliah");
                            TahunSemester tahunSemesterNilai = new TahunSemester(Integer.parseInt(thn), sem.charAt(0));
                            for(int j=0;j<data.size();j++){
                                NativeArray mk  = (NativeArray) data.get(j);
                                String namaMk = mk.get("nama_mata_kuliah").toString();
                                String kodeMk = mk.get("kode_mata_kuliah").toString();
                                int sks = Integer.parseInt(mk.get("jumlah_sks").toString());
                                String na = mk.get("na").toString();
                                if(!na.equals("#")){
                                    MataKuliah curr_mk = MataKuliahFactory.getInstance().createMataKuliah(kodeMk, sks, namaMk);
                                    logged_mhs.getRiwayatNilai().add(new Mahasiswa.Nilai(tahunSemesterNilai, curr_mk, na));
                                }
                            }
                            break;
                        }
                    }
//

                } catch (IOException | IllegalArgumentException | ScriptException e) {
                    e.printStackTrace();
                }

            }
            Collections.sort(logged_mhs.getRiwayatNilai(), (o1, o2) -> {
                if (o1.getTahunAjaran() < o2.getTahunAjaran()) {
                    return -1;
                }
                if (o1.getTahunAjaran() > o2.getTahunAjaran()) {
                    return +1;
                }
                if (o1.getSemester().getOrder() < o2.getSemester().getOrder()) {
                    return -1;
                }
                if (o1.getSemester().getOrder() > o2.getSemester().getOrder()) {
                    return +1;
                }
                return 0;
            });
            return logged_mhs;
        }



        public void requestNilaiTOEFL(String phpsessid, Mahasiswa mahasiswa) throws IOException {
            SortedMap<LocalDate, Integer> nilaiTerakhirTOEFL = new TreeMap<>();
            Connection.Response resp = Jsoup.connect(TOEFL_URL).cookie("ci_session", phpsessid).method(Connection.Method.POST).execute();
            Document doc = resp.parse();
            Elements nilaiTOEFL = doc.select("table").select("tbody").select("tr");
            if (!nilaiTOEFL.isEmpty()) {
                for (int i = 0; i < nilaiTOEFL.size(); i++) {
                    Element nilai = nilaiTOEFL.get(i).select("td").get(5);
                    Element tgl_toefl = nilaiTOEFL.get(i).select("td").get(1);
                    String[] tanggal = tgl_toefl.text().split("-");
                    LocalDate localDate = LocalDate.of(Integer.parseInt(tanggal[2]), Integer.parseInt(tanggal[1]),
                            Integer.parseInt(tanggal[0]));

                    nilaiTerakhirTOEFL.put(localDate, Integer.parseInt(nilai.text()));
                }
            }
            mahasiswa.setNilaiTOEFL(nilaiTerakhirTOEFL);
        }
    }
    private class RequestTahunSemester extends AsyncTask<String, String, TahunSemester>{

        @Override
        protected TahunSemester doInBackground(String... strings) {
            TahunSemester currTahunSemester = new TahunSemester("201");
            try {
                Connection.Response resp = Jsoup.connect(NILAI_URL).cookie("ci_session", strings[0]).method(Connection.Method.POST).execute();
                Document doc = resp.parse();
                String curr_sem = "";
                Elements options = doc.getElementsByAttributeValue("name", "dropdownSemester").first().children();
                curr_sem = options.last().val();
                curr_sem = curr_sem.substring(2,4).concat(curr_sem.substring(5));
                currTahunSemester = new TahunSemester(curr_sem);
                return currTahunSemester;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return currTahunSemester;
        }
        @Override
        protected void onPostExecute(TahunSemester tahunSemester) {
            super.onPostExecute(tahunSemester);
            homeManager.setTahunSemesterForFragment(tahunSemester);
        }
    }

    private class Logout extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Connection logoutConn = Jsoup.connect(LOGOUT_URL);
            logoutConn.timeout(0);
            logoutConn.validateTLSCertificates(false);
            logoutConn.method(Connection.Method.GET);
            try {
                logoutConn.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
