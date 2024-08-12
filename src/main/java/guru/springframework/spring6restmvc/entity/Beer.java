package guru.springframework.spring6restmvc.entity;

import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
//    @UuidGenerator
//    @GeneratedValue
//    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
//    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NonNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String beerName;

    @NonNull
    private BeerStyle beerStyle;

    @NonNull
    @NotBlank
    private String upc;
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;
}
