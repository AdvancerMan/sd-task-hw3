package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.domain.ProductRepository;
import ru.akirakozov.sd.refactoring.domain.ProductRepositoryImpl;
import ru.akirakozov.sd.refactoring.domain.StubConnectionProvider;
import ru.akirakozov.sd.refactoring.domain.model.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServletTest extends ServletTest {

    @Before
    public void init() throws IOException {
        final ProductRepository productRepository = new ProductRepositoryImpl(new StubConnectionProvider());
        productRepository.saveProduct(new Product("name", 123));
    }

    @Test
    public void testDoGet() throws IOException {
        final GetProductsServlet servlet = new GetProductsServlet();

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);

        final String response = responseWriter.toString();
        Assert.assertTrue(response.contains("name"));
        Assert.assertTrue(response.contains("123"));
    }
}
