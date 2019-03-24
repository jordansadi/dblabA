package com.company;

import java.sql.*;

class CreateDB {
    CreateDB() {
        try
        {
            // Create a named constant for the URL.
            // NOTE: This value is specific for Java DB.
            final String DB_URL = "jdbc:derby:CoffeeDB;create=true";

            // Create a connection to the database.
            Connection conn = DriverManager.getConnection(DB_URL);

            // If the DB already exists, drop the tables.
            dropTables(conn);

            buildProductsTable(conn);
            buildCartTable(conn);

            // Close the connection.
            conn.close();
        } catch (Exception e)
        {
            System.out.println("Error Creating the Table");
            System.out.println(e.getMessage());
        }

    }

    /**
     * The dropTables method drops any existing
     * in case the database already exists.
     */
    private static void dropTables(Connection conn)
    {
        System.out.println("Checking for existing tables.");

        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            try
            {
                stmt.execute("DROP TABLE Cart");
                System.out.println("Cart table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }

            try
            {
                stmt.execute("DROP TABLE Products");
                System.out.println("Products table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void buildProductsTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Products (" +
                    "Description CHAR(25), " +
                    "ProdNum INT NOT NULL PRIMARY KEY, " +
                    "Price DOUBLE " +
                    ")");

            // Insert rows.
            stmt.execute("INSERT INTO Products VALUES ( " +
                "'MilwaukeeHome Shirt', " +
                "1, " +
                "30.99 )");

            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Wiskullsin Shirt', " +
                    "2, " +
                    "30.99 )");

            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Giltee Shirt', " +
                    "3, " +
                    "30.99 )");

            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Fern & Nettle Soap', " +
                    "4, " +
                    "8.99 )");

            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Tactile Craftworks Wallet', " +
                    "5, " +
                    "15.99 )");

            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Dear Darlington Jewelry', " +
                    "6, " +
                    "20.99 )");

            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Paper Pleasers Mug', " +
                    "7, " +
                    "12.99 )");

            stmt.execute("INSERT INTO Products VALUES ( " +
                    "'Indulgence Chocolate', " +
                    "8, " +
                    "6.99 )");

            System.out.println("Products table created.");
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private static void buildCartTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Cart (" +
                    "CartPlace INT NOT NULL PRIMARY KEY," +
                    "Description CHAR(25), " +
                    "ProdNum INT, " +
                    "Price DOUBLE " +
                    ")");

            System.out.println("Cart table created.");
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
