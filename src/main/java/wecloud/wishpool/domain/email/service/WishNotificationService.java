package wecloud.wishpool.domain.email.service;

import lombok.AllArgsConstructor;
import wecloud.wishpool.domain.funding.service.FundingService;
import wecloud.wishpool.domain.wish.repository.WishRepository;
import wecloud.wishpool.domain.wish.entity.Wish;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WishNotificationService {

    private final WishRepository wishRepository;
    private final FundingService fundingService;
    private final EmailService emailService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkWishesAndNotify() {
        List<Wish> completedWishes = wishRepository.findByIsCompletedTrueAndIsEndedTrue();

        for (Wish wish : completedWishes) {
            notifyParticipants(wish);
        }
    }

    private void notifyParticipants(Wish wish) {
        List<String> recipients = new ArrayList<>();

        // 생일자 이메일 추가
        recipients.add(wish.getUser().getEmail());

        // 펀딩자 이메일 추가
        List<String> userFundingEmail = fundingService.getUserFundingEmail(wish.getId());
        recipients.addAll(userFundingEmail);

        String subject = "[WishPool] 생일 펀딩이 종료되었습니다!";
        String body = "생일 펀딩이 종료되었으며, 목표 금액에 도달하였습니다. \n참여해 주셔서 감사합니다.";

        emailService.sendEmail(recipients, subject, body);
    }
}
