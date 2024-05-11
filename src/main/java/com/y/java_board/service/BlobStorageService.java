package com.y.java_board.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.y.java_board.domain.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class BlobStorageService {

    private final BlobServiceClient blobServiceClient;
    private final BlobContainerClient blobContainerClient;
    private final String containerName;

    public String writeFile(Storage storage) {
        String path = "";
        try {
            path = getPath(storage);
            BlobClient blob = blobContainerClient.getBlobClient(path);
            blob.upload(storage.getInputStream(), false);
        } catch (Exception e) {
            log.error("[BlobStorageService] writeFile function");
            log.error("Message : " + e.getMessage());
        }
        return path;
    }

    public String updateFile(Storage storage) {
        String path = "";
        try {
            path = getPath(storage);
            BlobClient client = blobContainerClient.getBlobClient(path);
            client.upload(storage.getInputStream(), true);
        } catch (Exception e) {
            log.error("[BlobStorageService] updateFile function");
            log.error("Message : " + e.getMessage());
        }
        return path;
    }

    public byte[] readFile(String url) {
        byte[] result = new byte[0];
        try {
            BlobClient client = blobContainerClient.getBlobClient(url);
            result = client.downloadContent().toBytes();
        } catch (Exception e) {
            log.error("[BlobStorageService] readFile function");
            log.error("Message : " + e.getMessage());
        }
        return result;
    }

    public void delete(String url) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(url);
            blobClient.delete();
        } catch (Exception e) {
            log.error("[BlobStorageService] deleteFile function");
            log.error("Message : " + e.getMessage());
        }
        log.info("Blob is deleted Successfully");
    }

    public void createContainer(
    ) {
        try {
            blobServiceClient.createBlobContainer(containerName);
        } catch (Exception e) {
            log.error("[BlobStorageService] createContainer function");
            log.error("Message : " + e.getMessage());
        }
        log.info("[BlobStorageService] Container Created");
    }

    public void deleteContainer() {
        try {
            blobServiceClient.deleteBlobContainer(containerName);
        } catch (Exception e) {
            log.error("[BlobStorageService] deleteContainer function");
            log.error("Message : " + e.getMessage());
        }
        log.info("[BlobStorageService] Container Deleted");
    }

    private String getPath(Storage storage) throws IllegalAccessException {
        if (StringUtils.isNotBlank(storage.getPath())
                && StringUtils.isNotBlank(storage.getFileName())
        ) {
            return storage.getPath() + "/" + storage.getFileName();
        }
        throw new IllegalAccessException("[BlobStorageService] 존재하지 않는 파일 경로");
    }

}
