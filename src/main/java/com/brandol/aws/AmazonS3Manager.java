package com.brandol.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.brandol.apiPayload.code.status.ErrorStatus;
import com.brandol.apiPayload.exception.ErrorHandler;
import com.brandol.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    public String uploadFile(String keyName, MultipartFile file){ //S3 파일 업로드 함수
        log.info("keyName: "+ keyName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(),metadata));
        }catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    public String createFileName(String filename) { //S3 업로드 파일 이름 생성
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        return UUID.randomUUID() + "."+extension;
    }

    public void deleteFile(String keyName) {
        if(amazonS3.doesObjectExist(amazonConfig.getBucket(), keyName)) {
            log.info("keyName: "+ keyName);
            amazonS3.deleteObject(amazonConfig.getBucket(), keyName);
        } else {
            log.info("keyName: "+ keyName +"에 해당하는 파일이 없습니다");
        }
    }

    // avatar/ 디렉토리에 아바타 파일 이름 생성
    public String generateAvatarKeyName(String filename) {
        return amazonConfig.getAvatarPath() + "/" + createFileName(filename);
    }

    // url 에서 파일 이름 추출
    public String getKeyNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf("/");
        if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1);
        } else {
            throw new ErrorHandler(ErrorStatus._FILE_NAME_ERROR);
        }
    }

    // avatar/ 디렉토리의 아바타 파일 이름 추출
    public String getAvatarKeyName(String filename) {
        return amazonConfig.getAvatarPath() + "/" + getKeyNameFromUrl(filename);
    }
}
