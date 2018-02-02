package org.superbiz.moviefun.albumsapi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.lang.String.format;


public class CoverCatalog {

    private BlobStore blobStore;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public CoverCatalog(BlobStore blobStore) {
        this.blobStore = blobStore;
    }

    public void uploadCover(long albumId, InputStream inputStream, String contentType) throws IOException {
        logger.debug("Uploading cover for album with id {}", albumId);

        Blob coverBlob = new Blob(
                coverBlobName(albumId),
                inputStream,
                contentType
        );

        blobStore.put(coverBlob);
    }

    @HystrixCommand(fallbackMethod = "buildDefaultCoverBlob")
    public Blob getCover(long albumId) throws IOException {
        Optional<Blob> maybeCoverBlob = blobStore.get(coverBlobName(albumId));
        Blob coverBlob = maybeCoverBlob.orElseGet(this::buildDefaultCoverBlob);
        return coverBlob;
    }

    Blob buildDefaultCoverBlob(long albumId) {
        return buildDefaultCoverBlob();
    }


    private Blob buildDefaultCoverBlob() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("default-cover.jpg");

        return new Blob("default-cover", input, MediaType.IMAGE_JPEG_VALUE);
    }

    private String coverBlobName(long albumId) {
        return format("covers/%d", albumId);
    }
}
