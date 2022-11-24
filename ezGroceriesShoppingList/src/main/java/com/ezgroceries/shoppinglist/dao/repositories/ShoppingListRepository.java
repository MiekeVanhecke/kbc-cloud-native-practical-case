package com.ezgroceries.shoppinglist.dao.repositories;

import com.ezgroceries.shoppinglist.dao.entities.ShoppingListEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface ShoppingListRepository extends Repository<ShoppingListEntity, UUID> {
    public ShoppingListEntity save(ShoppingListEntity shoppingListEntity);

    public ShoppingListEntity findById(UUID shoppingListId);

    public List<ShoppingListEntity> findBy();
}
