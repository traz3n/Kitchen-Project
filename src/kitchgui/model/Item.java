package kitchgui.model;

import java.util.Objects;

public class Item {
    private String product;
    private String brand;
    private String category;
    private int quantity;
    private String expiry;

    public Item(String product, String brand, String category, int quantity, String expiry) {
        this.product = product;
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.expiry = expiry;
    }

    public String getProduct() {
        return product;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String toCsv() {
        // escape commas by quoting fields that contain a comma
        return String.join(",",
                escape(product), escape(brand), escape(category),
                String.valueOf(quantity), escape(expiry));
    }

    public static Item fromCsv(String line) {
        // very small CSV parser for 5 columns (supports quotes)
        String[] cols = splitCsv(line, 5);
        int qty = 0;
        try {
            qty = Integer.parseInt(cols[3].trim());
        } catch (Exception ignored) {
        }
        return new Item(unescape(cols[0]), unescape(cols[1]), unescape(cols[2]), qty, unescape(cols[4]));
    }

    private static String escape(String s) {
        if (s == null)
            return "";
        if (s.contains(",") || s.contains("\"")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static String unescape(String s) {
        s = s == null ? "" : s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
        }
        return s;
    }

    private static String[] splitCsv(String line, int expected) {
        String[] out = new String[expected];
        StringBuilder sb = new StringBuilder();
        boolean inQ = false;
        int idx = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQ = !inQ;
                sb.append(c);
            } else if (c == ',' && !inQ) {
                out[idx++] = sb.toString();
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        out[idx] = sb.toString();
        for (int i = 0; i < out.length; i++)
            if (out[i] == null)
                out[i] = "";
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item))
            return false;
        Item other = (Item) o;
        return Objects.equals(product, other.product)
                && Objects.equals(brand, other.brand)
                && Objects.equals(category, other.category)
                && Objects.equals(expiry, other.expiry)
                && quantity == other.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, brand, category, quantity, expiry);
    }
}
