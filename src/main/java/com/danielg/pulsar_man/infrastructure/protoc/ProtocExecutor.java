package com.danielg.pulsar_man.infrastructure.protoc;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProtocExecutor {
    private File protocExecutable;

    public ProtocExecutor() {
        try {
            // Extract the protoc directory from resources
            String protocResourcePath = "/protoc/bin/protoc"; // Path inside resources
            Path tempDir = Files.createTempDirectory("protoc");
            File binDir = new File(tempDir.toFile(), "bin"); // Preserve the bin directory structure
            if (!binDir.mkdirs()) {
                throw new IOException("Failed to create temp bin directory: " + binDir.getAbsolutePath());
            }

            this.protocExecutable = new File(binDir, "protoc");

            // Extract the protoc executable to the temp bin directory
            try (InputStream in = getClass().getResourceAsStream(protocResourcePath);
                 FileOutputStream out = new FileOutputStream(protocExecutable)) {
                if (in == null) {
                    throw new IllegalStateException("protoc not found in resources: " + protocResourcePath);
                }
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            // Ensure the protoc file is executable
            if (!protocExecutable.setExecutable(true)) {
                throw new IllegalStateException("Failed to make protoc executable: " + protocExecutable.getAbsolutePath());
            }

            System.out.println("Protoc executable path: " + protocExecutable.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract protoc binary", e);
        }
    }

    public void generateJavaClassesFromProto(List<File> protoFiles) throws Exception {
        Process process = createProtocProcess(protoFiles);
        // Compile the .java file
        compileProto(protoFiles, process);
    }

    private Process createProtocProcess(List<File> protoFiles) throws IOException, InterruptedException {
        File outputDir = new File("target/generated-sources/proto");
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Failed to create directory for generated sources: " + outputDir.getAbsolutePath());
            }
        }

        List<String> command = new ArrayList<>();
        command.add(this.protocExecutable.getAbsolutePath());
        command.add("--proto_path=" + protoFiles.getFirst().getParent());
        command.add("--java_out=" + outputDir.getAbsolutePath());

        for (File protoFile : protoFiles) {
            command.add(protoFile.getAbsolutePath());
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        if (process.waitFor() != 0) {
            throw new IOException("Failed to compile proto file: " + new String(process.getErrorStream().readAllBytes()));
        }
        return process;
    }

    private static void compileProto(List<File> protoFiles, Process process) throws InterruptedException, IOException {
        System.out.println("Compiling");
        String javaSourcePath = "target/generated-sources/proto/";

        for (File protoFile : protoFiles) {
            File javaFile = new File(javaSourcePath, "protobuf/" +
                    FilenameUtils.removeExtension(protoFile.getName()) + ".java");

            // Path to the output directory for compiled .class files
            String classOutputPath = "target/compiled-classes/";
            File classOutputDir = new File(classOutputPath);
            if (!classOutputDir.exists()) {
                classOutputDir.mkdirs();
            }
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("JavaCompiler is not available.");
            }
            int compileResult = compiler.run(null, null, null, "-d", classOutputPath, javaFile.getPath());
            if (compileResult != 0) {
                throw new RuntimeException("Compilation failed for: " + javaFile.getPath());
            }
            if (process.waitFor() != 0) {
                throw new IOException("Failed to compile proto file: " + new String(process.getErrorStream().readAllBytes()));
            }
        }
    }

    public Class<? extends GeneratedMessageV3> getClassFromProtoSchema(String schemaFile, String mainInnerClass)
            throws IOException, ClassNotFoundException {
        String targetPath = "target/compiled-classes/";
        URL[] urls = {new File(targetPath).toURI().toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());

        return (Class<? extends GeneratedMessageV3>) classLoader
                .loadClass("protobuf." + schemaFile + "$" + mainInnerClass);
    }


}
