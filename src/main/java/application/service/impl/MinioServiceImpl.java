package application.service.impl;

import application.exception.ValidationException;
import application.service.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class MinioServiceImpl implements MinioService {

    @Override
    public String uploadFile(MultipartFile file) {
        return "";
    }

    @Override
    public InputStream downloadFile(String bucketName, String key) {
        return null;
    }

    @Override
    public void deleteFile(String bucketName, String key) {

    }
    private void validateFile(MultipartFile file) {
        if (!file.isEmpty()) {
            throw new ValidationException("File is empty");
        }
        if (file.getSize() > 3*1024*1024) {
            throw new ValidationException("File is too large");
        }
        if (file.getContentType()==null || file.getContentType().isEmpty()){
            throw new ValidationException("File content type is empty");
        }
    }
}
