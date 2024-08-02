//package wecloud.wishpool.domain.ses.service;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
//import com.amazonaws.services.simpleemail.model.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EmailService {
//
//    private final AmazonSimpleEmailService sesClient;
//
//    @Value("${cloud.ses.source-email}")
//    private String sourceEmail;
//
//    public EmailService(@Value("${cloud.aws.credentials.access-key}") String accessKey,
//                        @Value("${cloud.aws.credentials.secret-key}") String secretKey,
//                        @Value("${cloud.ses.region}") String region) {
//        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
//
//        this.sesClient = AmazonSimpleEmailServiceClientBuilder.standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//                .build();
//    }
//
//    public void sendEmail(List<String> to, String subject, String body) {
//        for (String recipient : to) {
//            SendEmailRequest request = new SendEmailRequest()
//                    .withDestination(new Destination().withToAddresses(recipient))
//                    .withMessage(new Message()
//                            .withBody(new Body()
//                                    .withHtml(new Content().withCharset("UTF-8").withData(body))
//                                    .withText(new Content().withCharset("UTF-8").withData(body)))
//                            .withSubject(new Content().withCharset("UTF-8").withData(subject)))
//                    .withSource(sourceEmail);
//
//            sesClient.sendEmail(request);
//        }
//    }
//}
