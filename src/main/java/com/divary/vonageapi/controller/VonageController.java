package com.divary.vonageapi.controller;

import com.divary.vonageapi.configuration.VonageClientConfiguration;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import com.vonage.client.voice.Call;
import com.vonage.client.voice.ncco.Ncco;
import com.vonage.client.voice.ncco.TalkAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class VonageController {

    @Autowired
    VonageClientConfiguration vonageClient;

    @Value("${your-phone-number}")
    String phoneNumber;

    private static final Logger logger = LoggerFactory.getLogger(VonageController.class);

    @PostMapping("/send-sms")
    public String send(){

        TextMessage message = new TextMessage("Vonage APIs", phoneNumber, "A text message sent using the Vonage SMS API");

        SmsSubmissionResponse response = vonageClient.vonageClientSms().getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK){
            logger.info("Message sent successfully.");
            return "Success";
        } else {
            logger.info("Message failed with error: " + response.getMessages().get(0).getErrorText());
            return "Failed";
        }
    }

    @PostMapping("/call")
    public String voice() throws IOException {

        Ncco ncco = new Ncco(TalkAction.builder("Voice here").build());

        vonageClient.vonageClientVoice().getVoiceClient().createCall(new Call(phoneNumber, phoneNumber, ncco));
        return "Success";
    }

}
