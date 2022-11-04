package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.domain.ConnectionProviderImpl;
import ru.akirakozov.sd.refactoring.domain.ProductRepository;
import ru.akirakozov.sd.refactoring.domain.ProductRepositoryImpl;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;
import ru.akirakozov.sd.refactoring.view.HtmlViewBuilder;
import ru.akirakozov.sd.refactoring.view.ViewBuilder;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final ConnectionProviderImpl connectionProvider = new ConnectionProviderImpl("jdbc:sqlite:test.db");
        final ProductRepository productRepository = new ProductRepositoryImpl(connectionProvider);

        productRepository.createProductTable();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        final ViewBuilder viewBuilder = new HtmlViewBuilder();

        context.addServlet(new ServletHolder(new AddProductServlet(productRepository, viewBuilder)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productRepository, viewBuilder)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productRepository, viewBuilder)),"/query");

        server.start();
        server.join();
    }
}
