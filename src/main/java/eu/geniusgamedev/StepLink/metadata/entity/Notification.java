package eu.geniusgamedev.StepLink.metadata.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {
    // TODO: Implement a different types of notifications - event/following etc.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    public Notification(String message, User user) {
        this.message = message;
        this.owner = user;

        // TODO: Implement user timezones and save everydate in UTC zone
        date = new Date();
    }
}
