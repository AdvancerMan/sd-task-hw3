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
public class GetProductsServlet extends HttpServlet {

    private final ProductRepository productRepository;
    private final ViewBuilder viewBuilder;

    public GetProductsServlet(ProductRepository productRepository, final ViewBuilder viewBuilder) {
        this.productRepository = productRepository;
        this.viewBuilder = viewBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<html><body>");
        for (final Product product : productRepository.loadAll()) {
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
        }
        response.getWriter().println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
