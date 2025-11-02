package kitchgui.controller;

import kitchgui.model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private final Path dataDir;
    private final Path inventoryCsv;
    private final Path shoppingCsv;

    private final List<Item> inventory = new ArrayList<>();
    private final List<Item> shopping = new ArrayList<>();

    public InventoryService(Path dataDir) {
        this.dataDir = dataDir;
        this.inventoryCsv = dataDir.resolve("inventory.csv");
        this.shoppingCsv = dataDir.resolve("shopping.csv");
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<Item> getShopping() {
        return shopping;
    }

    public void loadAll() throws IOException {
        Files.createDirectories(dataDir);
        inventory.clear();
        shopping.clear();
        if (Files.exists(inventoryCsv)) {
            for (String line : Files.readAllLines(inventoryCsv, StandardCharsets.UTF_8)) {
                if (line.trim().isEmpty() || line.startsWith("Product"))
                    continue;
                try {
                    inventory.add(Item.fromCsv(line));
                } catch (Exception ignored) {
                }
            }
        }
        if (Files.exists(shoppingCsv)) {
            for (String line : Files.readAllLines(shoppingCsv, StandardCharsets.UTF_8)) {
                if (line.trim().isEmpty() || line.startsWith("Product"))
                    continue;
                try {
                    shopping.add(Item.fromCsv(line));
                } catch (Exception ignored) {
                }
            }
        }
    }

    public void saveAll() throws IOException {
        Files.createDirectories(dataDir);
        List<String> inv = new ArrayList<>();
        inv.add("Product,Brand,Category,Quantity,Expiry");
        for (Item it : inventory)
            inv.add(it.toCsv());
        Files.write(inventoryCsv, inv, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        List<String> shp = new ArrayList<>();
        shp.add("Product,Brand,Category,Quantity,Expiry");
        for (Item it : shopping)
            shp.add(it.toCsv());
        Files.write(shoppingCsv, shp, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void moveZeroQtyToShopping(int defaultQty) {
        List<Item> keep = new ArrayList<>();
        for (Item it : inventory) {
            if (it.getQuantity() <= 0) {
                shopping.add(new Item(it.getProduct(), it.getBrand(), it.getCategory(),
                        Math.max(defaultQty, 1), it.getExpiry()));
            } else {
                keep.add(it);
            }
        }
        inventory.clear();
        inventory.addAll(keep);
    }
}
