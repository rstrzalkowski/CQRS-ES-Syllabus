package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.util;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

class MultipartInputStreamFileResource extends InputStreamResource {

    private final String filename;

    MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() {
        return -1;
    }
}
