package kitchgui.controller;

import kitchgui.model.Item;
import kitchgui.model.Recipe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeService {
    private final Path dataDir;
    private final Path ingredientsDir;
    private final Path recipesDir;

    public RecipeService(Path dataDir) {
        this.dataDir = dataDir;
        this.ingredientsDir = dataDir.resolve("ingredients");
        this.recipesDir = dataDir.resolve("recipes");
    }

    private Path ingredientsCsv(int id) {
        return ingredientsDir.resolve("ingredients" + id + ".csv");
    }

    private Path recipeTxt(int id) {
        return recipesDir.resolve("recipe" + id + ".txt");
    }

    public Recipe load(int id, String name) throws IOException {
        Files.createDirectories(ingredientsDir);
        Files.createDirectories(recipesDir);
        Recipe r = new Recipe(id, name);

        Path ic = ingredientsCsv(id);
        if (Files.exists(ic)) {
            for (String line : Files.readAllLines(ic, StandardCharsets.UTF_8)) {
                if (line.trim().isEmpty() || line.startsWith("Product"))
                    continue;
                try {
                    r.getIngredients().add(Item.fromCsv(line));
                } catch (Exception ignored) {
                }
            }
        }
        Path rt = recipeTxt(id);
        if (Files.exists(rt)) {
            r.getSteps().addAll(Files.readAllLines(rt, StandardCharsets.UTF_8));
        }
        return r;
    }

    public void save(Recipe r) throws IOException {
        Files.createDirectories(ingredientsDir);
        Files.createDirectories(recipesDir);

        List<String> ings = new ArrayList<>();
        ings.add("Product,Brand,Category,Quantity,Expiry");
        ings.addAll(r.getIngredients().stream().map(Item::toCsv).collect(Collectors.toList()));
        Files.write(ingredientsCsv(r.getId()), ings, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        Files.write(recipeTxt(r.getId()), r.getSteps(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
