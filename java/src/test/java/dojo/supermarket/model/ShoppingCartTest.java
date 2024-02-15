package dojo.supermarket.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junitparams.Parameters;
import junitparams.JUnitParamsRunner;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitParamsRunner.class)
class ShoppingCartTest {

    private static Map<Product, Offer> offers;

    @BeforeAll
    public static void setUp() {
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        Product apples = new Product("apples", ProductUnit.KILO);
        Product rice = new Product("rice", ProductUnit.EACH);
        Product dentifrice = new Product("dentifrice", ProductUnit.EACH);
        Product cherryTomatoes = new Product("cherry tomatoes", ProductUnit.KILO);

        offers = Map.of(
                toothbrush, new Offer(SpecialOfferType.THREE_FOR_TWO, toothbrush, 1.0),
                apples, new Offer(SpecialOfferType.TEN_PERCENT_DISCOUNT, apples, 10.0),
                rice, new Offer(SpecialOfferType.TWO_FOR_AMOUNT, rice, 2.0),
                dentifrice, new Offer(SpecialOfferType.FIVE_FOR_AMOUNT, dentifrice, 5.0),
                cherryTomatoes, new Offer(SpecialOfferType.TWENTY_PERCENT_DISCOUNT, cherryTomatoes, 20.0)
        );

    }

    private static final Object[] products() {
        return new Object[] {
                new Object[] { new Product("toothbrush", ProductUnit.EACH) },
                new Object[] { new Product("apples", ProductUnit.KILO) },
                new Object[] { new Product("bananas", ProductUnit.KILO) },
                new Object[] { new Product("oranges", ProductUnit.KILO) }
        };
    }

    @Test
    public void testEmptyShoppingCart() {
        ShoppingCart cart = new ShoppingCart();
        assertEquals(0, cart.getItems().size());
    }

    @Test
    @Parameters(method = "products")
    public void testTHREE_FOR_TWODiscount() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("toothbrush", ProductUnit.EACH);
        cart.addItemQuantity(product, 3);
        cart.handleOffers(new Offer(SpecialOfferType.THREE_FOR_TWO, product, 1.0));
        assertEquals(1.0, cart.getTotalPrice());
    }

}