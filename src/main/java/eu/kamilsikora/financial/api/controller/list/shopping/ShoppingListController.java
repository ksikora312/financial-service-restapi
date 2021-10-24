package eu.kamilsikora.financial.api.controller.list.shopping;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListCollectionDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto getPrimaryList(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListCollectionDto getAllLists(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto getListAtId(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long listId);

    @PutMapping("/element/{id}/{state}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseShoppingListDto markElementAs(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long elementId, @PathVariable("state") Boolean done);

    @PutMapping("/primary/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseShoppingListDto markAsPrimary(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long listId);
}
