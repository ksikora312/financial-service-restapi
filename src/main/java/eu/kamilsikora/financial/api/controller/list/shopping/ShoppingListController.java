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
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/list/shopping")
@Api(tags = {"Shopping list controller"})
public interface ShoppingListController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseShoppingListDto createNewList(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewShoppingListDto newShoppingListDto);

    @PostMapping("/element")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseShoppingListDto addNewElement(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewShoppingListElementDto newShoppingListElementDto);

    @PutMapping("/element")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto updateElement(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateShoppingListElementDto updateShoppingListElementDto);

    @PatchMapping("/element/{id}/{name}")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto updateElementName(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @PathVariable("id") Long elementId,
                                              @PathVariable("name") String elementName);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto updateList(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateShoppingListDto updateShoppingListDto);

    @PatchMapping("/{id}/{name}")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto updateListName(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                           @PathVariable("id") Long listId,
                                           @PathVariable("name") String listName);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto getPrimaryList(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListCollectionDto getAllLists(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam(required = false, defaultValue = "false") Boolean withDone);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto getListAtId(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long listId);

    @PutMapping("/element/{id}/{state}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseShoppingListElementDto markElementAs(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long elementId, @PathVariable("state") Boolean done);

    @PutMapping("/primary/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto markAsPrimary(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long listId);

    @GetMapping("/overview")
    @ResponseStatus(HttpStatus.OK)
    ResponseListCollectionOverview getListsOverview(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteList(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long listId);

    @DeleteMapping("/element/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto deleteListElement(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long elementId);
}
