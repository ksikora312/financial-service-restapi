package eu.kamilsikora.financial.api.dto.list.shopping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShoppingListDto {
    private Long id;
    private Long categoryId;
    private Double value;
}
