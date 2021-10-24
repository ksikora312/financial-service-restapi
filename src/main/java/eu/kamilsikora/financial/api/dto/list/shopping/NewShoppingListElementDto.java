package eu.kamilsikora.financial.api.dto.list.shopping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewShoppingListElementDto {
    private Long listId;
    private String name;
    private Long categoryId;
    private Double value;
}
