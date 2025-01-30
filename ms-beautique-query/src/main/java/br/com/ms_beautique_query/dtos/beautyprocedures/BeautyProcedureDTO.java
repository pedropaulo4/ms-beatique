package br.com.ms_beautique_query.dtos.beautyprocedures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "beautyprocedures")
public class BeautyProcedureDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
