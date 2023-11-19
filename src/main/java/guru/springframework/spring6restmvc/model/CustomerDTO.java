package guru.springframework.spring6restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Customer {
    private String customerName;
    private UUID id;
    private Date creationDate;

}
