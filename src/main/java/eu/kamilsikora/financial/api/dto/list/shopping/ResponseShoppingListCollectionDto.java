package eu.kamilsikora.financial.api.dto.list.shopping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseShoppingListCollectionDto {
    private List<ResponseShoppingListDto> shoppingLists;
}
