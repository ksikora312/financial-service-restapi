package eu.kamilsikora.financial.api.controller.list.shopping;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import eu.kamilsikora.financial.api.service.list.shopping.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShoppingListControllerImpl implements ShoppingListController {

    private final ShoppingListService shoppingListService;

    @Override
    public ResponseShoppingListDto createNewList(final UserPrincipal userPrincipal, final NewShoppingListDto newShoppingListDto) {
        return shoppingListService.createNewList(userPrincipal, newShoppingListDto);
    }

    @Override
    public ResponseShoppingListDto addNewElement(final UserPrincipal userPrincipal, final NewShoppingListElementDto newShoppingListElementDto) {
        return shoppingListService.createNewElement(userPrincipal, newShoppingListElementDto);
    }
}
