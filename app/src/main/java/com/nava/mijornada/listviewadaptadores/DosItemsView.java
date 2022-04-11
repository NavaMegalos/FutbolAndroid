package com.nava.mijornada.listviewadaptadores;

public class DosItemsView {
    private String textViewOne;
    private String textViewTwo;

    public DosItemsView(String textViewOne, String textViewTwo) {
        this.textViewOne = textViewOne;
        this.textViewTwo = textViewTwo;
    }

    public String getTextViewOne() {
        return textViewOne;
    }

    public String getTextViewTwo() {
        return textViewTwo;
    }

    public void setTextViewOne(String textViewOne) {
        this.textViewOne = textViewOne;
    }

    public void setTextViewTwo(String textViewTwo) {
        this.textViewTwo = textViewTwo;
    }

}
