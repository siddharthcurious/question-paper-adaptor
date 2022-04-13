package com.numeratorx.adaptor.questionpaperadaptor.models;

import lombok.Data;

import java.util.List;

@Data
public class QuestionAnswers {
    private String questionText;
    private List<String> ansOptions;

    public QuestionAnswers(String questionText, List<String> ansOptions) {
        this.questionText = questionText;
        this.ansOptions = ansOptions;
    }
}
