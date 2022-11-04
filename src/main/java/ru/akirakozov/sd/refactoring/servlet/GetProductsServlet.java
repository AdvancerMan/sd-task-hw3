package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.domain.ProductRepository;
import ru.akirakozov.sd.refactoring.domain.model.Product;
import ru.akirakozov.sd.refactoring.view.ViewBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductRepository productRepository;
    private final ViewBuilder viewBuilder;

    public GetProductsServlet(final ProductRepository productRepository, final ViewBuilder viewBuilder) {
        this.productRepository = productRepository;
        this.viewBuilder = viewBuilder;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final List<Product> products = productRepository.loadAll();
        viewBuilder.buildAllProductsView(response.getWriter(), products);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
