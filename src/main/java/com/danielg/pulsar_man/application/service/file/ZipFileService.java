package com.danielg.pulsar_man.application.service.file;

import com.danielg.pulsar_man.application.port.input.file.UploadZipFileUseCase;
import com.danielg.pulsar_man.utils.FileUtils;
import com.danielg.pulsar_man.utils.ZipUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ZipFileService implements UploadZipFileUseCase {
    private static final String RESOURCE_DIR = "src/main/resources/uploads/";

    @Override
    public Path saveAndUnzipFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!file.getOriginalFilename().endsWith(".zip")) {
            throw new IllegalArgumentException("File is not a zip file");
        }

        try {
            Path filePath = Paths.get(RESOURCE_DIR + file.getOriginalFilename());
            Path savedFile = Files.write(filePath, file.getBytes());
            String destDir = RESOURCE_DIR + "/" + FileUtils.getFileNameWithoutExtension(savedFile.toString());

            File unzippedFolder = ZipUtils.unzipFolder(savedFile.toString(), destDir);
            return unzippedFolder.toPath();
        } catch (IOException e) {
            throw new IOException("Failed to save file: " + e.getMessage());
        }
    }
}
