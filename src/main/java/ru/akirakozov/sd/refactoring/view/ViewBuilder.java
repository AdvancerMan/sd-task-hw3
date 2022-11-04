package ru.akirakozov.sd.refactoring.view;

import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.io.PrintWriter;
import java.util.List;

public interface ViewBuilder {

    void buildDefaultSuccessView(final PrintWriter printWriter);

    void buildInvalidCommandView(final PrintWriter printWriter, final String command);

    void buildAllProductsView(final PrintWriter printWriter, final List<Product> products);

    void buildMinProductView(final PrintWriter printWriter, final Product minProduct);

    void buildMaxProductView(final PrintWriter printWriter, final Product maxProduct);

    void buildPricesSumView(final PrintWriter printWriter, final long priceSum);

    void buildProductsCountView(final PrintWriter printWriter, final int productsCount);
}
