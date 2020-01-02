package com.integrals.bhcapp.Helper;

public class HomeCard {

    String name,desc,topscore;
    int icon;


    public HomeCard(String name, String desc,int icon) {
        this.name = name;
        this.desc = desc;
        this.icon=icon;
    }

    public String getTopscore() {
        return topscore;
    }

    public void setTopscore(String topscore) {
        this.topscore = topscore;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
