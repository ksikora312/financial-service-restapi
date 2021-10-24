package eu.kamilsikora.financial.api.service.list.shopping;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListCollectionDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingListElement;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.ListMapper;
import eu.kamilsikora.financial.api.repository.list.shopping.ShoppingListElementRepository;
import eu.kamilsikora.financial.api.repository.list.shopping.ShoppingListRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.service.outcome.CategoryService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListElementRepository shoppingListElementRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final UserHelperService userHelperService;
    private final CategoryService categoryService;
    private final ExceptionThrowingValidator validator;
    private final ListMapper listMapper;

    @Transactional
    public ResponseShoppingListDto createNewList(final UserPrincipal userPrincipal, final NewShoppingListDto newList) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final Category category = categoryService.resolveAndIncrementUsage(user, newList.getCategoryId());
        ShoppingList shoppingList = listMapper.mapToEntity(newList, user, category);
        validator.validate(shoppingList);
        user.addNewShoppingList(shoppingList);
        shoppingListRepository.save(shoppingList);
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional
    public ResponseShoppingListDto createNewElement(final UserPrincipal userPrincipal, final NewShoppingListElementDto newShoppingListElement) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final Long listId = newShoppingListElement.getListId();
        final ShoppingList shoppingList = findListById(user.getShoppingLists(), listId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Shopping list does not exist!"));
        final Category category = categoryService.resolveAndIncrementUsage(user, newShoppingListElement.getCategoryId());
        final ShoppingListElement element = listMapper.mapToEntity(newShoppingListElement, shoppingList, category);
        validator.validate(newShoppingListElement, element);
        shoppingList.addElement(element);
        shoppingListElementRepository.save(element);
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional(readOnly = true)
    public ResponseShoppingListDto getPrimaryList(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = user.getShoppingLists().stream()
                .filter(ShoppingList::getIsPrimary)
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional(readOnly = true)
    public ResponseShoppingListCollectionDto getAllLists(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final List<ResponseShoppingListDto> shoppingListsDto = user.getShoppingLists().stream()
                .map(listMapper::mapToDto)
                .collect(Collectors.toList());
        return new ResponseShoppingListCollectionDto(shoppingListsDto);
    }

    @Transactional(readOnly = true)
    public ResponseShoppingListDto getListById(final UserPrincipal userPrincipal, final Long id) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList list = findListById(user.getShoppingLists(), id)
                .orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        return listMapper.mapToDto(list);
    }

    @Transactional
    public ResponseShoppingListDto markElementAs(final UserPrincipal userPrincipal, final Long elementId, final Boolean done) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = user.markShoppingListElementAs(elementId, done);
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional
    public ResponseShoppingListDto markAsPrimary(final UserPrincipal userPrincipal, final Long listId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = user.markShoppingListAsPrimary(listId);
        return listMapper.mapToDto(shoppingList);
    }


    private Optional<ShoppingList> findListById(final List<ShoppingList> shoppingLists, final Long id) {
        return shoppingLists.stream()
                .filter(list -> list.getListId().equals(id))
                .findFirst();
    }

}
