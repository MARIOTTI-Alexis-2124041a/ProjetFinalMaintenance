package dojo.supermarket.model;

public class PercentDiscount extends Offer{
    int amount;
    public PercentDiscount(Product product, double argument) {
        super(SpecialOfferType.PERCENT_DISCOUNT, product, argument);
    }

    @Override
    public Discount getDiscount(double quantity, Product p, double unitPrice) {
        if (offerType == SpecialOfferType.PERCENT_DISCOUNT) {
            return new Discount(p, argument + "% off", -getDiscountForPercent(unitPrice, quantity));
        }
        else {
            return null;
        }
    }

    private double getDiscountForPercent(double unitPrice, double quantity) {
        return quantity * unitPrice * argument / 100.0;
    }
}
