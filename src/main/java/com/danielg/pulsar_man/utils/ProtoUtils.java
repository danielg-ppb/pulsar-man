package com.danielg.pulsar_man.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ProtoUtils {
    static Logger logger = LoggerFactory.getLogger(ProtoUtils.class);

    public static void generateJavaFromProto(File protoFile) throws Exception {
        System.out.println(protoFile);

        File outputDir = new File("target/generated-sources/proto");
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Failed to create directory for generated sources: " + outputDir.getAbsolutePath());
            }
        }
        System.out.println("Compiling");

        ProcessBuilder processBuilder = new ProcessBuilder(
                "/Users/guilherme.daniel/.local/bin/protoc", //TODO: Have a VAR here instead of this full path
                "--proto_path=" + protoFile.getParent(),
                "--java_out=" + outputDir.getAbsolutePath(),
                protoFile.getAbsolutePath()
        );
        Process process = processBuilder.start();
        if (process.waitFor() != 0) {
            throw new IOException("Failed to compile proto file: " + new String(process.getErrorStream().readAllBytes()));
        }

        String javaSourcePath = "target/generated-sources/proto/";
        File javaFile = new File(javaSourcePath, "protobuf/MegaProto.java");

        // Path to the output directory for compiled .class files
        String classOutputPath = "target/compiled-classes/";
        File classOutputDir = new File(classOutputPath);
        if (!classOutputDir.exists()) {
            classOutputDir.mkdirs();
        }
        // Compile the .java file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("JavaCompiler is not available. Ensure you are using a JDK, not a JRE.");
        }
        int compileResult = compiler.run(null, null, null, "-d", classOutputPath, javaFile.getPath());
        if (compileResult != 0) {
            throw new RuntimeException("Compilation failed for: " + javaFile.getPath());
        }
        if (process.waitFor() != 0) {
            throw new IOException("Failed to compile proto file: " + new String(process.getErrorStream().readAllBytes()));
        }
    }

    //TODO: Pulsar really needs to update their protobuf version. It only supports GeneratedMessageV3
    public static Class<? extends GeneratedMessageV3> getClassFromProtoSchema(String schemaFile, String mainInnerClass)
            throws IOException, ClassNotFoundException {
        String targetPath = "target/compiled-classes/";
        URL[] urls = {new File(targetPath).toURI().toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls, ProtoUtils.class.getClassLoader());

        return (Class<? extends GeneratedMessageV3>) classLoader
                .loadClass("protobuf." + schemaFile + "$" + mainInnerClass);
    }
}
