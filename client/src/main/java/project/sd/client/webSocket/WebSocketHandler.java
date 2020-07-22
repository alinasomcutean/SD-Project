package project.sd.client.webSocket;

import javafx.application.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project.sd.client.util.AlertBox;

@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String inMessage = message.getPayload();
        if(inMessage.startsWith("Book available now: ")) {
            Platform.runLater(() -> AlertBox.display("Book available", "Book " + inMessage.substring(20) + " is available now!"));
        }
        if(inMessage.startsWith("Blocked: ")) {
            Platform.runLater(() -> AlertBox.display("Blocked", "Your account has been blocked until " + inMessage.substring(9)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

    }

    public WebSocketSession getSession() {
        return session;
    }
}
