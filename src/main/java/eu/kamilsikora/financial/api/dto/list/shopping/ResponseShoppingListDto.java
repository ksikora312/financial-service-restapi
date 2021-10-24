package eu.kamilsikora.financial.api.dto.list.shopping;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseShoppingListDto {
    private Long listId;
    private String category;
    private String name;
    private Boolean done;
    private List<ResponseShoppingListElementDto> elements;
    private Boolean isPrimary;
}
