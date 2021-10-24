package eu.kamilsikora.financial.api.dto.list.shopping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewShoppingListDto {
    private String name;
    private Boolean isPrimary;
    private Long categoryId;
}
