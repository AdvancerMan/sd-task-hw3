package ru.akirakozov.sd.refactoring.view;

import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.io.PrintWriter;
import java.util.List;

public class HtmlViewBuilder implements ViewBuilder {
    @Override
    public void buildDefaultSuccessView(final PrintWriter printWriter) {
        printWriter.println("OK");
    }

    @Override
    public void buildInvalidCommandView(final PrintWriter printWriter, final String command) {
        printWriter.println("Unknown command: " + command);
    }

    private void buildDocument(final PrintWriter printWriter, final Runnable documentContentBuilder) {
        printWriter.println("<html><body>");
        documentContentBuilder.run();
        printWriter.println("</body></html>");
    }

    private void buildProduct(final PrintWriter printWriter, final Product product) {
        printWriter.println(product.getName() + "\t" + product.getPrice() + "</br>");
    }

    @Override
    public void buildAllProductsView(final PrintWriter printWriter, final List<Product> products) {
        buildDocument(printWriter, () -> {
            for (final Product product : products) {
                buildProduct(printWriter, product);
            }
        });
    }

    @Override
    public void buildMinProductView(final PrintWriter printWriter, final Product minProduct) {
        buildDocument(printWriter, () -> {
            printWriter.println("<h1>Product with min price: </h1>");
            buildProduct(printWriter, minProduct);
        });
    }

    @Override
    public void buildMaxProductView(final PrintWriter printWriter, final Product maxProduct) {
        buildDocument(printWriter, () -> {
            printWriter.println("<h1>Product with max price: </h1>");
            buildProduct(printWriter, maxProduct);
        });
    }

    @Override
    public void buildPricesSumView(final PrintWriter printWriter, final long priceSum) {
        buildDocument(printWriter, () -> {
            printWriter.println("Summary price: ");
            printWriter.println(priceSum);
        });
    }

    @Override
    public void buildProductsCountView(final PrintWriter printWriter, final int productsCount) {
        buildDocument(printWriter, () -> {
            printWriter.println("Number of products: ");
            printWriter.println(productsCount);
        });
    }
}
