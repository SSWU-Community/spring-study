package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sungshin.sooon.service.FileUploader;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private final FileUploader fileUploader;

    @PostMapping("/single")
    public String uploadSingle(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return fileUploader.upload(multipartFile, "static");
    }
}
