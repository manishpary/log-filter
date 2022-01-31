package com.coding.logfilter;

import com.coding.logfilter.service.LogEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class LogFilterApplication implements CommandLineRunner {
    private final LogEntryService logEntryService;

    public static void main(String[] args) {
        SpringApplication.run(LogFilterApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        log.info("Execution Started");
        if (Objects.isNull(args) || args.length != 1) {
            log.error("File path is not correctly provided.");
            throw new RuntimeException("Invalid File Path Parameter");
        }

        try (Stream<String> lines = Files.lines(Paths.get(args[0]))) {
            lines.parallel().forEach(logEntryService::processLineByLine);
        } catch (InvalidPathException e) {
            log.error("File path is invalid.");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("error in reading file");
            e.printStackTrace();
        }

        logEntryService.validateAllLinesAreProcessed();
    }
}
