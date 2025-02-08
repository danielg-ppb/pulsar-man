package com.danielg.pulsar_man.application.port.input.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface UploadZipFileUseCase {
    Path saveAndUnzipFile(MultipartFile file) throws IOException;

}
