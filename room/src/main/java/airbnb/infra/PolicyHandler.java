package airbnb.infra;

import airbnb.config.kafka.KafkaProcessor;
import airbnb.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    RoomRepository roomRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RoomReserved'"
    )
    public void wheneverRoomReserved_SendMessage(
        @Payload RoomReserved roomReserved
    ) {
        RoomReserved event = roomReserved;
        System.out.println(
            "\n\n##### listener SendMessage : " + roomReserved + "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RoomCancelled'"
    )
    public void wheneverRoomCancelled_SendMessage(
        @Payload RoomCancelled roomCancelled
    ) {
        RoomCancelled event = roomCancelled;
        System.out.println(
            "\n\n##### listener SendMessage : " + roomCancelled + "\n\n"
        );
        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
