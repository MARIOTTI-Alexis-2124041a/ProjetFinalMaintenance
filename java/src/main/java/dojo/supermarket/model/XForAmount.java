package dojo.supermarket.model;

public class XForAmount extends Offer{
    double amount;
    public XForAmount(Product product, double argument, double amount) {
        super(SpecialOfferType.X_FOR_AMOUNT, product, argument);
        this.amount = amount;
    }

    @Override
    public Discount getDiscount(double quantity, Product p, double unitPrice) {
        if (offerType == SpecialOfferType.X_FOR_AMOUNT && quantity >= amount) {
            return new Discount(p, argument + "% off", -getDiscountAmount(unitPrice, quantity));
        }
        else {
            return null;
        }
    }

    private double getDiscountAmount(double unitPrice, double quantity) {
        return unitPrice * quantity - argument * (getQuantityAsInt(quantity)/amount) + getQuantityAsInt(quantity) % 2 * unitPrice;
    }

    private int getQuantityAsInt(double quantity) {
        return (int) quantity;
    }
}

