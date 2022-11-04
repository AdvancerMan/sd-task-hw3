package ru.akirakozov.sd.refactoring.domain;

import ru.akirakozov.sd.refactoring.domain.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private final ConnectionProvider connectionProvider;

    public ProductRepositoryImpl(final ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void createProductTable() {
        try (Connection c = connectionProvider.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveProduct(final Product product) {
        try {
            try (Connection c = connectionProvider.getConnection()) {
                String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> loadAll() {
        final List<Product> result = new ArrayList<>();
        try {
            try (Connection c = connectionProvider.getConnection()) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

                while (rs.next()) {
                    String  name = rs.getString("name");
                    int price  = rs.getInt("price");
                    result.add(new Product(name, price));
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Product loadMaxByPrice() {
        Product result = null;
        try {
            try (Connection c = connectionProvider.getConnection()) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

                while (rs.next()) {
                    String  name = rs.getString("name");
                    int price  = rs.getInt("price");
                    result = new Product(name, price);
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Product loadMinByPrice() {
        Product result = null;
        try {
            try (Connection c = connectionProvider.getConnection()) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

                while (rs.next()) {
                    String  name = rs.getString("name");
                    int price  = rs.getInt("price");
                    result = new Product(name, price);
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public long loadPriceSum() {
        long result = 0;
        try {
            try (Connection c = connectionProvider.getConnection()) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

                if (rs.next()) {
                    result = rs.getInt(1);
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int loadProductsCount() {
        int result = 0;
        try {
            try (Connection c = connectionProvider.getConnection()) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

                if (rs.next()) {
                    result = rs.getInt(1);
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
