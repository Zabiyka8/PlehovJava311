package com.example.topacademy_java311_plehov.util;

import com.example.topacademy_java311_plehov.DAO.services.IngredientService;
import com.example.topacademy_java311_plehov.DAO.services.PizzaService;
import com.example.topacademy_java311_plehov.DAO.services.TechCartService;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Size;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Type;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Ingredient;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
import com.example.topacademy_java311_plehov.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final ApplicationUserRepository appUserRepo;
//

    private final PasswordEncoder encoder;

    @GetMapping("/init")
    public void init() throws IOException {
        ingredientInit();
//        usersInit();
        if (pizzaService.findById(1).isEmpty()) {
            List.of("БАРБЕКЮ", "ВЕРОНА", "ГАВАЙСКАЯ", "МЯСНАЯ", "ПЕПЕРОНИ", "ЦЕЗАРЬ")
                    .forEach(pizza -> {
                        Pizza newPizza = pizzaService.save(Pizza.builder()
                                .name(pizza)
                                .price(new Random().nextInt(400, 1000))
                                .size(Size.S)
                                .type(Type.STANDARD)
                                .build());

                        List<Ingredient> selectedIngredients = switch (pizza) {
                            case "БАРБЕКЮ" -> List.of(
                                    ingredientService.findByName("куринная грудка"),
                                    ingredientService.findByName("сыр моцарелла"),
                                    ingredientService.findByName("бекон"),
                                    ingredientService.findByName("ветчина"),
                                    ingredientService.findByName("пепперони"),
                                    ingredientService.findByName("соус"));
                            case "ГАВАЙСКАЯ" -> List.of(
                                    ingredientService.findByName("сыр моцарелла"),
                                    ingredientService.findByName("ананас"),
                                    ingredientService.findByName("ветчина"),
                                    ingredientService.findByName("соус"));
                            case "ПЕПЕРОНИ" -> List.of(
                                    ingredientService.findByName("сыр моцарелла"),
                                    ingredientService.findByName("соус"),
                                    ingredientService.findByName("пепперони"));
                            case "МЯСНАЯ" -> List.of(
                                    ingredientService.findByName("говядина"),
                                    ingredientService.findByName("ветчина"),
                                    ingredientService.findByName("бекон"),
                                    ingredientService.findByName("соус"),
                                    ingredientService.findByName("свинина"),
                                    ingredientService.findByName("пепперони"));
                            case "ЦЕЗАРЬ" -> List.of(
                                    ingredientService.findByName("соус"),
                                    ingredientService.findByName("сыр моцарелла"),
                                    ingredientService.findByName("помидор"),
                                    ingredientService.findByName("куринная грудка"),
                                    ingredientService.findByName("бекон"));
                            case "ВЕРОНА" -> List.of(
                                    ingredientService.findByName("соус"),
                                    ingredientService.findByName("шампиньон"),
                                    ingredientService.findByName("помидор"),
                                    ingredientService.findByName("пепперони"));
                            default -> List.of();
                        };

                        for (Ingredient ingredient : selectedIngredients) {
                            if (ingredient != null) {
                                TechCart techCart = TechCart.builder()
                                        .pizza(newPizza)
                                        .amount(ingredient.getAmount())
                                        .ingredient(ingredient)
                                        .build();
                                techCartService.save(techCart);
                            }
                        }
                    });
        }
    }

    public void ingredientInit() throws IOException {
        String ingredientFile = "ingredient.txt";
        try (Stream<String> stream = Files.lines(Path.of(ingredientFile))) {
            List<String> lines = stream.toList();
            for (String line : lines) {
                String[] ingredientsProps = line
                        .substring(1, line.length() - 2)
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

    private void usersInit() {
        ApplicationUser admin = new ApplicationUser("employee@ya.ru", encoder.encode("user1"));
        ApplicationUser user = new ApplicationUser("user@ya.ru", encoder.encode("user"));

        appUserRepo.save(admin);
        appUserRepo.save(user);
    }

//    public void usersInit() {
//        // Создание или получение ролей
//        Role anonymousRole = new Role("ANONYMOUS", true, true, true); // Пример роли администратора
//        Role userRole = new Role("USER", false, false, false); // Пример роли пользователя
//
//        // Сохранение ролей, чтобы использовать их позже
//        // Если в базе уже существует эта роль, можно использовать другой механизм получения или удаления старой роли
//        // В противном случае роли будут сохранены сразу
//        anonymousRole = roleRepo.save(anonymousRole);
//        userRole = roleRepo.save(userRole);
//
//        // Создание профиля для пользователя
//        Profile anonymousProfile = Profile.builder()
//                .email("anonymous@ya.ru")
//                .name("Anonymous User")
//                .build();
//
//        Profile userProfile = Profile.builder()
//                .email("user@ya.ru")
//                .name("Normal User")
//                .build();
//
//        // Создание пользователей
//        User anonymous = User.builder()
//                .username("anonymous@ya.ru")
//                .password(encoder.encode("anonymous"))
//                .profile(anonymousProfile)
//                .role(userRole)  // Укажите роль пользователя
//                .build();
//
//        User user = User.builder()
//                .username("user@ya.ru")
//                .password(encoder.encode("user"))
//                .profile(userProfile)
//                .role(userRole)  // Укажите роль пользователя
//                .build();
//
//        // Сохранение пользователей в репозитории
//        userRepo.save(anonymous);
//        userRepo.save(user);
//    }
}