package eu.kamilsikora.financial.api.controller.list.shopping;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.ResponseListCollectionOverview;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListCollectionDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.UpdateShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.UpdateShoppingListElementDto;
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

    @Override
    public ResponseShoppingListDto updateElement(final UserPrincipal userPrincipal, final UpdateShoppingListElementDto updateShoppingListElementDto) {
        return shoppingListService.updateElementDetails(userPrincipal, updateShoppingListElementDto);
    }

    @Override
    public ResponseShoppingListDto updateElementName(final UserPrincipal userPrincipal, final Long elementId, final String elementName) {
        return shoppingListService.updateElementName(userPrincipal, elementId, elementName);
    }

    @Override
    public ResponseShoppingListDto updateList(UserPrincipal userPrincipal, UpdateShoppingListDto updateShoppingListDto) {
        return shoppingListService.updateListDetails(userPrincipal, updateShoppingListDto);
    }

    @Override
    public ResponseShoppingListDto updateListName(final UserPrincipal userPrincipal, final Long listId, final String listName) {
        return shoppingListService.updateListName(userPrincipal, listId, listName);
    }

    @Override
    public ResponseShoppingListDto getPrimaryList(final UserPrincipal userPrincipal) {
        return shoppingListService.getPrimaryList(userPrincipal);
    }

    @Override
    public ResponseShoppingListCollectionDto getAllLists(final UserPrincipal userPrincipal, final Boolean withDone) {
        return shoppingListService.getAllLists(userPrincipal, withDone);
    }

    @Override
    public ResponseShoppingListDto getListAtId(final UserPrincipal userPrincipal, final Long listId) {
        return shoppingListService.getListById(userPrincipal, listId);
    }

    @Override
    public ResponseShoppingListElementDto markElementAs(final UserPrincipal userPrincipal, final Long elementId, final Boolean done) {
        return shoppingListService.markElementAs(userPrincipal, elementId, done);
    }

    @Override
    public ResponseShoppingListDto markAsPrimary(final UserPrincipal userPrincipal, final Long listId) {
        return shoppingListService.markAsPrimary(userPrincipal, listId);
    }

    @Override
    public ResponseListCollectionOverview getListsOverview(UserPrincipal userPrincipal) {
        return shoppingListService.getOverviews(userPrincipal);
    }

    @Override
    public void deleteList(UserPrincipal userPrincipal, Long listId) {
        shoppingListService.deleteList(userPrincipal, listId);
    }

    @Override
    public ResponseShoppingListDto deleteListElement(UserPrincipal userPrincipal, Long elementId) {
        return shoppingListService.deleteListElement(userPrincipal, elementId);
    }
}

