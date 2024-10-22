package org.framework.File;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.framework.exceptions.FileException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class FileParam {
     private Map<String, FileData> files = new HashMap<>();

    public void add(HttpServletRequest request, String paramName) throws Exception {
        Part filePart = request.getPart(paramName);

        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            long fileSize = filePart.getSize();

            // Debugging information to ensure file content is being received
            System.out.println("Received file: " + fileName);
            System.out.println("File size: " + fileSize);

            // Ensure that the file size is non-zero
            if (fileSize > 0) {
                // Read the content of the file into a byte array
                byte[] fileContent = readFileContent(filePart);

                // Store the file data in the map
                files.put(paramName, new FileData(fileName, fileContent, fileSize));
            } else {
                throw new FileException("File is empty for parameter: " + paramName);
            }
        } else {
            throw new FileException("File not found in request for parameter: " + paramName);
        }
    }

    // Helper method to read file content into byte[]
    private byte[] readFileContent(Part filePart) throws IOException {
        try (InputStream inputStream = filePart.getInputStream()) {
            return inputStream.readAllBytes(); // Reads the entire input stream into a byte array
        }
    }

    public File getFile(String paramName) throws Exception {
        FileData fileData = files.get(paramName);

        if (fileData == null) {
            throw new FileException("No file found for parameter: " + paramName);
        }

        // Correct file creation, create a temporary file instead of a directory
        File tempFile = File.createTempFile("upload_", "_" + fileData.getFileName());

        // Write the byte[] content into the temporary file
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileData.getFileContent()); // Write byte[] to the file
        }

        return tempFile;
    }

    public String getFileName(String paramName) {
        FileData fileData = files.get(paramName);
        if (fileData != null) {
            return fileData.getFileName();
        }
        return null; // Handle case where no file exists for the param
    }

    public class FileData {
        private String fileName;
        private byte[] fileContent;
        private long fileSize;

        public FileData(String fileName, byte[] fileContent, long fileSize) {
            this.fileName = fileName;
            this.fileContent = fileContent;
            this.fileSize = fileSize;
        }

        // Getters for file attributes
        public String getFileName() {
            return fileName;
        }

        public byte[] getFileContent() {
            return fileContent;
        }

        public long getFileSize() {
            return fileSize;
        }
    }
}
