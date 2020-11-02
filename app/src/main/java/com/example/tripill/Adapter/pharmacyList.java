package com.example.tripill.Adapter;

public class pharmacyList {

    String pharmacyListName_kr;
    String pharmacyListName_en;

    public pharmacyList(String pharmacyListName_kr, String pharmacyListName_en) {
        this.pharmacyListName_kr=pharmacyListName_kr;
        this.pharmacyListName_en=pharmacyListName_en;
    }

    public String getPharmacyListName_kr() {
        return pharmacyListName_kr;
    }

    public void setPharmacyListName_kr(String pharmacyListName_kr) {
        this.pharmacyListName_kr=pharmacyListName_kr;
    }

    public String getPharmacyListName_en() {
        return pharmacyListName_en;
    }

    public void setPharmacyListName_en(String pharmacyListName_en) {
        this.pharmacyListName_en=pharmacyListName_en;
    }
}

