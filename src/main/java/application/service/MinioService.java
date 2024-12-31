package application.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    String uploadFile(MultipartFile file);
    InputStream downloadFile(String bucketName, String key);
    void deleteFile(String bucketName, String key);
}
