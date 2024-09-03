package airbnb.domain;

import airbnb.PaymentApplication;
import airbnb.domain.PaymentApproved;
import airbnb.domain.PaymentDenied;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Payment_table")
@Data
//<<< DDD / Aggregate Root
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;

    private Long reservationId;

    private Integer price;

    private String status;

    @PostPersist
    public void onPostPersist() {
        PaymentApproved paymentApproved = new PaymentApproved(this);
        paymentApproved.publishAfterCommit();

        PaymentDenied paymentDenied = new PaymentDenied(this);
        paymentDenied.publishAfterCommit();
    }

    public static PaymentRepository repository() {
        PaymentRepository paymentRepository = PaymentApplication.applicationContext.getBean(
            PaymentRepository.class
        );
        return paymentRepository;
    }

    //<<< Clean Arch / Port Method
    public static void payment(RoomReserved roomReserved) {
        //implement business logic here:

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        PaymentApproved paymentApproved = new PaymentApproved(payment);
        paymentApproved.publishAfterCommit();
        PaymentDenied paymentDenied = new PaymentDenied(payment);
        paymentDenied.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(roomReserved.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);

            PaymentApproved paymentApproved = new PaymentApproved(payment);
            paymentApproved.publishAfterCommit();
            PaymentDenied paymentDenied = new PaymentDenied(payment);
            paymentDenied.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
