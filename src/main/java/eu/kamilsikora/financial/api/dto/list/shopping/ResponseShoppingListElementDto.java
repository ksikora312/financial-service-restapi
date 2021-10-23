package eu.kamilsikora.financial.api.dto.list.shopping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseShoppingListElementDto {
    private Long elementId;
    private String name;
    private String category;
    private Double value;
    private Boolean done;
}
