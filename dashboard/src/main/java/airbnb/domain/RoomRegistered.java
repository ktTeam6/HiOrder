package airbnb.domain;

import airbnb.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RoomRegistered extends AbstractEvent {

    private Long id;
    private String name;
    private String description;
    private Integer price;
}
