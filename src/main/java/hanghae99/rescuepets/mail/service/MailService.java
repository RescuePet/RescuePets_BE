package hanghae99.rescuepets.mail.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
import lombok.RequiredArgsConstructor;
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

    public void send(Member member) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, member.getEmail());
            message.setSubject("신고 알림");
            message.setText(setTemplate(member), "utf-8", "html");
        } catch (MessagingException e) {
            throw new CustomException(ExceptionMessage.MAIL_SEND_FAIL);
        }

        javaMailSender.send(message);
    }

    private String setTemplate(Member member) {
        Context context = new Context();
        context.setVariable("member", member.getNickname());
        return templateEngine.process("mail", context);
    }
}
