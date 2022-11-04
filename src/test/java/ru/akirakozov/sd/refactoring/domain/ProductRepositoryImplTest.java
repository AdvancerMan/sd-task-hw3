package ru.akirakozov.sd.refactoring.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryImplTest {

    private ProductRepositoryImpl productRepository;

    @Before
    public void init() throws IOException {
        productRepository = new ProductRepositoryImpl(new StubConnectionProvider());
    }

    private List<Product> saveProducts() {
        final Product product1 = new Product("name", 1);
        productRepository.saveProduct(product1);
        productRepository.saveProduct(product1);
        productRepository.saveProduct(product1);

        final Product product2 = new Product("name2", 123);
        productRepository.saveProduct(product2);
        productRepository.saveProduct(product2);

        final Product product3 = new Product("name3", 12345);
        productRepository.saveProduct(product3);

        final List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product1);
        products.add(product1);
        products.add(product2);
        products.add(product2);
        products.add(product3);

        return products.stream().sorted().collect(Collectors.toList());
    }

    @Test
    public void testSaveAndGet() {
        final List<Product> expected = saveProducts();
        final List<Product> actual = productRepository
                .loadAll()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSaveAndMax() {
        final Product maxProduct = saveProducts()
                .stream()
                .max(Comparator.comparingInt(Product::getPrice))
                .orElseThrow(RuntimeException::new);
        Assert.assertEquals(maxProduct, productRepository.loadMaxByPrice());
    }

    @Test
    public void testSaveAndMin() {
        final Product minProduct = saveProducts()
                .stream()
                .min(Comparator.comparingInt(Product::getPrice))
                .orElseThrow(RuntimeException::new);
        Assert.assertEquals(minProduct, productRepository.loadMinByPrice());
    }

    @Test
    public void testSaveAndSum() {
        final int pricesSum = saveProducts()
                .stream()
                .mapToInt(Product::getPrice)
                .sum();
        Assert.assertEquals(pricesSum, productRepository.loadPriceSum());
    }

    @Test
    public void testSaveAndCount() {
        final List<Product> products = saveProducts();
        Assert.assertEquals(products.size(), productRepository.loadProductsCount());
    }
}
