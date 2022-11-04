package ru.akirakozov.sd.refactoring.domain;

import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.util.List;

public interface ProductRepository {

    void createProductTable();

    void saveProduct(final Product product);

    List<Product> loadAll();

    Product loadMaxByPrice();

    Product loadMinByPrice();

    long loadPriceSum();

    int loadProductsCount();
}
