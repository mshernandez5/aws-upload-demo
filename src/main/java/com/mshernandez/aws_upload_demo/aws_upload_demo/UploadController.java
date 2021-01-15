package com.mshernandez.aws_upload_demo.aws_upload_demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@CrossOrigin
@RestController
public class UploadController
{

    @Value("${aws.s3.bucket}")
    private String awsBucketName;

    /**
     * Allows a client to upload a file to the
     * default directory.
     * 
     * @param uploadedFile An uploaded file.
     */
    @RequestMapping("api/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile uploadedFile)
    {
        // Check In Case No File Provided In Request
        if (uploadedFile == null)
        {
            return;
        }
        // Try To Get Binary Content Of Received File
        byte[] fileBytes;
        try
        {
            fileBytes = uploadedFile.getBytes();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            return;
        }
        // Map File Metadata Key/Value Properties Example
        Map<String, String> exampleMetadata = new HashMap<>();
        exampleMetadata.put("filename", uploadedFile.getOriginalFilename());
        // Attempt To Upload The File To S3
        String entityTag = uploadToS3(uploadedFile.getOriginalFilename(), fileBytes, exampleMetadata);
        System.out.println("Uploaded " + uploadedFile.getOriginalFilename() + ", Entity Tag: " + entityTag);
    }

    public String uploadToS3(String fileName, byte[] fileBytes, Map<String, String> metadata)
    {
        // Select S3 Upload Region
        Region region = Region.US_WEST_1;
        // Configure S3 Client Instance & Try To Upload File
        try (S3Client s3 = S3Client.builder()
                .region(region)
                .build())
        {
            // Form Upload Request
            PutObjectRequest uploadRequest = PutObjectRequest.builder()
                .bucket(awsBucketName)
                .key(fileName)
                .metadata(metadata)
                .build();
            // Upload File & Get Response
            PutObjectResponse uploadResponse = s3.putObject(uploadRequest, RequestBody.fromBytes(fileBytes));
            return uploadResponse.eTag();
        }
        catch (S3Exception e)
        {
            System.err.println(e.getMessage());
        }
        return "Error Uploading File!";
    }
}