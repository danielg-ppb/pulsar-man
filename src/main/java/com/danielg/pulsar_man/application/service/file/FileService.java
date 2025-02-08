package com.danielg.pulsar_man.application.service.file;

import com.danielg.pulsar_man.application.port.input.file.UploadFileUseCase;
import com.danielg.pulsar_man.application.port.input.file.UploadZipFileUseCase;
import com.danielg.pulsar_man.utils.FileUtils;
import com.danielg.pulsar_man.utils.ZipUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService implements UploadFileUseCase {
    private static final String RESOURCE_DIR = "src/main/resources/uploads/";

    @Override
    public Path saveFile(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(RESOURCE_DIR));
        File destinationFile = new File(RESOURCE_DIR + file.getOriginalFilename());

        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
            fileOutputStream.write(file.getBytes());
        }

        return destinationFile.toPath();

    }


}
