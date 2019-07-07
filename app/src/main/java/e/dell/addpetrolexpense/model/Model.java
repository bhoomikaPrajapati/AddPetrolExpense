package e.dell.addpetrolexpense.model;

import java.io.Serializable;

public class Model implements Serializable {

    public static String DATA="data";

    private String datepik;
    private  String timepik;
    private String amount;
    private String km;
    private String pay_user;
    private int id;

    public Model(int id) {
        this.id = id;
    }

    public Model() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatepik() {
        return datepik;
    }

    public void setDatepik(String datepik) {
        this.datepik = datepik;
    }

    public String getTimepik() {
        return timepik;
    }

    public void setTimepik(String timepik) {
        this.timepik = timepik;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public Model(String datepik, String timepik, String amount, String km) {
        this.datepik = datepik;
        this.timepik = timepik;
        this.amount = amount;
        this.km = km;
    }


    public String getPay_user() {
        return pay_user;
    }

    public void setPay_user(String pay_user) {
        this.pay_user = pay_user;
    }
}
