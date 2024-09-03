package airbnb.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "ViewPage_table")
@Data
public class ViewPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Integer price;
    private String roomStatus;
    private String reservationStatus;
}
