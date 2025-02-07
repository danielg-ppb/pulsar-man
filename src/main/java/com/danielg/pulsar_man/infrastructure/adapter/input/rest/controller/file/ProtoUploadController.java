package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.file;

import com.danielg.pulsar_man.application.port.input.file.UploadFileUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/file/proto")
public class ProtoUploadController {
    private final UploadFileUseCase uploadFileUseCase;

    @Autowired
    public ProtoUploadController(UploadFileUseCase uploadFileUseCase) {
        this.uploadFileUseCase = uploadFileUseCase;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path savedFile = uploadFileUseCase.saveFile(file);
            return new ResponseEntity<>("File uploaded successfully!" + savedFile.getFileName().toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        StringBuilder responseMessage = new StringBuilder();
        StringBuilder parsedContent = new StringBuilder();

        try {
            for (MultipartFile file : files) {
                String content = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining("\n"));

                boolean foundFirstMessage = false;

                for (String line : content.lines().toList()) {
                    if (line.startsWith("message ") || line.startsWith("enum ")) {
                        foundFirstMessage = true;
                    }

                    if (foundFirstMessage) {
                        parsedContent.append(line).append("\n");
                    }
                }

                // Print to console
                System.out.println("Proto File Content:\n" + parsedContent);

                responseMessage.append("File uploaded successfully: ").append(file.getOriginalFilename()).append("\n");
            }
            return new ResponseEntity<>(responseMessage.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
