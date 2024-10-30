package com.danielg.pulsar_man.utils;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ProtoUtils {
    static Logger logger = LoggerFactory.getLogger(ProtoUtils.class);

    public static void compileProtoFile(Path protoFilePath) throws IOException, InterruptedException {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path targetDir = currentPath.resolve("target/classes/com/danielg/pulsar_man");

        Files.createDirectories(targetDir);

        // Set up ProcessBuilder to compile the proto file
        ProcessBuilder processBuilder = new ProcessBuilder(
                "protoc",
                "--proto_path=" + protoFilePath.getParent().toAbsolutePath(),
                "--java_out=" + targetDir.toAbsolutePath(),
                protoFilePath.toAbsolutePath().toString()
        );

        // Start process
        Process process = processBuilder.start();

        // Log error output if any
        try (InputStream errorStream = process.getErrorStream()) {
            errorStream.transferTo(System.err);
        }

        // Check exit value
        if (process.waitFor() != 0) {
            throw new IOException("Failed to compile proto file.");
        }

        logger.info("Proto file compiled successfully to " + targetDir);
    }

    public static Descriptors.Descriptor loadProtoDescriptor(String protoFileName) throws Exception {
        Map<String, Descriptors.Descriptor> descriptors = new HashMap<>();
        // Step 1: Extract .proto file from resources
        File protoFile = extractProtoFile(protoFileName);

        // Step 2: Compile the .proto file to a temporary .desc file
        File descriptorFile = File.createTempFile("descriptor", ".desc");
        compileProto(protoFile, descriptorFile);

        // Step 3: Load the descriptor from the compiled .desc file
        try (InputStream is = Files.newInputStream(descriptorFile.toPath())) {
            DescriptorProtos.FileDescriptorSet descriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(is);
            Descriptors.FileDescriptor fileDescriptor = Descriptors.FileDescriptor.buildFrom(
                    descriptorSet.getFile(0), new Descriptors.FileDescriptor[]{}
            );
            Descriptors.Descriptor messageTypeDescriptor = fileDescriptor.getMessageTypes().get(0);
            descriptors.put(protoFileName, messageTypeDescriptor);
            return messageTypeDescriptor;
        } finally {
            // Clean up temporary files
            protoFile.delete();
            descriptorFile.delete();
        }
    }

    private static File extractProtoFile(String protoFileName) throws IOException {
        InputStream is = ProtoUtils.class.getClassLoader().getResourceAsStream(protoFileName);
        if (is == null) {
            throw new IllegalArgumentException("File not found: " + protoFileName);
        }
        File tempProtoFile = File.createTempFile("temp", ".proto");
        try (FileOutputStream os = new FileOutputStream(tempProtoFile)) {
            is.transferTo(os);
        }
        return tempProtoFile;
    }

    private static void compileProto(File protoFile, File descriptorFile) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "protoc",
                "--descriptor_set_out=" + descriptorFile.getAbsolutePath(),
                "--proto_path=" + protoFile.getParent(),
                protoFile.getAbsolutePath()
        );
        Process process = processBuilder.start();
        if (process.waitFor() != 0) {
            throw new IOException("Failed to compile proto file: " + new String(process.getErrorStream().readAllBytes()));
        }
    }
}
