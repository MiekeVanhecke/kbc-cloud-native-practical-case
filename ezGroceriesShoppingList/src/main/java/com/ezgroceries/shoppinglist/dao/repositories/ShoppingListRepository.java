package com.ezgroceries.shoppinglist.dao.repositories;

import com.ezgroceries.shoppinglist.dao.entities.ShoppingListEntity;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface ShoppingListRepository extends Repository<ShoppingListEntity, UUID> {
}
