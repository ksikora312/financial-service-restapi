package eu.kamilsikora.financial.api.dto.list.shopping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShoppingListElementDto {
    private Long elementId;
    private Double value;
    private Long categoryId;
}
