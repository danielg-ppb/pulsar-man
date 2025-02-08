package com.danielg.pulsar_man.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static File saveMultipartFile(MultipartFile multipartFile) throws IOException {
        String basePath = System.getProperty("user.dir");
        File targetDirectory = new File(basePath, "target/generated-sources/proto");

        if (!targetDirectory.exists()) {
            boolean created = targetDirectory.mkdirs();
            if (!created) {
                throw new IOException("Failed to create target directory: " + targetDirectory.getAbsolutePath());
            }
        }

        File targetFile = new File(targetDirectory, multipartFile.getOriginalFilename());

        multipartFile.transferTo(targetFile);

        return targetFile;
    }

    public static String getFileNameWithoutExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? fileName : fileName.substring(0, lastDotIndex);
    }
}
