package com.ezgroceries.shoppinglist.dao.repositories;

import com.ezgroceries.shoppinglist.dao.entities.CocktailEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface CocktailRepository extends Repository<CocktailEntity, UUID> {

    public List<CocktailEntity> findByIdDrinkIn(List<String> ids);

    public CocktailEntity save(CocktailEntity newCocktailEntity);

    public CocktailEntity findById(UUID cocktailId);
}
