package productservice.rabbitmq;

import com.rabbitmq.client.Channel;
import productservice.exception.AcknowledgementCouldNotBeSetException;

import java.io.IOException;

public class MyAcknowledgement {

    public static void setAcknowledgement(Channel channel, long l, boolean b){
        try {
            channel.basicAck(l, b);
        } catch (IOException e) {
            throw new AcknowledgementCouldNotBeSetException();
        }
    }
}