package ch.so.agi.gretl.tasks;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import ch.so.agi.gretl.logging.GretlLogger;
import ch.so.agi.gretl.logging.LogEnvironment;
import ch.so.agi.gretl.steps.S3DownloadStep;
import ch.so.agi.gretl.steps.S3UploadStep;
import ch.so.agi.gretl.util.TaskUtil;

public class S3Download extends DefaultTask {
    protected GretlLogger log;

    @Input
    public String accessKey;
    
    @Input
    public String secretKey;

    @Input
    public String bucketName;
    
    @Input 
    public String key;
    
    @OutputDirectory
    public File downloadDir;
    
    @Input
    @Optional
    public String endPoint = "https://s3.eu-central-1.amazonaws.com";
    
    @Input
    public String region = "eu-central-1";
        
    @TaskAction
    public void upload() {
        log = LogEnvironment.getLogger(S3Download.class);

        if (accessKey == null) {
            throw new IllegalArgumentException("accessKey must not be null");
        }
        if (secretKey == null) {
            throw new IllegalArgumentException("secretKey must not be null");
        }
        if (downloadDir == null) {
            throw new IllegalArgumentException("downloadDir must not be null");
        }
        if (bucketName == null) {
            throw new IllegalArgumentException("bucketName must not be null");
        }
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        if (region == null) {
            throw new IllegalArgumentException("region must not be null");
        }        
                
        try {
            S3DownloadStep s3DownloadStep = new S3DownloadStep();
            s3DownloadStep.execute(accessKey, secretKey, bucketName, key, endPoint, region, downloadDir);
        } catch (Exception e) {
            log.error("Exception in S3Download task.", e);
            GradleException ge = TaskUtil.toGradleException(e);
            throw ge;
        }
    }
}
