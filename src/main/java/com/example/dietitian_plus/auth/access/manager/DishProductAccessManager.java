package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.DishMessages;
import com.example.dietitian_plus.domain.dish.Dish;
import com.example.dietitian_plus.domain.dishesproducts.DishProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishProductAccessManager {

    private final SecurityUtils securityUtils;

    private final DishAccessManager dishAccessManager;

    public void checkCanReadDishWithProducts(Dish dish) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianDishOwnerRequest = dishAccessManager.isDietitianDishOwner(dish);

        if (!isAdminRequest && !isDietitianDishOwnerRequest) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

    public void checkCanAddProductToDish(Dish dish) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianDishOwnerRequest = dishAccessManager.isDietitianDishOwner(dish);

        if (!isAdminRequest && !isDietitianDishOwnerRequest) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

    public void checkCanUpdateDishProductEntry(DishProduct dishProduct) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianDishOwnerRequest = dishAccessManager.isDietitianDishOwner(dishProduct.getDish());

        if (!isAdminRequest && !isDietitianDishOwnerRequest) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

    public void checkCanDeleteDishProductEntry(DishProduct dishProduct) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianDishOwnerRequest = dishAccessManager.isDietitianDishOwner(dishProduct.getDish());

        if (!isAdminRequest && !isDietitianDishOwnerRequest) {
            throw new AccessDeniedException(DishMessages.YOU_HAVE_NO_ACCESS_TO_THIS_DISH);
        }
    }

}
