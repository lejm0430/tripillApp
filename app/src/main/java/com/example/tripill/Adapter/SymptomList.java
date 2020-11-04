package com.example.tripill.Adapter;
public class SymptomList {
    String symptom;
    boolean isSelected;
    int score;

    public SymptomList(String symptom, int score) {
        this.symptom=symptom;
        this.isSelected=false;
        this.score = score;
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
}

