package eu.kamilsikora.financial.api.service.list.shopping;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import eu.kamilsikora.financial.api.mapper.ListMapper;
import eu.kamilsikora.financial.api.repository.list.shopping.ShoppingListElementRepository;
import eu.kamilsikora.financial.api.repository.list.shopping.ShoppingListRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListElementRepository shoppingListElementRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final UserHelperService userHelperService;
    private final ExceptionThrowingValidator validator;
    private final ListMapper listMapper;

    @Transactional
    public ResponseShoppingListDto createNewList(final UserPrincipal userPrincipal, final NewShoppingListDto newList) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        ShoppingList shoppingList = listMapper.mapToEntity(newList, user);
        validator.validate(shoppingList);
        user.addNewShoppingList(shoppingList);
        shoppingListRepository.save(shoppingList);
        return listMapper.mapToDto(shoppingList);
    }


}
