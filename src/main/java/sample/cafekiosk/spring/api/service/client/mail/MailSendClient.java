package sample.cafekiosk.spring.api.service.client.mail;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        log.info("메일전송");
        return true;
    }

    public void a() {
        log.info("a");
    }

    public void b() {
        log.info("b");
    }

    public void c() {
        log.info("c");
    }

}
