# Lab 07 - Hardening

In this lab we'll work on the general robustness of our application.

## Persist all Cocktail attributes

Our runtime reliance on a free API is a thorn in the eye. It could go down at anytime, leaving our application useless.
Before we try to remedy this we first want to do some refactoring.

We want to persist **all** Cocktail attributes that are relevant for our application, this means:

* Adding database columns for the missing attributes (use extra ``Flyway`` scripts and ``alter table`` statements)
* Add the appropriate JPA Entity mappings for these attributes
* Make sure to copy them from the external API and persist them whenever we persist new ``CocktailEntity`` instances

These are all the missing attributes:

* glass
* instructions
* image link

## Circuit Breaker

We're going to apply the [Circuit Breaker pattern](https://en.wikipedia.org/wiki/Circuit_breaker_design_pattern) on our
outgoing CocktailDB API. After all, this is an external API and can be unreliable.

### Timeouts

If the external API is down we want our API calls to fail fast. We'll configure a timeout for these external calls, we
can provide both a ``connect`` and ``read`` timeout.

Add these to ``application.properties``:

```properties
# feign config
feign.hystrix.enabled=true
feign.client.config.default.connect-timeout=3000
feign.client.config.default.read-timeout=3000
feign.client.config.default.logger-level=basic
logging.level.com.ezgroceries.shoppinglist=DEBUG
```

These properties also enable some basic logging for all our external calls. Restart your application and do a
search: http://localhost:8080/cocktailResources?search=beach

You should see some ``feign`` logging, for example:

```
[CocktailDBClient#searchCocktails] ---> GET https://www.thecocktaildb.com/api/json/v1/1/search.php?s=beach HTTP/1.1
[CocktailDBClient#searchCocktails] <--- HTTP/1.1 200 OK (307ms)
```

In normal conditions the external API seems pretty fast, let's test our timeout by setting a ridiculously low value:

```properties
feign.client.config.default.connect-timeout=1
```

Restart and do the search again. It should fail and you should see:

```
java.net.SocketTimeoutException: connect timed out
```

There we go, our timeout is working. Make sure to set the property back to a reasonable value before continuing.

### Fallbacks

But what if the external APIs are down? What could we do? We can provide fallback values, since we recently refactored
to persist all relevant attributes: we could exclusively use the results in our database to answer search calls!

First have a look at
the [fallback documentation](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html#spring-cloud-feign-hystrix-fallback)
.

Here's an example of how to do this on our CocktailDBClient:

```java

@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1", fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);

    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }

        @Override
        public CocktailDBResponse searchCocktails(String search) {
            List<CocktailEntity> cocktailEntities = cocktailRepository.findByNameContainingIgnoreCase(search);

            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktailEntity -> {
                CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
                drinkResource.setIdDrink(cocktailEntity.getIdDrink());
                drinkResource.setStrDrink(cocktailEntity.getName());
                //...omitted, all attributes need to be mapped
                return drinkResource;
            }).collect(Collectors.toList()));

            return cocktailDBResponse;
        }
    }
}
``` 

So this basically means: any time the external call fails, we'll replace the call's expected response with one we create
ourselves, populated from a search on our database.

## Commit and tag your work

Commit your work: use the lab name as comment and tag it with the same name. Don't forget to push to Github.