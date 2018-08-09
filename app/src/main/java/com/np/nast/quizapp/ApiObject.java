package com.np.nast.quizapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiObject {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("opta")
    @Expose
    private String opta;
    @SerializedName("optb")
    @Expose
    private String optb;
    @SerializedName("optc")
    @Expose
    private String optc;
    @SerializedName("answer")
    @Expose
    private String answer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpta() {
        return opta;
    }

    public void setOpta(String opta) {
        this.opta = opta;
    }

    public String getOptb() {
        return optb;
    }

    public void setOptb(String optb) {
        this.optb = optb;
    }

    public String getOptc() {
        return optc;
    }

    public void setOptc(String optc) {
        this.optc = optc;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
