package airbnb.domain;

import airbnb.domain.*;
import airbnb.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RoomUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private String description;
    private Integer price;

    public RoomUpdated(Room aggregate) {
        super(aggregate);
    }

    public RoomUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
