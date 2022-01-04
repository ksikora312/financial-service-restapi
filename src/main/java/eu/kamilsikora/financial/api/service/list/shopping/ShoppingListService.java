package eu.kamilsikora.financial.api.service.list.shopping;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.list.ResponseListCollectionOverview;
import eu.kamilsikora.financial.api.dto.list.ResponseListOverview;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.NewShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListCollectionDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ResponseShoppingListElementDto;
import eu.kamilsikora.financial.api.dto.list.shopping.ShoppingListToOutcomeDto;
import eu.kamilsikora.financial.api.dto.list.shopping.UpdateShoppingListDto;
import eu.kamilsikora.financial.api.dto.list.shopping.UpdateShoppingListElementDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.entity.expenses.ShoppingListSingleOutcome;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingList;
import eu.kamilsikora.financial.api.entity.list.shopping.ShoppingListElement;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.ListMapper;
import eu.kamilsikora.financial.api.repository.list.shopping.ShoppingListElementRepository;
import eu.kamilsikora.financial.api.repository.list.shopping.ShoppingListRepository;
import eu.kamilsikora.financial.api.repository.outcome.ShoppingListSingleOutcomeRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.service.outcome.CategoryService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListElementRepository shoppingListElementRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final UserHelperService userHelperService;
    private final ExceptionThrowingValidator validator;
    private final ListMapper listMapper;
    private final CategoryService categoryService;
    private final ShoppingListSingleOutcomeRepository shoppingListSingleOutcomeRepository;

    @Transactional
    public ResponseShoppingListDto createNewList(final UserPrincipal userPrincipal, final NewShoppingListDto newList) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        ShoppingList shoppingList = listMapper.mapToEntity(newList, user);
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
        final ShoppingListElement element = listMapper.mapToEntity(newShoppingListElement, shoppingList);
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
    public ResponseShoppingListCollectionDto getAllLists(final UserPrincipal userPrincipal, final Boolean withDone) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final List<ResponseShoppingListDto> shoppingListsDto = user.getShoppingLists().stream()
                .filter(list -> !list.getDone() || withDone)
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
    public ResponseShoppingListElementDto markElementAs(final UserPrincipal userPrincipal, final Long elementId, final Boolean done) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingListElement element = user.markShoppingListElementAs(elementId, done);

        return listMapper.mapToDto(element);
    }

    @Transactional
    public ResponseShoppingListDto markAsPrimary(final UserPrincipal userPrincipal, final Long listId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = user.markShoppingListAsPrimary(listId);
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional
    public ResponseShoppingListDto updateListDetails(final UserPrincipal userPrincipal, final UpdateShoppingListDto update) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = findListById(user.getShoppingLists(), update.getId())
                .orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        listMapper.mapIntoEntity(shoppingList, update);
        validator.validate(shoppingList);
        shoppingListRepository.save(shoppingList);
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional
    public ResponseShoppingListDto updateListName(final UserPrincipal userPrincipal, final Long listId, final String newName) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = findListById(user.getShoppingLists(), listId)
                .orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        shoppingList.setName(newName);
        validator.validate(shoppingList);
        shoppingListRepository.save(shoppingList);
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional
    public ResponseShoppingListDto updateElementDetails(final UserPrincipal userPrincipal,
                                                        final UpdateShoppingListElementDto updateElement) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList listContainingTheElement = findListContainingElement(user.getShoppingLists(), updateElement.getElementId())
                .orElseThrow(() -> new ObjectDoesNotExistException("Element does not belong to any of user's lists!"));
        final ShoppingListElement shoppingListElement = findElementInAllLists(user.getShoppingLists(), updateElement.getElementId())
                .orElseThrow(() -> new ObjectDoesNotExistException("Element does not belong to any of user's lists!"));
        listMapper.mapIntoEntity(shoppingListElement, updateElement);
        validator.validate(shoppingListElement);
        shoppingListElementRepository.save(shoppingListElement);
        return listMapper.mapToDto(listContainingTheElement);
    }

    @Transactional
    public ResponseShoppingListDto updateElementName(final UserPrincipal userPrincipal, final Long id, final String newName) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList listContainingTheElement = findListContainingElement(user.getShoppingLists(), id)
                .orElseThrow(() -> new ObjectDoesNotExistException("Element does not belong to any of user's lists!"));
        final ShoppingListElement shoppingListElement = findElementInAllLists(user.getShoppingLists(), id)
                .orElseThrow(() -> new ObjectDoesNotExistException("Element does not belong to any of user's lists!"));
        shoppingListElement.setName(newName);
        validator.validate(shoppingListElement);
        shoppingListElementRepository.save(shoppingListElement);
        return listMapper.mapToDto(listContainingTheElement);
    }

    @Transactional
    public ResponseListCollectionOverview getOverviews(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final List<ShoppingList> shoppingLists = user.getShoppingLists()
                .stream().filter(ShoppingList::getActive).collect(Collectors.toList());

        final List<ResponseListOverview> overviews = listMapper.mapShoppingListsToOverview(shoppingLists);
        return new ResponseListCollectionOverview(overviews);
    }

    @Transactional
    public void deleteList(final UserPrincipal userPrincipal, final Long listId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = user.getShoppingLists().stream()
                .filter(list -> list.getListId().equals(listId))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        shoppingListElementRepository.deleteAll(shoppingList.getElements());
        shoppingListRepository.delete(shoppingList);
        user.getShoppingLists().remove(shoppingList);
    }

    @Transactional
    public ResponseShoppingListDto deleteListElement(final UserPrincipal userPrincipal, final Long elementId) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = user.getShoppingLists().stream().filter(list -> doesListContainElement(list, elementId))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("Elements does not belong to any of user's lists"));
        final ShoppingListElement shoppingElement = shoppingList.getElements().stream()
                .filter(element -> element.getElementId().equals(elementId))
                .findFirst().get();
        shoppingList.getElements().remove(shoppingElement);
        shoppingListElementRepository.delete(shoppingElement);
        return listMapper.mapToDto(shoppingList);
    }

    @Transactional
    public void listToOutcome(final UserPrincipal userPrincipal, final ShoppingListToOutcomeDto content) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ShoppingList shoppingList = user.getShoppingLists().stream()
                .filter(list -> list.getListId().equals(content.getListId()))
                .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("List does not exist!"));
        final Category category = categoryService.resolveAndIncrementUsage(user, content.getCategoryId());
        final ShoppingListSingleOutcome outcome = mapToOutcome(user, content, shoppingList, category);
        shoppingList.setActive(false);
        shoppingListSingleOutcomeRepository.save(outcome);
    }

    private ShoppingListSingleOutcome mapToOutcome(final User user, final ShoppingListToOutcomeDto content,
                                                   final ShoppingList shoppingList, final Category category) {
        ShoppingListSingleOutcome outcome = new ShoppingListSingleOutcome();

        outcome.setName(content.getName());
        outcome.setOutcomeType(OutcomeType.SHOPPING_LIST_OUTCOME);
        outcome.setSource(shoppingList);
        outcome.setCategory(category);
        outcome.setDate(LocalDateTime.ofInstant(content.getDate().toInstant(), ZoneOffset.UTC).toLocalDate());
        outcome.setValue(content.getValue());
        outcome.setExpenses(user.getExpenses());

        return outcome;
    }


    private Optional<ShoppingListElement> findElementInAllLists(final List<ShoppingList> shoppingLists, final long elementId) {
        return findListContainingElement(shoppingLists, elementId)
                .stream()
                .map(ShoppingList::getElements)
                .flatMap(Collection::stream)
                .filter(element -> element.getElementId().equals(elementId))
                .findFirst();
    }

    private Optional<ShoppingList> findListContainingElement(final List<ShoppingList> shoppingLists, final long elementId) {
        return shoppingLists.stream()
                .filter(list -> doesListContainElement(list, elementId))
                .findFirst();
    }

    private boolean doesListContainElement(final ShoppingList shoppingList, final long elementId) {
        return shoppingList.getElements().stream()
                .anyMatch(element -> element.getElementId().equals(elementId));
    }

    private Optional<ShoppingList> findListById(final List<ShoppingList> shoppingLists, final Long id) {
        return shoppingLists.stream()
                .filter(list -> list.getListId().equals(id))
                .findFirst();
    }

}
