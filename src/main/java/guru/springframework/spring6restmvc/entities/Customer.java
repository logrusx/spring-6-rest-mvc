package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue
    @UuidGenerator
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    private String customerName;

    private String email;

    @Version
    private Integer version;
    private Date creationDate;


}
