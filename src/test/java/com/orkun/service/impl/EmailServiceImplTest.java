package com.orkun.service.impl;

import com.orkun.dto.FileDTO;
import com.orkun.service.ReportService;
import com.orkun.utils.TestHelper;
import com.orkun.utils.email.EmailRequest;
import com.orkun.utils.email.EmailSender;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class EmailServiceImplTest {


    @Mock
    private EmailSender emailSender;

    @Mock
    private ReportService reportService;

    // @InjectMocks creates the mock object of the class and injects the mocks
    // that are marked with the annotations @Mock into it.
    @InjectMocks
    private EmailServiceImpl emailService;

    // @Captor used to capture and store the arguments passed to a mocked method for further assertions or verifications.
    @Captor
    private ArgumentCaptor<EmailRequest> emailRequestCaptor;

    private FileDTO fileDTO;


    @BeforeEach
    void setUp() throws IOException {

        String filePath = "jsonfile/mockedFileDTO.json";

        String fileName = TestHelper.extractJsonPropertyFromFile(filePath, "fileName");
        String fileContent = TestHelper.extractJsonPropertyFromFile(filePath, "fileContent");

        fileDTO = new FileDTO(fileName, fileContent);

    }



    @Test
    void givenNoInput_whenSendEmailWithoutAttachment_thenReturnTrue() {

        // given - precondition or setup
        willDoNothing().given(emailSender).sendEmail(any(EmailRequest.class));

        // when - action or behaviour that we are going to test
        Boolean result = emailService.sendEmailWithoutAttachment();

        // then - verify the output and interactions
        verify(emailSender, times(1)).sendEmail(emailRequestCaptor.capture());


        EmailRequest capturedEmailRequest = emailRequestCaptor.getValue();
        assertThat(capturedEmailRequest.getFrom()).isEqualTo("lyffy@pirateking.com");
        assertThat(capturedEmailRequest.getSubject()).isEqualTo("Test Email");
        assertThat(capturedEmailRequest.getEmailBody()).isEqualTo("email-template.html");
        assertThat(capturedEmailRequest.getToRecipients()).hasSize(1).contains("jwick@gmail.com");
        assertThat(capturedEmailRequest.getCcRecipients()).hasSize(1).contains("mpolo@gmail.com");
        assertThat(capturedEmailRequest.getDynamicVariables()).hasSize(2)
                .containsEntry("recipientName", "John Wick")
                .containsEntry("githubRepoUrl", "https://github.com/pmoustopoulos/department-api");
        assertThat(capturedEmailRequest.getImagePaths()).hasSize(1).containsEntry("logo", "reportLogo/luffy.png");
        assertThat(capturedEmailRequest.getAttachments()).isNull();

        assertThat(result).isTrue();
    }

    @Test
    void givenNoInput_whenSendEmailWithAttachment_thenReturnTrue() throws JRException {

        // given - precondition or setup
        given(reportService.generateDepartmentsExcelReport()).willReturn(fileDTO);
        given(reportService.generatePdfFullReport()).willReturn(fileDTO);
        willDoNothing().given(emailSender).sendEmail(any(EmailRequest.class));

        // when - action or behaviour that we are going to test
        Boolean result = emailService.sendEmailWithAttachment();

        // then - verify the output and interactions
        verify(emailSender, times(1)).sendEmail(emailRequestCaptor.capture());

        EmailRequest capturedEmailRequest = emailRequestCaptor.getValue();
        assertThat(capturedEmailRequest.getFrom()).isEqualTo("lyffy@pirateking.com");
        assertThat(capturedEmailRequest.getSubject()).isEqualTo("Test Email");
        assertThat(capturedEmailRequest.getEmailBody()).isEqualTo("email-template.html");
        assertThat(capturedEmailRequest.getToRecipients()).hasSize(2).contains("jwick@gmail.com", "maria@gmail.com");
        assertThat(capturedEmailRequest.getCcRecipients()).hasSize(2).contains("mpolo@gmail.com", "nick@gmail.com");
        assertThat(capturedEmailRequest.getDynamicVariables()).hasSize(2)
                .containsEntry("recipientName", "John Wick")
                .containsEntry("githubRepoUrl", "https://github.com/pmoustopoulos/department-api");
        assertThat(capturedEmailRequest.getImagePaths()).hasSize(1).containsEntry("logo", "reportLogo/luffy.png");
        assertThat(capturedEmailRequest.getAttachments()).isNotNull();

        assertThat(result).isTrue();
    }


}
