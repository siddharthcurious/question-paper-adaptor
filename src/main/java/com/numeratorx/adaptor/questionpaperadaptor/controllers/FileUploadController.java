package com.numeratorx.adaptor.questionpaperadaptor.controllers;

import com.numeratorx.adaptor.questionpaperadaptor.responses.QuestionsProcessedStats;
import com.numeratorx.adaptor.questionpaperadaptor.responses.Response;
import com.numeratorx.adaptor.questionpaperadaptor.responses.StatusCode;
import com.numeratorx.adaptor.questionpaperadaptor.services.FilePreprocessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/question/paper/v1/")
public class FileUploadController {

    @Autowired
    private FilePreprocessingService filePreprocessingService;

    @PostMapping("/text/file/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestHeader("userId") String userId){
        QuestionsProcessedStats stats = filePreprocessingService.processTextFile(file, userId);
        stats.setFilename(file.getOriginalFilename());
        stats.setUsername(userId);
        Map<String, Object> data = new HashMap<>();
        if(stats.getSuccessCount() == stats.getTotalCount()) {
            data.put("questionsProcessedStats", stats);
            return new ResponseEntity<>(new Response(LocalDateTime.now(), "File processed successfully", StatusCode.OK, data), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new Response(LocalDateTime.now(), "Some error occurred while processing file", StatusCode.NOT_OK, data), HttpStatus.PARTIAL_CONTENT);
    }
}
