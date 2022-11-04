package ru.akirakozov.sd.refactoring.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class HtmlViewBuilderTest {

    private HtmlViewBuilder htmlViewBuilder;

    private PrintWriter resultWriter;

    private StringWriter resultStringWriter;

    @Before
    public void init() {
        htmlViewBuilder = new HtmlViewBuilder();
        resultStringWriter = new StringWriter();
        resultWriter = new PrintWriter(resultStringWriter);
    }

    private void assertResultIgnoreLineBreaks(final String expected) {
        final String actual = resultStringWriter.toString().replace("\n", "");
        final String expectedProcessed = expected.replace("\n", "");
        Assert.assertEquals(expectedProcessed, actual);
    }

    @Test
    public void testBuildDefaultSuccessView() {
        htmlViewBuilder.buildDefaultSuccessView(resultWriter);
        assertResultIgnoreLineBreaks("OK");
    }

    @Test
    public void testBuildAllProductsView() {
        final List<Product> products = new ArrayList<>();
        products.add(new Product("name1", 1));
        products.add(new Product("name2", 2));
        products.add(new Product("name3", 3));

        htmlViewBuilder.buildAllProductsView(resultWriter, products);
        assertResultIgnoreLineBreaks(
                "<html><body>" +
                        "name1\t1</br>" +
                        "name2\t2</br>" +
                        "name3\t3</br>" +
                        "</body></html>"
        );
    }

    @Test
    public void testBuildMinProductView() {
        htmlViewBuilder.buildMinProductView(resultWriter, new Product("name", 123));
        assertResultIgnoreLineBreaks(
                "<html><body>" +
                        "<h1>Product with min price: </h1>" +
                        "name\t123</br>" +
                        "</body></html>"
        );
    }

    @Test
    public void testBuildMaxProductView() {
        htmlViewBuilder.buildMinProductView(resultWriter, new Product("name", 123));
        assertResultIgnoreLineBreaks(
                "<html><body>" +
                        "<h1>Product with max price: </h1>" +
                        "name\t123</br>" +
                        "</body></html>"
        );
    }

    @Test
    public void testBuildPricesSumView() {
        htmlViewBuilder.buildPricesSumView(resultWriter, 123);
        assertResultIgnoreLineBreaks(
                "<html><body>" +
                        "Summary price: " +
                        "123" +
                        "</body></html>"
        );
    }

    @Test
    public void testBuildProductsCountView() {
        htmlViewBuilder.buildProductsCountView(resultWriter, 123);
        assertResultIgnoreLineBreaks(
                "<html><body>" +
                        "Number of products: " +
                        "123" +
                        "</body></html>"
        );
    }
}
