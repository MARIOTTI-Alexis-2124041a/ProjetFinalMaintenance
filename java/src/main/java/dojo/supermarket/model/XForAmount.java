package dojo.supermarket.model;

public abstract class XForAmount extends Offer{
    int amount;
    public XForAmount(SpecialOfferType offerType, Product product, double argument, int amount) {
        super(offerType, product, argument);
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
