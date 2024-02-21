package dojo.supermarket.model;





import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

class ShoppingCartTest {

    private static Map<Product, Offer> offers;
    private static SupermarketCatalog catalog;

    private static Product toothbrush;
    private static Product apples;
    private static Product rice;
    private static Product dentifrice;
    private static Product cherryTomatoes;

    private static List<ShoppingCart> shoppingCarts = new ArrayList<>();

    private static List<Double> shoppingCartTotals = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        toothbrush = new Product("toothbrush", ProductUnit.EACH);
        apples = new Product("apples", ProductUnit.KILO);
        rice = new Product("rice", ProductUnit.EACH);
        dentifrice = new Product("dentifrice", ProductUnit.EACH);
        cherryTomatoes = new Product("cherry tomatoes", ProductUnit.KILO);

        offers = Map.of(
                toothbrush, new ThreeForTwo(toothbrush, 1.0),
                apples, new PercentDiscount(apples, 20.0),
                rice, new PercentDiscount(rice, 10.0),
                dentifrice, new XForAmount(dentifrice, 5, 7.49),
                cherryTomatoes, new XForAmount(cherryTomatoes, 2, 0.99)
        );

        catalog = new Catalog(Map.of(
                toothbrush, 0.99,
                apples, 1.99,
                rice, 2.49,
                dentifrice, 1.79,
                cherryTomatoes, 0.69
        ));

        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.addItemQuantity(toothbrush, 3);
        shoppingCart1.addItemQuantity(apples, 2.0);
        shoppingCart1.addItemQuantity(rice, 1.0);
        shoppingCart1.addItemQuantity(dentifrice, 5.0);
        shoppingCart1.addItemQuantity(cherryTomatoes, 2.0);
        shoppingCarts.add(shoppingCart1);
        shoppingCartTotals.add(15.885);

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.addItemQuantity(toothbrush, 3);
        shoppingCart2.addItemQuantity(apples, 2.0);
        shoppingCart2.addItemQuantity(rice, 1.0);
        shoppingCart2.addItemQuantity(dentifrice, 4.0);
        shoppingCart2.addItemQuantity(cherryTomatoes, 2.0);
        shoppingCarts.add(shoppingCart2);
        shoppingCartTotals.add(16.0);

        ShoppingCart shoppingCart3 = new ShoppingCart();
        shoppingCart3.addItemQuantity(toothbrush, 1);
        shoppingCart3.addItemQuantity(apples, 1.5);
        shoppingCart3.addItemQuantity(rice, 0.5);
        shoppingCart3.addItemQuantity(dentifrice, 7.0);
        shoppingCart3.addItemQuantity(cherryTomatoes, 4.0);
        shoppingCarts.add(shoppingCart3);
        shoppingCartTotals.add(20.0);

    }


    @Test
    public void testDiscount() {
        for (int i = 0; i < shoppingCarts.size() -1; i++) {
            Teller teller = new Teller(catalog);
            Receipt receipt = teller.checksOutArticlesFrom(shoppingCarts.get(i));
            System.out.println(shoppingCartTotals.get(i));
            assertEquals((double)shoppingCartTotals.get(i), receipt.getTotalPrice());
        }
    }


}