package D20PTIT.hoidap.Service;

import ch.qos.logback.core.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        fileName = uuid.toString() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        return "ok";
    }

    public Set<String> uploadFiles(List<MultipartFile> multipartFiles) {
        Set<String> ans = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            UUID uuid = UUID.randomUUID();
            String fileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            fileName = uuid.toString() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
            ans.add(fileName);
            try {
                s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ans;
    }
}
