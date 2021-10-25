package eu.kamilsikora.financial.api.dto.list.shopping;

import eu.kamilsikora.financial.api.entity.expenses.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShoppingListDto {
    private Long id;
    private Category category;
    private Double value;
}
