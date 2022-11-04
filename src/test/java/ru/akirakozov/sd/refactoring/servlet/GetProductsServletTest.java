package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.domain.ProductRepository;
import ru.akirakozov.sd.refactoring.domain.ProductRepositoryImpl;
import ru.akirakozov.sd.refactoring.domain.StubConnectionProvider;
import ru.akirakozov.sd.refactoring.domain.model.Product;
import ru.akirakozov.sd.refactoring.view.HtmlViewBuilder;
import ru.akirakozov.sd.refactoring.view.ViewBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServletTest extends ServletTest {

    private GetProductsServlet servlet;

    @Before
    public void initGetProductsServletTest() throws IOException {
        final ProductRepository productRepository = new ProductRepositoryImpl(new StubConnectionProvider());
        productRepository.createProductTable();
        productRepository.saveProduct(new Product("name", 123));

        final ViewBuilder viewBuilder = new HtmlViewBuilder();

        servlet = new GetProductsServlet(productRepository, viewBuilder);
    }

    @Test
    public void testDoGet() throws IOException {
        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);

        final String response = responseWriter.toString();
        Assert.assertTrue(response.contains("name"));
        Assert.assertTrue(response.contains("123"));
    }
}
