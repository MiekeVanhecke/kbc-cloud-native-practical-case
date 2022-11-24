package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.dao.CocktailDBResponse;
import com.ezgroceries.shoppinglist.dao.CocktailResource;
import com.ezgroceries.shoppinglist.dao.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.dao.repositories.CocktailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailService {
    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    public
    List<CocktailResource> mergeCocktails(List<CocktailDBResponse.DrinkResource>drinks){
        //Get all the idDrink attributes
        List<String> ids=drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap=cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getIdDrink, o->o,(o, o2)->o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap=drinks.stream().map(drinkResource->{
            CocktailEntity cocktailEntity=existingEntityMap.get(drinkResource.getIdDrink());
            if(cocktailEntity==null){
                CocktailEntity newCocktailEntity=new CocktailEntity(drinkResource.getIdDrink(), drinkResource.getStrDrink());
                newCocktailEntity.setIngredients(drinkResource.getAllIngredients());
                cocktailEntity=cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o->o,(o, o2)->o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks,allEntityMap);
    }

    private List<CocktailResource> mergeAndTransform(List<CocktailDBResponse.DrinkResource>drinks,Map<String, CocktailEntity> allEntityMap){
        return drinks.stream().map(drinkResource->new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getId().toString(),drinkResource.getStrDrink(),drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(),drinkResource.getStrDrinkThumb(),drinkResource.getAllIngredients())).collect(Collectors.toList());
    }

}
