package com.company.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance = new AuditService();

    private BufferedWriter bufferedWriter;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private AuditService(){
        try{
            Path path = Path.of("resources\\audit.csv");
            String header = "";
            if (!Files.exists(path)) {
                try {
                    Files.createFile(path);
                    header = "actionTime,action\n";
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            this.bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
            if(!header.isEmpty()) {
                bufferedWriter.write(header);
                bufferedWriter.flush();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static AuditService getInstance() {
        return instance;
    }

    public void logMessage(String actionName){
        try{
            bufferedWriter.write(String.format("%s, %s\n", LocalDateTime.now().format(DATE_FORMATTER), actionName));
            bufferedWriter.flush();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
