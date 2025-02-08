package com.danielg.pulsar_man.utils;

import java.io.*;
import java.util.zip.*;

public class ZipUtils {

    public static File unzipFolder(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdirs(); // Ensure the destination folder exists
        }

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();

                if (entry.isDirectory()) {
                    // Create directories inside the destination folder
                    new File(filePath).mkdirs();
                } else {
                    // Extract file
                    extractFile(zipIn, filePath);
                }

                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }

            return destDir;
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        File extractedFile = new File(filePath);
        // Create parent directories if they don't exist
        extractedFile.getParentFile().mkdirs();

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = zipIn.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }
    }

}
