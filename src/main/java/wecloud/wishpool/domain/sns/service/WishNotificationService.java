package wecloud.wishpool.domain.sns.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wecloud.wishpool.domain.funding.service.FundingService;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.domain.wish.repository.WishRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WishNotificationService {

    private final WishRepository wishRepository;
//    private final FundingService fundingService;
    private final SnsNotificationService snsNotificationService;


//    @Scheduled(fixedRate = 60000) // 1분마다 실행
//    @Transactional
//    public void checkWishesAndNotify() {
//        List<Wish> completedWishes = wishRepository.findByIsCompletedTrueOrIsEndedTrue();
//
//        for (Wish wish : completedWishes) {
//            notifyParticipants(wish);
//        }
//    }

//    public void notifyParticipants(Wish wish) {
//        List<String> recipients = new ArrayList<>();
//
//        // 생일자 이메일 추가
//        recipients.add(wish.getUser().getEmail());
//
//        // 펀딩자 이메일 추가
//        List<String> userFundingEmail = fundingService.getUserFundingEmail(wish.getId());
//
//        recipients.addAll(userFundingEmail);
//
//        System.out.println(recipients);
//
//        String subject = "[WishPool] 생일 펀딩이 종료되었습니다!";
//        String body = "생일 펀딩이 종료되었으며, 목표 금액에 도달하였습니다. \n참여해 주셔서 감사합니다.";
//
//        // SNS를 사용한 이메일 전송
//        String topicArn = "arn:aws:sns:ap-northeast-2:008971649853:funding-complete";
//        snsNotificationService.sendEmail(recipients, subject, body, topicArn);
//    }

    @Transactional
    public void notifyParticipants(Wish wish,List<String> userFundingEmail) {
//    List<String> recipients = new ArrayList<>();

    // 생일자 이메일 추가
//    recipients.add(wish.getUser().getEmail());

    // 펀딩자 이메일 추가
//    List<String> userFundingEmail = fundingService.getUserFundingEmail(wish.getId());

//    recipients.addAll(userFundingEmail);

    System.out.println(userFundingEmail);

    String subject = "[WishPool] 생일 펀딩이 종료되었습니다!";
    String body = "생일 펀딩이 종료되었으며, 목표 금액에 도달하였습니다. \n참여해 주셔서 감사합니다.";

    // SNS를 사용한 이메일 전송
    String topicArn = "arn:aws:sns:ap-northeast-2:008971649853:funding-complete";
    snsNotificationService.sendEmail(userFundingEmail, subject, body, topicArn);
}
}
