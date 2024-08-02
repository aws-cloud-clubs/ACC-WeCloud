package wecloud.wishpool.domain.sns.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.exception.SdkException;

import java.util.List;

@Service
public class SnsNotificationService {

    private final SnsClient snsClient;

    public SnsNotificationService(@Value("${cloud.aws.credentials.access-key}") String accessKey,
                                  @Value("${cloud.aws.credentials.secret-key}") String secretKey,
                                  @Value("${cloud.aws.region.static}") String region) {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        this.snsClient = SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    // 이메일 전송 메서드
    public void sendEmail(List<String> recipients, String subject, String body, String topicArn) {
        for (String recipient : recipients) {
            try {
                // PublishRequest 생성
                PublishRequest request = PublishRequest.builder()
                        .message(body)
                        .subject(subject)
                        .targetArn(topicArn) // 수신자의 ARN
                        .build();

                // 메시지 전송
                PublishResponse result = snsClient.publish(request);

                System.out.println("Email sent to " + recipient + ". Message ID: " + result.messageId());
            } catch (SdkException e) {
                System.err.println("SNS Error: " + e.getMessage());
            }
        }
    }
}
