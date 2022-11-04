package ru.akirakozov.sd.refactoring.view;

import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.io.PrintWriter;
import java.util.List;

public class HtmlViewBuilder implements ViewBuilder {
    @Override
    public void buildDefaultSuccessView(PrintWriter printWriter) {
        printWriter.println("OK");
    }

    @Override
    public void buildInvalidCommandView(PrintWriter printWriter, String command) {
        printWriter.println("Unknown command: " + command);
    }

    private void buildDocument(PrintWriter printWriter, Runnable documentContentBuilder) {
        printWriter.println("<html><body>");
        documentContentBuilder.run();
        printWriter.println("</body></html>");
    }

    private void buildProduct(PrintWriter printWriter, Product product) {
        printWriter.println(product.getName() + "\t" + product.getPrice() + "</br>");
    }

    @Override
    public void buildAllProductsView(PrintWriter printWriter, List<Product> products) {
        buildDocument(printWriter, () -> {
            for (final Product product : products) {
                buildProduct(printWriter, product);
            }
        });
    }

    @Override
    public void buildMinProductView(PrintWriter printWriter, Product minProduct) {
        buildDocument(printWriter, () -> {
            printWriter.println("<h1>Product with min price: </h1>");
            buildProduct(printWriter, minProduct);
        });
    }

    @Override
    public void buildMaxProductView(PrintWriter printWriter, Product maxProduct) {
        buildDocument(printWriter, () -> {
            printWriter.println("<h1>Product with max price: </h1>");
            buildProduct(printWriter, maxProduct);
        });
    }

    @Override
    public void buildPricesSumView(PrintWriter printWriter, long priceSum) {
        buildDocument(printWriter, () -> {
            printWriter.println("Summary price: ");
            printWriter.println(priceSum);
        });
    }

    @Override
    public void buildProductsCountView(PrintWriter printWriter, int productsCount) {
        buildDocument(printWriter, () -> {
            printWriter.println("Number of products: ");
            printWriter.println(productsCount);
        });
    }
}
