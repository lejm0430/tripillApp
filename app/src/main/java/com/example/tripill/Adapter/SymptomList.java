package com.example.tripill.Adapter;
public class SymptomList {
    String symptom;
    boolean isSelected;
    int score;
    String symptomkr;

    public SymptomList(String symptom, int score, String symptomkr) {
        this.symptom=symptom;
        this.isSelected=false;
        this.score = score;
        this.symptomkr = symptomkr;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom=symptom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score=score;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSymptomkr() {
        return symptomkr;
    }

    public void setSymptomkr(String symptomkr) {
        this.symptomkr=symptomkr;
    }
}

