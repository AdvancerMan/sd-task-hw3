package ru.akirakozov.sd.refactoring.domain;

import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private final ConnectionProvider connectionProvider;

    public ProductRepositoryImpl(final ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    private <R> R generateStatement(final StatementMapper<R> statementMapper) {
        try (final Connection connection = connectionProvider.getConnection();
             final Statement statement = connection.createStatement()) {
            return statementMapper.mapStatement(statement);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void executeUpdate(final String sql) {
        generateStatement(statement -> statement.executeUpdate(sql));
    }

    private <R> R executeQuery(final String sql, final ResultSetMapper<R> resultMapper) {
        return generateStatement(statement -> {
            try (final ResultSet resultSet = statement.executeQuery(sql)) {
                return resultMapper.mapResultSet(resultSet);
            }
        });
    }

    private static Product getProduct(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            final String name = resultSet.getString("name");
            final int price = resultSet.getInt("price");
            return new Product(name, price);
        }
        return null;
    }

    @Override
    public void createProductTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    @Override
    public void saveProduct(final Product product) {
        executeUpdate("INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" +
                product.getName() +
                "\"," +
                product.getPrice() +
                ")");
    }

    @Override
    public List<Product> loadAll() {
        return executeQuery("SELECT * FROM PRODUCT", resultSet -> {
            final List<Product> result = new ArrayList<>();

            Product product = getProduct(resultSet);
            while (product != null) {
                result.add(product);
                product = getProduct(resultSet);
            }
            return result;
        });
    }

    @Override
    public Product loadMaxByPrice() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", ProductRepositoryImpl::getProduct);
    }

    @Override
    public Product loadMinByPrice() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", ProductRepositoryImpl::getProduct);
    }

    @Override
    public long loadPriceSum() {
        return executeQuery("SELECT SUM(price) FROM PRODUCT", resultSet -> {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return null;
        });
    }

    @Override
    public int loadProductsCount() {
        return executeQuery("SELECT COUNT(*) FROM PRODUCT", resultSet -> {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return null;
        });
    }

    @FunctionalInterface
    private interface StatementMapper<R> {
        R mapStatement(Statement statement) throws SQLException;
    }

    @FunctionalInterface
    private interface ResultSetMapper<R> {
        R mapResultSet(ResultSet resultSet) throws SQLException;
    }
}
