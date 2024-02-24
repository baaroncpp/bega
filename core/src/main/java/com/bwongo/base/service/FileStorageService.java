package com.bwongo.base.service;

import com.bwongo.base.config.StorageConfig;
import com.bwongo.base.models.enums.FileType;
import com.bwongo.base.models.jpa.TFile;
import com.bwongo.base.repository.TFileRepository;
import com.bwongo.commons.models.exceptions.BadRequestException;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringUtil;
import com.bwongo.commons.models.utils.Validate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.bwongo.base.utils.BaseEnumValidation.isFileType;
import static com.bwongo.base.utils.BaseMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/24/24
 **/
@Service
public class FileStorageService {

    @Value("${app.file-storage}")
    private String locationPath;
    private final Path rootLocation;
    private final TFileRepository fileRepository;

    public FileStorageService(StorageConfig storageConfig, TFileRepository fileRepository) {
        Validate.notEmpty(locationPath, FILE_STORAGE_PATH_NOT_FOUND);
        this.rootLocation = Paths.get(storageConfig.getLocation());
        this.fileRepository = fileRepository;
    }

    @Transactional
    public void store(MultipartFile file, String fileName, String fileType) {

        Validate.isTrue(isFileType(fileType), ExceptionType.BAD_REQUEST, UNACCEPTED_FILE_TYPE);
        Validate.notNull(file, ExceptionType.BAD_REQUEST, EMPTY_FILE);

        FileType fileTypeEnum = FileType.valueOf(fileType);
        var filePath = generateFilePath(fileTypeEnum);
        var originalFilename = file.getOriginalFilename();
        var fileSize = file.getSize();
        assert originalFilename != null;
        var extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);

        Path destinationFile = this.rootLocation.resolve(
                Paths
                        .get(filePath))
                        .normalize()
                        .toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new BadRequestException("Failed to store file.", e);
        }

        var document = new TFile();
        document.setFileName(fileName);
        document.setFilePath(filePath);
        document.setFileType(fileTypeEnum);
        document.setFileFormat(extension);
        document.setExists(Boolean.TRUE);
        document.setFileSize(fileSize);

        fileRepository.save(document);
    }

    public Path load(String filePath) {
        return rootLocation.resolve(filePath);
    }

    private String generateFilePath(FileType fileType){
        var filePath = "";
        do {
            filePath = fileType.name() + "_" + StringUtil.getRandom6DigitString();
        }while(fileRepository.existsByFilePath(filePath));

        return filePath;
    }
}
