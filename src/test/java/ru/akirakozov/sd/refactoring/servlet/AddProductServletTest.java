package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.domain.ProductRepository;
import ru.akirakozov.sd.refactoring.domain.ProductRepositoryImpl;
import ru.akirakozov.sd.refactoring.domain.StubConnectionProvider;
import ru.akirakozov.sd.refactoring.view.HtmlViewBuilder;
import ru.akirakozov.sd.refactoring.view.ViewBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class AddProductServletTest extends ServletTest {

    private AddProductServlet servlet;

    @Before
    public void initAddProductServletTest() throws IOException {
        final ProductRepository productRepository = new ProductRepositoryImpl(new StubConnectionProvider());
        productRepository.createProductTable();

        final ViewBuilder viewBuilder = new HtmlViewBuilder();

        servlet = new AddProductServlet(productRepository, viewBuilder);
    }

    @Test
    public void testDoGet() throws IOException {
        parameters.put("name", "name");
        parameters.put("price", "123");

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);
        Assert.assertTrue(responseWriter.toString().toLowerCase(Locale.ROOT).contains("ok"));
    }
}
