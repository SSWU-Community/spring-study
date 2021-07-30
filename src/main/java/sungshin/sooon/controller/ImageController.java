package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sungshin.sooon.util.S3Uploader;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController { //https://developers.kakao.com/docs/latest/ko/kakaostory/rest-api#upload-image참고. 이미지업로드와 스토리쓰기를 다른 api로 받음.
    private final S3Uploader s3Uploader;

    @PostMapping("")
    public ResponseEntity upload(@RequestPart("files") List<MultipartFile> files) throws IOException {
        List<String> uploadImageUrls = s3Uploader.upload(files, "static");
        return new ResponseEntity(uploadImageUrls, HttpStatus.CREATED);
    }
}
