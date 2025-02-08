package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.file;

import com.danielg.pulsar_man.application.port.input.file.UploadFileUseCase;
import com.danielg.pulsar_man.application.port.input.file.UploadZipFileUseCase;
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
    private final UploadZipFileUseCase uploadZipFileUseCase;

    @Autowired
    public ProtoUploadController(UploadFileUseCase uploadFileUseCase, UploadZipFileUseCase uploadZipFileUseCase) {
        this.uploadFileUseCase = uploadFileUseCase;
        this.uploadZipFileUseCase = uploadZipFileUseCase;
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

    @PostMapping("/upload/zip")
    public ResponseEntity<String> uploadZipFile(@RequestParam("file") MultipartFile file) {
        try {
            Path savedFile = uploadZipFileUseCase.saveAndUnzipFile(file);
            return new ResponseEntity<>("Zip File uploaded successfully!" + savedFile.getFileName().toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Zip file upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
