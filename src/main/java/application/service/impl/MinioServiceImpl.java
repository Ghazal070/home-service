package application.service.impl;

import application.exception.ValidationException;
import application.service.MinioService;
import ch.qos.logback.core.testUtil.RandomUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Override
    public String uploadFile(String bucketName,MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        validateFile(file);
        String fileName = "home-service%s_%s_%d_%s".formatted(
                file.getOriginalFilename(),
                RandomString.make(1),
                ThreadLocalRandom.current().nextInt(1000, 2000),
                RandomString.make(1));
        PutObjectArgs.builder()
                .bucket()

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
        if (file.getSize() > 3 * 1024 * 1024) {
            throw new ValidationException("File is too large");
        }
        if (file.getContentType() == null || file.getContentType().isEmpty()) {
            throw new ValidationException("File content type is empty");
        }
        if (!file.getContentType().equalsIgnoreCase("jpg")) {
            throw new ValidationException("File content type is not jpg");
        }
    }
}
