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
public class AddProductServlet extends HttpServlet {

    private final ProductRepository productRepository;
    private final ViewBuilder viewBuilder;

    public AddProductServlet(final ProductRepository productRepository, final ViewBuilder viewBuilder) {
        this.productRepository = productRepository;
        this.viewBuilder = viewBuilder;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String name = request.getParameter("name");
        final long price = Long.parseLong(request.getParameter("price"));

        productRepository.saveProduct(new Product(name, price));
        viewBuilder.buildDefaultSuccessView(response.getWriter());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
