package com.thanguit.tiushop.model.repository;

public class Intro {
    private int image;
    private String title;
    private String hint;

    public Intro(int image, String title, String hint) {
        this.image = image;
        this.title = title;
        this.hint = hint;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
