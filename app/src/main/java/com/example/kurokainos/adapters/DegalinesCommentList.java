package com.example.kurokainos.adapters;

public class DegalinesCommentList {

    String Adresas;
    String BenzinoKaina;
    String DyzelioKaina;
    String DujuKaina;
    String CommentDate;

    public DegalinesCommentList(String adresas, String benzinoKaina, String dyzelioKaina, String dujuKaina, String commentDate) {
        Adresas = adresas;
        BenzinoKaina = benzinoKaina;
        DyzelioKaina = dyzelioKaina;
        DujuKaina = dujuKaina;
        CommentDate = commentDate;
    }

    public String getAdresas() {
        return Adresas;
    }

    public void setAdresas(String adresas) {
        Adresas = adresas;
    }

    public String getBenzinoKaina() {
        return BenzinoKaina;
    }

    public void setBenzinoKaina(String benzinoKaina) {
        BenzinoKaina = benzinoKaina;
    }

    public String getDyzelioKaina() {
        return DyzelioKaina;
    }

    public void setDyzelioKaina(String dyzelioKaina) {
        DyzelioKaina = dyzelioKaina;
    }

    public String getDujuKaina() {
        return DujuKaina;
    }

    public void setDujuKaina(String dujuKaina) {
        DujuKaina = dujuKaina;
    }

    public String getCommentDate() {
        return CommentDate;
    }

    public void setCommentDate(String commentDate) {
        CommentDate = commentDate;
    }
}
