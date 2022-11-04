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

    @Override
    public void buildAllProductsView(PrintWriter printWriter, List<Product> products) {
        printWriter.println("<html><body>");
        for (final Product product : products) {
            printWriter.println(product.getName() + "\t" + product.getPrice() + "</br>");
        }
        printWriter.println("</body></html>");
    }

    @Override
    public void buildMinProductView(PrintWriter printWriter, Product minProduct) {
        printWriter.println("<html><body>");
        printWriter.println("<h1>Product with min price: </h1>");
        printWriter.println(minProduct.getName() + "\t" + minProduct.getPrice() + "</br>");
        printWriter.println("</body></html>");
    }

    @Override
    public void buildMaxProductView(PrintWriter printWriter, Product maxProduct) {
        printWriter.println("<html><body>");
        printWriter.println("<h1>Product with max price: </h1>");
        printWriter.println(maxProduct.getName() + "\t" + maxProduct.getPrice() + "</br>");
        printWriter.println("</body></html>");
    }

    @Override
    public void buildPricesSumView(PrintWriter printWriter, long priceSum) {
        printWriter.println("<html><body>");
        printWriter.println("Summary price: ");
        printWriter.println(priceSum);
        printWriter.println("</body></html>");
    }

    @Override
    public void buildProductsCountView(PrintWriter printWriter, int productsCount) {
        printWriter.println("<html><body>");
        printWriter.println("Number of products: ");
        printWriter.println(productsCount);
        printWriter.println("</body></html>");
    }
}
