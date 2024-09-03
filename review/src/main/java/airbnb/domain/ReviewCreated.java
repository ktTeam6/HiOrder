package airbnb.domain;

import airbnb.domain.*;
import airbnb.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ReviewCreated extends AbstractEvent {

    private Long id;
    private Long roomId;
    private String content;
    private String customerId;

    public ReviewCreated(Review aggregate) {
        super(aggregate);
    }

    public ReviewCreated() {
        super();
    }
}
//>>> DDD / Domain Event
