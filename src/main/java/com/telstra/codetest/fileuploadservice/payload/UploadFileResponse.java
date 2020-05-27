package com.telstra.codetest.fileuploadservice.payload;


public class UploadFileResponse {
    private String fileName;
    private String fileType;
    private long size;
    private String status;
    private String description;

    public UploadFileResponse(String fileName, String fileType, long size, String status, String description ) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.status = status;
        this.description = description;

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
