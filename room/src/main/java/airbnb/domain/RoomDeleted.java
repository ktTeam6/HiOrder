package airbnb.domain;

import airbnb.domain.*;
import airbnb.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RoomDeleted extends AbstractEvent {

    private Long id;
    private String name;
    private String description;
    private Integer price;

    public RoomDeleted(Room aggregate) {
        super(aggregate);
    }

    public RoomDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
