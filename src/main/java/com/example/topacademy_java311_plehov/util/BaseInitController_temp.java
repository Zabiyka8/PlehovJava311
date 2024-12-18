package com.example.topacademy_java311_plehov.util;

import com.example.topacademy_java311_plehov.DAO.services.IngredientService;
import com.example.topacademy_java311_plehov.DAO.services.PizzaService;
import com.example.topacademy_java311_plehov.DAO.services.TechCartService;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Size;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Type;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Ingredient;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@RequestMapping("/s")
@RequiredArgsConstructor
public class BaseInitController_temp {

    private final PizzaService pizzaService;
    private final IngredientService ingredientService;
    private final TechCartService techCartService; // Добавьте это, если у вас нет сервиса для TechCart
    private Ingredient ingredientTemp;

    @GetMapping("/init")
    public void init() throws IOException {
        ingredientInit();

        if (pizzaService.findById(1).isEmpty()) {
            List.of("БАРБЕКЮ", "ВЕРОНА", "ГАВАЙСКАЯ", "МЯСНАЯ", "ПЕПЕРОНИ", "ЦЕЗАРЬ")
                    .forEach(pizza -> {
                        Pizza newPizza = pizzaService.save(Pizza.builder()
                                .name(pizza)
                                .price(new Random().nextInt(400, 1000))
                                .size(Size.S)
                                .type(Type.STANDARD)
                                .build());

                        List<Ingredient> selectedIngredients;

                        switch (pizza) {
                            case "БАРБЕКЮ":
                                selectedIngredients = List.of(ingredientService.findByName("Курица"), ingredientService.findByName("cоус"));
                                break;
                            case "ГАВАЙСКАЯ":
                                selectedIngredients = List.of(ingredientService.findByName("Ананас"), ingredientService.findByName("Ветчина"));
                                break;
                            case "ПЕПЕРОНИ":
                                selectedIngredients = List.of(ingredientService.findByName("Пепперони"));
                                break;

                            default:
                                selectedIngredients = List.of();
                        }

                        // Заполнение технической карты
                        for (Ingredient ingredient : selectedIngredients) {
                            if (ingredient != null) { // Проверка на null в случае, если ингредиент не найден
                                TechCart techCart = TechCart.builder()
                                        .pizza(newPizza)
                                        .amount(new Random().nextInt(1, 5)) // Случайное количество
                                        .ingredient(ingredient)
                                        .build();
                                techCartService.save(techCart); // Сохранение технической карты
                            }
                        }
                    });

        }
    }

    private void ingredientInit() throws IOException {
        String ingredientFile = "ingredient.txt";
        try (Stream<String> stream = Files.lines(Path.of(ingredientFile))) {
            List<String> lines = stream.toList();
            for (int i = 1; i < lines.size(); i++) {
                String temp = lines.get(i);
                String[] ingredientsProps = temp
                        .substring(1, temp.length() - 2)
                        .split(", ");
                ingredientTemp = Ingredient.builder()
                        .name(ingredientsProps[1])
                        .amount(Integer.valueOf(ingredientsProps[2]))
                        .price(Integer.parseInt(ingredientsProps[3]))
                        .inStock(Integer.valueOf(ingredientsProps[4]))
                        .build();
                ingredientService.save(ingredientTemp);
            }
        }
    }
}