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
                /*if (getOffer(offers, p).offerType == SpecialOfferType.THREE_FOR_TWO && getQuantityAsInt(p) >= 3) {
                    discount = new Discount(p, "3 for 2", - getDiscountTotal(catalog, p, 3));
                }
                else if (getOffer(offers, p).offerType == SpecialOfferType.X_FOR_AMOUNT && getQuantityAsInt(p) >= 2) {
                    discount = new Discount(p, "2 for " + getOffer(offers, p).argument, -getDiscountAmount(offers, catalog, p, 2));
                }
                else if (getOffer(offers, p).offerType == SpecialOfferType.X_FOR_AMOUNT && getQuantityAsInt(p) >= 5) {
                    discount = new Discount(p, 5 + " for " + getOffer(offers, p).argument, -getDiscountAmount(offers, catalog, p, 5));
                }
                else if (getOffer(offers, p).offerType == SpecialOfferType.PERCENT_DISCOUNT) {
                    discount = new Discount(p, getOffer(offers, p).argument + "% off", -getDiscountForPercent(catalog, p, offers));
                }*/

                if (getOffer(offers, p).getDiscount(getQuantity(p), p, getUnitPrice(catalog, p)) != null)
                    receipt.addDiscount(getOffer(offers, p).getDiscount(getQuantity(p), p, getUnitPrice(catalog, p)));
            }
        }
    }

    /*private double getTotal(Map<Product, Offer> offers, SupermarketCatalog catalog, Product p, int i) {
        return getOffer(offers, p).argument * getNumberOfXs(p, i) + getQuantityAsInt(p) % 2 * getUnitPrice(catalog, p);
    }

    private double getDiscountAmount(Map<Product, Offer> offers, SupermarketCatalog catalog, Product p, int i) {
        return getUnitPrice(catalog, p) * getQuantity(p) - getTotal(offers, catalog, p, i);
    }

    private double getDiscountTotal(SupermarketCatalog catalog, Product p, int i) {
        return getQuantity(p) * getUnitPrice(catalog, p) - ((getNumberOfXs(p, i) * 2 * getUnitPrice(catalog, p)) + getQuantityAsInt(p) % 3 * getUnitPrice(catalog, p));
    }

    private double getDiscountForPercent(SupermarketCatalog catalog, Product p, Map<Product, Offer> offers) {
        return getQuantity(p) * getUnitPrice(catalog, p) * getOffer(offers, p).argument / 100.0;
    }*/

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
