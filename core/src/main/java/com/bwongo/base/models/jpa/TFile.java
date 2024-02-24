package com.bwongo.base.models.jpa;

import com.bwongo.base.models.enums.FileType;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
@Table(name = "t_file", schema = "core")
public class TFile extends AuditEntity{
    private String fileName;
    private String filePath;
    private String fileFormat;
    private boolean exists;
    private FileType fileType;
    private Long fileSize;

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    @Column(name = "file_format")
    public String getFileFormat() {
        return fileFormat;
    }

    @Column(name = "exists")
    public boolean isExists() {
        return exists;
    }

    @Column(name = "file_type")
    @Enumerated(EnumType.STRING)
    public FileType getFileType() {
        return fileType;
    }

    @Column(name = "file_size")
    public Long getFileSize() {
        return fileSize;
    }
}
