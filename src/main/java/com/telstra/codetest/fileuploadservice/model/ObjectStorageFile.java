package com.telstra.codetest.fileuploadservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "object_storage")
public class ObjectStorageFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] file;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    private String status;

    public ObjectStorageFile() {

    }

    public ObjectStorageFile(String fileName, String fileType, byte[] fileData, String status) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.file = fileData;
        this.status = status;
    }

    @PrePersist
    void createdDate() {
        this.createdDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public byte[] getFile() {  return file; }

    public void setFile(byte[] file) {  this.file = file; }

    public Date getCreatedDate() { return createdDate; }

    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
