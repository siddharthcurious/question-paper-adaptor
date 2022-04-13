package com.numeratorx.adaptor.questionpaperadaptor.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestionsProcessedStats {
    private LocalDateTime timestamp;
    private int failedCount;
    private int successCount;
    private int totalCount;
    private String filename;
    private String username;

    public QuestionsProcessedStats(LocalDateTime timestamp, String filename,  String username) {
        this.timestamp = timestamp;
        this.filename = filename;
        this.username = username;
    }
}
