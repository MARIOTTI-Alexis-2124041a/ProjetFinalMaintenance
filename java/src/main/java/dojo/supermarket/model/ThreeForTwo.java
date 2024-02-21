package dojo.supermarket.model;

public class ThreeForTwo extends Offer{

    int amount;
    public ThreeForTwo(Product product, double argument) {
        super(SpecialOfferType.THREE_FOR_TWO, product, argument);
    }

    @Override
    public Discount getDiscount(double quantity, Product p, double unitPrice) {
        if (offerType == SpecialOfferType.THREE_FOR_TWO && quantity >= 3) {
            return new Discount(p, "3 for 2", -getDiscountTotal(unitPrice, quantity));
        }
        else {
            return null;
        }
    }

    private double getDiscountTotal(double unitPrice, double quantity) {
        return quantity * unitPrice - ((getQuantityAsInt(quantity)/3 * 2 * unitPrice) + getQuantityAsInt(quantity) % 3 * unitPrice);
    }

    private int getQuantityAsInt(double quantity) {
        return (int) quantity;
    }
}