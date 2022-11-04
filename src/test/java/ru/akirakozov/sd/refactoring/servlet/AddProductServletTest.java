package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class AddProductServletTest extends ServletTest {

    @Test
    public void testDoGet() throws IOException {
        final AddProductServlet servlet = new AddProductServlet();

        parameters.put("name", "name");
        parameters.put("price", "123");

        expectContentType("text/html");
        expectStatus(HttpServletResponse.SC_OK);
        servlet.doGet(httpServletRequest, httpServletResponse);
        Assert.assertTrue(responseWriter.toString().toLowerCase(Locale.ROOT).contains("ok"));
    }
}
