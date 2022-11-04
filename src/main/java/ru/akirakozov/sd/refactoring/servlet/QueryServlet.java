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
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String command = request.getParameter("command");

        if ("max".equals(command)) {
            final Product product = productRepository.loadMaxByPrice();

            viewBuilder.buildMaxProductView(response.getWriter(), product);
        } else if ("min".equals(command)) {
            final Product product = productRepository.loadMinByPrice();

            viewBuilder.buildMinProductView(response.getWriter(), product);
        } else if ("sum".equals(command)) {
            final long priceSum = productRepository.loadPriceSum();

            viewBuilder.buildPricesSumView(response.getWriter(), priceSum);
        } else if ("count".equals(command)) {
            final int productsCount = productRepository.loadProductsCount();

            viewBuilder.buildProductsCountView(response.getWriter(), productsCount);
        } else {
            viewBuilder.buildInvalidCommandView(response.getWriter(), command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
