package dojo.supermarket.model;

import java.util.Map;

import java.util.HashMap;

public class Catalog implements SupermarketCatalog{

    Map<Product, Double> products;

    public Catalog(Map<Product, Double> products) {
        this.products = products;
    }

    public Catalog() {
        this.products = new HashMap<>();
    }
    public void addProduct(Product product, double price) {
        products.put(product, price);
    }

    public double getUnitPrice(Product product) {
        return products.get(product);
    }
}
