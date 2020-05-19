package org.techtown.capstoneprojectcocktail;

public class Comment {
    //코멘트 클래스 손대기전에 노희상이랑 의논 필수
    //코멘트 클래스 손대기전에 노희상이랑 의논 필수
    //코멘트 클래스 손대기전에 노희상이랑 의논 필수
    //코멘트 클래스 손대기전에 노희상이랑 의논 필수
    //코멘트 클래스 손대기전에 노희상이랑 의논 필수
    //코멘트 클래스 손대기전에 노희상이랑 의논 필수
    //코멘트 클래스 손대기전에 노희상이랑 의논 필수
    String name;
    String date;
    String contents;
    String imageUrl;
    String uid;

    public Comment(String name, String date, String contents, String imageUrl, String uid) {
        this.name = name;
        this.date = date;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
