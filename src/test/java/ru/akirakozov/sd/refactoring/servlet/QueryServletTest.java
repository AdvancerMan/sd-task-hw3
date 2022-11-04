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
import java.util.Locale;

public class QueryServletTest extends ServletTest {

    private QueryServlet servlet;

    @Before
    public void initQueryServletTest() throws IOException {
        final ProductRepository productRepository = new ProductRepositoryImpl(new StubConnectionProvider());
        productRepository.createProductTable();
        productRepository.saveProduct(new Product("name", 123));
        productRepository.saveProduct(new Product("name2", 321));

        final ViewBuilder viewBuilder = new HtmlViewBuilder();

        servlet = new QueryServlet(productRepository, viewBuilder);
    }

    @Test
    public void testDoGetUnknown() throws IOException {
        parameters.put("command", "unknown");

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);
        Assert.assertTrue(responseWriter.toString().toLowerCase(Locale.ROOT).contains("unknown command"));
    }

    @Test
    public void testDoGetMin() throws IOException {
        parameters.put("command", "min");

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);

        final String response = responseWriter.toString();
        Assert.assertTrue(response.contains("name"));
        Assert.assertTrue(response.contains("123"));
    }

    @Test
    public void testDoGetMax() throws IOException {
        parameters.put("command", "max");

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);

        final String response = responseWriter.toString();
        Assert.assertTrue(response.contains("name2"));
        Assert.assertTrue(response.contains("321"));
    }

    @Test
    public void testDoGetCount() throws IOException {
        parameters.put("command", "count");

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);

        final String response = responseWriter.toString();
        Assert.assertTrue(response.contains("2"));
    }

    @Test
    public void testDoGetSum() throws IOException {
        parameters.put("command", "sum");

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);

        final String response = responseWriter.toString();
        Assert.assertTrue(response.contains("444"));
    }
}
