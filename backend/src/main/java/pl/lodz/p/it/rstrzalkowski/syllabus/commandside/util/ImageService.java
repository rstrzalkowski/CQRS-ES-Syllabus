package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String saveImage(MultipartFile image) throws IOException;
}
