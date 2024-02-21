package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {

    public Receipt() {
    }

    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public double getTotalPrice() {
        double total = 0.0;
        for (ReceiptItem item : items) {
            total += item.getTotalPrice();
        }
        for (Discount discount : discounts) {
            total += discount.getDiscountAmount();
        }
        return total;
    }

    public void addProduct(Product p, double quantity, double price, double totalPrice) {
        items.add(new ReceiptItem(p, quantity, price, totalPrice));
    }

    public List<ReceiptItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ReceiptItem item : items) {
            sb.append(item.getQuantity()).append(" ").append(item.getProduct().getName()).append(" ").append(item.getTotalPrice()).append("\n");
        }
        for (Discount discount : discounts) {
            sb.append(discount.getDescription()).append(" -").append(discount.getDiscountAmount()).append("\n");
        }
        sb.append("Total: ").append(getTotalPrice());
        return sb.toString();
    }
}
