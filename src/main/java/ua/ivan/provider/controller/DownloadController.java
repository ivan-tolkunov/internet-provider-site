package ua.ivan.provider.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.web.bind.annotation.PostMapping;
import ua.ivan.provider.config.MediaTypeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class DownloadController {

    private static final String DEFAULT_FILE_NAME = "F:\\Desktop\\Programming\\provider\\src\\main\\resources\\packages.txt";

    private ServletContext servletContext;

    @Autowired
    public DownloadController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {

        MediaType mediaType = MediaTypeConfig.getMediaTypeForFileName(this.servletContext, DEFAULT_FILE_NAME);
        File file = new File(DEFAULT_FILE_NAME);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}


