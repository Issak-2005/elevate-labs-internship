package com.quizapp;

public class Result {
    private int resultId;
    private int userId;
    private int score;
    private String status;

    public Result(int resultId, int userId, int score, String status) {
        this.resultId = resultId;
        this.userId = userId;
        this.score = score;
        this.status = status;
    }

    public int getResultId() { return resultId; }
    public int getUserId() { return userId; }
    public int getScore() { return score; }
    public String getStatus() { return status; }
}
