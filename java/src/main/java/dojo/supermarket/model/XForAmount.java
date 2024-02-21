package dojo.supermarket.model;

public class XForAmount extends Offer{
    double amount;
    public XForAmount(Product product, double argument, double amount) {
        super(SpecialOfferType.X_FOR_AMOUNT, product, argument);
        this.amount = amount;
    }

    @Override
    public Discount getDiscount(double quantity, Product p, double unitPrice) {
        if (quantity >= argument) {
            return new Discount(p, argument + " for the price of ", -getDiscountAmount(unitPrice, quantity));
        }
        else {
            return null;
        }
    }

    private double getDiscountAmount(double unitPrice, double quantity) {
        return unitPrice * quantity -  (amount * (getQuantityAsInt(quantity)/getArgumentAsInt()) + getQuantityAsInt(quantity) % argument * unitPrice);
    }

    private int getQuantityAsInt(double quantity) {
        return (int) quantity;
    }

    private int getArgumentAsInt() {
        return (int) argument;
    }
}

