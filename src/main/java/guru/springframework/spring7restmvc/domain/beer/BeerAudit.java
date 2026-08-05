package guru.springframework.spring7restmvc.domain.beer;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import guru.springframework.spring6restmvcapi.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BeerAudit {

    @Id
    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)    ", updatable = false, nullable = false)
    private UUID auditId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)    ", updatable = false, nullable = false)
    private UUID id;

    private Integer version;

    @Size(max = 50)
    @Column(length = 50)
    private String beerName;

    @JdbcTypeCode(SqlTypes.SMALLINT)
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @CreationTimestamp
    private LocalDateTime createdDateAudit;

    private String principalName;

    private String auditEventType;
}
