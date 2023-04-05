package hanghae99.rescuepets.mail.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void send(Member postMember, String commenter) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, postMember.getEmail());
            message.setSubject(commenter + "이/가 댓글을 등록했습니다.");
            message.setText(setTemplate(commenter), "utf-8", "html");
        } catch (MessagingException e) {
            throw new CustomException(ExceptionMessage.MAIL_SEND_FAIL);
        }

        javaMailSender.send(message);
    }

    private String setTemplate(String commenter) {
        Context context = new Context();
        context.setVariable("commenter", commenter);
        return templateEngine.process("mail", context);
    }
}
