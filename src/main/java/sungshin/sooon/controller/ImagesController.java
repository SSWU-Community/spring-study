package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sungshin.sooon.service.AmazonS3Service;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImagesController {

    private final AmazonS3Service s3Uploader;

    @PostMapping("")
    public String upload(@RequestParam(value = "images") MultipartFile image) throws IOException {
        return s3Uploader.upload(image, "static");
    }
}
