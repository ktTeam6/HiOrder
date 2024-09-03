package airbnb.domain;

import airbnb.RoomApplication;
import airbnb.domain.RoomDeleted;
import airbnb.domain.RoomRegistered;
import airbnb.domain.RoomUpdated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Room_table")
@Data
//<<< DDD / Aggregate Root
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private String status;

    @PostPersist
    public void onPostPersist() {
        RoomRegistered roomRegistered = new RoomRegistered(this);
        roomRegistered.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        RoomUpdated roomUpdated = new RoomUpdated(this);
        roomUpdated.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        RoomDeleted roomDeleted = new RoomDeleted(this);
        roomDeleted.publishAfterCommit();
    }

    public static RoomRepository repository() {
        RoomRepository roomRepository = RoomApplication.applicationContext.getBean(
            RoomRepository.class
        );
        return roomRepository;
    }

    //<<< Clean Arch / Port Method
    public void deleteRoom() {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
