package jp.ac.jec.cm0146.rirekisyo;

import com.google.android.material.transition.ScaleProvider;

import java.util.Calendar;

public class Company {
    private int id;
    private String name;
    private String address;
    private String TEL;
    private String capital;
    private String profit;
    private String salary1st;
    private String memo;

    public Company(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Company(int id, String name, String address, String TEL, String capital, String profit, String salary1st, String memo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.TEL = TEL;
        this.capital = capital;
        this.profit= profit;
        this.salary1st = salary1st;
        this.memo = memo;
    }

    public Company(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getSalary1st() {
        return salary1st;
    }

    public void setSalary1st(String salary1st) {
        this.salary1st = salary1st;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
