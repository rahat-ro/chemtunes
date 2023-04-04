package com.programmersjail.chemtunesorganicchemistry.chempak;

public class ChemPackageModel {

    private String package_title,package_s_dis,package_dis,package_img,	package_duration,
            package_fee,package_limit;

    public ChemPackageModel(String package_title, String package_s_dis, String package_dis,
                            String package_img, String package_duration, String package_fee, String package_limit) {
        this.package_title = package_title;
        this.package_s_dis = package_s_dis;
        this.package_dis = package_dis;
        this.package_img = package_img;
        this.package_duration = package_duration;
        this.package_fee = package_fee;
        this.package_limit = package_limit;
    }

    public String getPackage_title() {
        return package_title;
    }

    public String getPackage_s_dis() {
        return package_s_dis;
    }

    public String getPackage_dis() {
        return package_dis;
    }

    public String getPackage_img() {
        return package_img;
    }

    public String getPackage_duration() {
        return package_duration;
    }

    public String getPackage_fee() {
        return package_fee;
    }

    public String getPackage_limit() {
        return package_limit;
    }
}
