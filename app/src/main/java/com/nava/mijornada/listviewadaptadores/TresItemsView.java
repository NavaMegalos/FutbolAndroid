package com.nava.mijornada.listviewadaptadores;

public class TresItemsView {
    private String textViewOne;
    private String textViewTwo;
    private String textViewThree;

    public TresItemsView(String textViewOne, String textViewTwo, String textViewThree) {
        this.textViewOne = textViewOne;
        this.textViewTwo = textViewTwo;
        this.textViewThree = textViewThree;
    }

    public String getTextViewOne() {
        return textViewOne;
    }

    public String getTextViewTwo() {
        return textViewTwo;
    }

    public String getTextViewThree() {
        return textViewThree;
    }

    public void setTextViewOne(String textViewOne) {
        this.textViewOne = textViewOne;
    }

    public void setTextViewTwo(String textViewTwo) {
        this.textViewTwo = textViewTwo;
    }

    public void setTextViewThree(String textViewThree) {
        this.textViewThree = textViewThree;
    }
}
