package hanghae99.rescuepets.mail.controller;

import hanghae99.rescuepets.common.security.MemberDetails;
import hanghae99.rescuepets.mail.service.MailService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메일 테스트")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class MailController {

    private final MailService mailService;

    @PostMapping(value = "/mail")
    public void sendMail(@Parameter(hidden = true) @AuthenticationPrincipal MemberDetails memberDetails) {
        mailService.send(memberDetails.getMember());
    }
}
