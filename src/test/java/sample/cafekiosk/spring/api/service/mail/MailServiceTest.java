package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.api.service.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    public void sendMail() throws Exception{
        //given
//        when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);
//        when(mailSendHistoryRepository.save(Mockito.any())).thenReturn(any(MailSendHistory.class));
//        doReturn(true)
//                .when(mailSendClient)
//                .sendMail(anyString(), anyString(), anyString(), anyString());
        BDDMockito.given(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);


        //when
        boolean result = mailService.sendMail("", "", "", "");

        //then
        assertTrue(result);
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

}