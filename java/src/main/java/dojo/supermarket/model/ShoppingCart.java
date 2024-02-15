package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return Collections.unmodifiableList(items);
    }

    void addItem(Product product) {
        addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return Collections.unmodifiableMap(productQuantities);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            if (offers.containsKey(p)) {
                Discount discount = null;
                int i = 1;
                if (getOffer(offers, p).offerType == SpecialOfferType.THREE_FOR_TWO) {
                    i = 3;
                } else if (getOffer(offers, p).offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
                    i = 2;
                    if (getQuantityAsInt(p) >= 2) {
                        double total = getOffer(offers, p).argument * getNumberOfXs(p, i) + getQuantityAsInt(p) % 2 * getUnitPrice(catalog, p);
                        double discountN = getUnitPrice(catalog, p) * getQuantity(p) - total;
                        discount = new Discount(p, "2 for " + getOffer(offers, p).argument, -discountN);
                    }
                }
                if (getOffer(offers, p).offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
                    i = 5;
                }
                if (getOffer(offers, p).offerType == SpecialOfferType.THREE_FOR_TWO && getQuantityAsInt(p) > 2) {
                    double discountAmount = getQuantity(p) * getUnitPrice(catalog, p) - ((getNumberOfXs(p, i) * 2 * getUnitPrice(catalog, p)) + getQuantityAsInt(p) % 3 * getUnitPrice(catalog, p));
                    discount = new Discount(p, "3 for 2", -discountAmount);
                }
                if (getOffer(offers, p).offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
                    discount = new Discount(p, getOffer(offers, p).argument + "% off", -getQuantity(p) * getUnitPrice(catalog, p) * getOffer(offers, p).argument / 100.0);
                }
                if (getOffer(offers, p).offerType == SpecialOfferType.FIVE_FOR_AMOUNT && getQuantityAsInt(p) >= 5) {
                    double discountTotal = getUnitPrice(catalog, p) * getQuantity(p) - (getOffer(offers, p).argument * getNumberOfXs(p, i) + getQuantityAsInt(p) % 5 * getUnitPrice(catalog, p));
                    discount = new Discount(p, i + " for " + getOffer(offers, p).argument, -discountTotal);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }
        }
    }

    private int getNumberOfXs(Product p, int i) {
        return getQuantityAsInt(p) / i;
    }

    private int getQuantityAsInt(Product p) {
        return (int) getQuantity(p);
    }

    private static double getUnitPrice(SupermarketCatalog catalog, Product p) {
        return catalog.getUnitPrice(p);
    }

    private static Offer getOffer(Map<Product, Offer> offers, Product p) {
        return offers.get(p);
    }

    private double getQuantity(Product p) {
        return productQuantities.get(p);
    }
}
