package org.framework.File;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.framework.exceptions.FileException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class FileParam {
    private Map<String,FileData> files =new HashMap<>();

    public void add(HttpServletRequest request,String paramName) throws Exception{
        Part filePart= request.getPart(paramName);

        if(filePart != null){
            String fileName=filePart.getSubmittedFileName();
            InputStream fileContent=filePart.getInputStream();
            long fileSize=filePart.getSize();

            files.put(paramName,new FileData(fileName,fileContent,fileSize));
        }else{
            throw new FileException("File not found in request for parameter:"+paramName);
        }
    }
    
    public File getFile(String paramName)throws Exception{
        FileData fileData= files.get(paramName);

        if (fileData==null) {
            throw new FileException("No file found for parameter:"+paramName);
        }

        File tempFile=Files.createTempDirectory(null, fileData.getFileName()).toFile();

        // Écrire le contenu du fichier dans le fichier temporaire
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream fileContent = fileData.getFileContent();
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }
    
    public String getFileName(String paramName){
        FileData fileData= files.get(paramName);
        return fileData.getFileName();
    }

    public class FileData {
        private String fileName;
        private InputStream fileContent;
        private long fileSize;

        public FileData(String fileName, InputStream fileContent, long fileSize) {
            this.fileName = fileName;
            this.fileContent = fileContent;
            this.fileSize = fileSize;
        }

        // Getters pour les attributs du fichier
        public String getFileName() {
            return fileName;
        }

        public InputStream getFileContent() {
            return fileContent;
        }

        public long getFileSize() {
            return fileSize;
        }
    }
}
