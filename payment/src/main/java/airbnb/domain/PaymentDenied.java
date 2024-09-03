package airbnb.domain;

import airbnb.domain.*;
import airbnb.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PaymentDenied extends AbstractEvent {

    private Long id;
    private String customerId;
    private Long reservationId;
    private Integer price;

    public PaymentDenied(Payment aggregate) {
        super(aggregate);
    }

    public PaymentDenied() {
        super();
    }
}
//>>> DDD / Domain Event
