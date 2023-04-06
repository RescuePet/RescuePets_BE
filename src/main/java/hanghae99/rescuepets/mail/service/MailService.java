package hanghae99.rescuepets.mail.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ExceptionMessage;
import hanghae99.rescuepets.common.entity.MailType;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.common.entity.PostTypeEnum;
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

    public void send(Post post, String commenter, String comment, MailType mailType) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, post.getMember().getEmail());
            message.setSubject(commenter + "이/가 댓글을 등록했습니다.");
            message.setText(setTemplate(post, commenter, comment), "utf-8", "html");
        } catch (MessagingException e) {
            throw new CustomException(ExceptionMessage.MAIL_SEND_FAIL);
        }

        javaMailSender.send(message);
    }

    private String setTemplate(Post post, String commenter, String comment) {
        String postType = post.getPostType() == PostTypeEnum.CATCH ? "목격" : "실종";
        Context context = new Context();
        context.setVariable("kind", post.getKindCd());
        context.setVariable("postType", postType);
        context.setVariable("commenter", commenter);
        context.setVariable("comment", comment);
        return templateEngine.process("mail", context);
    }
}
