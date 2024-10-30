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

import java.nio.file.Path;

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
}
