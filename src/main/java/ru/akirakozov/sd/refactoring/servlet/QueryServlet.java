package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.domain.ProductRepository;
import ru.akirakozov.sd.refactoring.domain.model.Product;
import ru.akirakozov.sd.refactoring.view.ViewBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductRepository productRepository;
    private final ViewBuilder viewBuilder;

    public QueryServlet(final ProductRepository productRepository, final ViewBuilder viewBuilder) {
        this.productRepository = productRepository;
        this.viewBuilder = viewBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            final Product product = productRepository.loadMaxByPrice();

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");
        } else if ("min".equals(command)) {
            final Product product = productRepository.loadMinByPrice();

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            final long priceSum = productRepository.loadPriceSum();

            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");
            response.getWriter().println(priceSum);
            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            final int productsCount = productRepository.loadProductsCount();

            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");
            response.getWriter().println(productsCount);
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
