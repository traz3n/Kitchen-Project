package kitchgui.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private final int id;
    private String name;
    private final List<Item> ingredients = new ArrayList<>();
    private final List<String> steps = new ArrayList<>();

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }
}
