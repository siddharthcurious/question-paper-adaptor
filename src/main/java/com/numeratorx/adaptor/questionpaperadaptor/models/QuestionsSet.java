package com.numeratorx.adaptor.questionpaperadaptor.models;

import lombok.Data;

import java.util.List;

@Data
public class QuestionsSet {
    private String heading;
    private String userId;
    private List<String> tags;
    private List<QuestionAnswers> qaList;
}
