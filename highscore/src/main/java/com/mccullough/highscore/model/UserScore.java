package com.mccullough.highscore.model;

public class UserScore {

    private int id;
    private String userName;
    private int score;

    public UserScore() {
    }

    public UserScore(int id, String userName, int score) {
        this.id = id;
        this.userName = userName;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserScore{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", score=" + score +
                '}';
    }
}
