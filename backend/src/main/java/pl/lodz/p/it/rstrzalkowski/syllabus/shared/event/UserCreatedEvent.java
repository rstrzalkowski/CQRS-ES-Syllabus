package pl.lodz.p.it.rstrzalkowski.syllabus.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreatedEvent {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String personalId;
    private String imageUrl;
    private String description;
    private Timestamp createdAt;
}