package com.numeratorx.adaptor.questionpaperadaptor.services;

import com.numeratorx.adaptor.questionpaperadaptor.models.QuestionAnswers;
import com.numeratorx.adaptor.questionpaperadaptor.models.QuestionsSet;
import com.numeratorx.adaptor.questionpaperadaptor.responses.QuestionsProcessedStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilePreprocessingService {

    private static final Logger log = LoggerFactory.getLogger(FilePreprocessingService.class);

    @Autowired
    private KafkaMessageSenderService kafkaMessageSenderService;

    private List<String> getSplitted(String text, String sep){
        List<String> data = new LinkedList<>();
        String [] splitted = text.split(sep);
        for(String tag: splitted){
            data.add(tag.strip());
        }
        return data;
    }

    public QuestionsProcessedStats processTextFile(MultipartFile file, String userId) {
        QuestionsProcessedStats stats = new QuestionsProcessedStats();
        try {
            String content = new String(file.getBytes());
            String [] splitted = content.strip().split("##");
            List<String>  qatexts = new LinkedList<>();
            for(String qa: splitted){
                qatexts.add(qa.strip());
            }
            List<String> headingAndTags = getSplitted(qatexts.get(0), "#");
            qatexts.remove(0);
            stats.setTotalCount(qatexts.size());
            List<QuestionAnswers> qaList = new LinkedList<>();
            for(String _qa: qatexts){
                List<String> _queAns = List.of(_qa.split("#"));
                List<String> _ans = _queAns.subList(1, _queAns.size());
                QuestionAnswers queAns = new QuestionAnswers(_queAns.get(0).strip(), _ans.stream().map(t->t.strip()).collect(Collectors.toList()));
                qaList.add(queAns);
            }
            stats.setSuccessCount(qaList.size());
            stats.setFailedCount(qatexts.size() - qaList.size());
            stats.setTimestamp(LocalDateTime.now());
            QuestionsSet questionsPaper = new QuestionsSet();
            questionsPaper.setHeading(headingAndTags.get(0));
            questionsPaper.setUserId(userId);
            questionsPaper.setTags(headingAndTags.subList(1,headingAndTags.size()));
            questionsPaper.setQaList(qaList);
            kafkaMessageSenderService.sendMessage("text-questions-processor", questionsPaper);
            return stats;
        } catch (IOException e) {
            log.error("Error while processing question file");
            stats.setTimestamp(LocalDateTime.now());
            return stats;
        }
    }
}
