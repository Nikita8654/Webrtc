package com.nikita.signallingserver.resource;

import com.nikita.signallingserver.model.InputMessage;
import com.nikita.signallingserver.model.OutputMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Slf4j
public class WebSocketController {

    @MessageMapping("/msg")
    @SendTo("/topic/reply")
    public OutputMessage handleClientMessages(InputMessage inputMessage){
        log.info("Message received: {}", inputMessage);
        return OutputMessage
                .builder()
                .from(inputMessage.getFrom())
                .content(inputMessage.getContent())
                .time(new SimpleDateFormat("HH:mm").format(new Date()))
                .build();
    }

    @MessageExceptionHandler
    @SendToUser("/topic/errors")
    public String handleException(Throwable exception) {
        log.error("Message exception handling, error: {}", exception.getMessage());
        return exception.getMessage();
    }
}
