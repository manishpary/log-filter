package com.coding.logfilter.service;

import com.coding.logfilter.dto.LogEntry;
import com.coding.logfilter.model.LogEvent;
import com.coding.logfilter.repository.LogEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogEntryService {
    private final LogEventRepository logEventRepository;
    private final ObjectMapper objectMapper;
    private final Map<String, LogEntry> logEntries = new HashMap<>();

    /***
     * Process input jsonString to LogEntry Object
     * @param line
     */
    public void processLineByLine(String line) {
        LogEntry logEntry;

        try {
            logEntry = objectMapper.readValue(line, LogEntry.class);
            if (logEntries.containsKey(logEntry.getId())) {
                mapToLogEntity(logEntries.get(logEntry.getId()), logEntry);
                logEntries.remove(logEntry.getId());
            } else {
                logEntries.put(logEntry.getId(), logEntry);
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to parse log string - {}", line);
            e.printStackTrace();
        }
    }

    /**
     * Map found log entries with same id to LogEvent
     * and save to database
     * @param existing
     * @param current
     */
    private void mapToLogEntity(LogEntry existing, LogEntry current) {
        long duration = Math.abs(existing.getTimestamp() - current.getTimestamp());
        boolean alert = duration > 4;

        LogEvent logEvent = new LogEvent(existing.getId(), duration, existing.getType(), existing.getHost(), alert);

        logEventRepository.save(logEvent);
    }


    public void validateAllLinesAreProcessed() {
        if (logEntries.size() > 0) {
            log.error("incomplete process");
        } else {
            log.info("processed successfully");
        }
    }


}
