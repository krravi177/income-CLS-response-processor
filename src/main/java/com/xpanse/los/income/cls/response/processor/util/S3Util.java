package com.xpanse.los.income.cls.response.processor.util;

import com.xpanse.los.income.cls.response.processor.exception.IncomeClsRespProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
public class S3Util {

    private final Logger logger = LoggerFactory.getLogger(S3Util.class);
    private final static String LOG_CLASS_REF = "income-cls-response-processor | S3Util -";
    private final S3Client s3Client;
    private final String incomeReqBucket;

    public S3Util(S3Client s3Client,
                  @Value("${aws.s3.income-response-store.bucket}") String incomeReqBucket) {
        this.s3Client = s3Client;
        this.incomeReqBucket = incomeReqBucket;
    }

    public ResponseInputStream<GetObjectResponse> getS3Object(String filepath) throws IncomeClsRespProcessorException {
        try {
            return s3Client.getObject(r -> r.bucket(incomeReqBucket).key(filepath).responseContentType("text/json"));
        } catch (S3Exception e) {
            logger.error("{} getS3Object method | {}", LOG_CLASS_REF, e.getMessage());
            throw new IncomeClsRespProcessorException("Failed to load data from s3 bucket");
        }
    }

}

