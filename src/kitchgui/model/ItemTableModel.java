package kitchgui.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {
    private final String[] cols = { "Product", "Brand", "Category", "Quantity", "Expiry" };
    private final List<Item> rows = new ArrayList<>();

    public List<Item> getRows() {
        return new ArrayList<>(rows);
    }

    public void setAll(List<Item> items) {
        rows.clear();
        rows.addAll(items);
        fireTableDataChanged();
    }

    public Item get(int idx) {
        return rows.get(idx);
    }

    public void add(Item it) {
        rows.add(it);
        fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
    }

    public void update(int idx, Item it) {
        rows.set(idx, it);
        fireTableRowsUpdated(idx, idx);
    }

    public void remove(int idx) {
        rows.remove(idx);
        fireTableRowsDeleted(idx, idx);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    @Override
    public Object getValueAt(int r, int c) {
        Item it = rows.get(r);
        switch (c) {
            case 0:
                return it.getProduct();
            case 1:
                return it.getBrand();
            case 2:
                return it.getCategory();
            case 3:
                return it.getQuantity();
            case 4:
                return it.getExpiry();
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return c == 3 ? Integer.class : String.class;
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return false;
    }
}
