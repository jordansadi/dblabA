package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static int cartPlace = 1;
    public static void main(String[] args) {

        Scanner k = new Scanner(System.in);

        CreateDB cc = new CreateDB();
        outputDB("Products");
        int userInput;

        System.out.println("=====================");
        System.out.println("1-8 add item to cart");
        System.out.println("9 view cart");
        System.out.println("0 to exit");
        System.out.println("=====================");

        while (true) {
            userInput = 10;

            while (userInput > 9 || userInput < 0) {
                System.out.println("Enter a number 0-9 to select an action:");

                try {
                    userInput = k.nextInt();
                    if (userInput > 9 || userInput < 0)
                        System.out.println("Invalid Input");
                } catch (Exception e) {
                    System.out.println("Invalid Input");
                    k.next();
                }
            }

            if (userInput == 0)
                return;
            else if (userInput == 9) {
                outputDB("Cart");
                return;
            }
            else
                addItem(userInput);
        }
    }

    private static void addItem(int itemNumber) {
        final String DB_URL = "jdbc:derby:CoffeeDB";
        String SQL = "INSERT INTO Cart VALUES (?, ?, ?, ?)";
        Connection conn = null;
        Statement getSTMT = null;
        PreparedStatement setSTMT;

        try{
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Adding item to cart...");
            getSTMT = conn.createStatement();
            setSTMT = conn.prepareStatement(SQL);

            String get = "SELECT ProdNum, Description, Price FROM Products WHERE ProdNum=" + itemNumber;
            ResultSet rs = getSTMT.executeQuery(get);

            String description = "";
            double price = 0;

            if(rs.next()) {
                price = rs.getDouble(3);
                description = rs.getString(2);
            }

            setSTMT.setInt(1, cartPlace);
            setSTMT.setString(2, description);
            setSTMT.setInt(3, itemNumber);
            setSTMT.setDouble(4, price);
            cartPlace++;

            setSTMT.executeUpdate();
            System.out.println("Item added to cart...");
            //Clean-up environment
            getSTMT.close();
            setSTMT.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(getSTMT!=null)
                    getSTMT.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    private static void outputDB(String table) {
        final String DB_URL = "jdbc:derby:CoffeeDB";
        Statement stmt = null;
        Connection conn = null;
        try{
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT ProdNum, Description, Price FROM " + table;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                String id  = rs.getString("ProdNum");
                double price = rs.getDouble("Price");
                String description = rs.getString("Description");

                //Display values
                System.out.print("ID: " + id.trim());
                System.out.print(", Price: " + price);
                System.out.println(", Description: " + description);
            }

            if (table.equals("Cart")) {
                // ResultSet res = st.executeQuery("SELECT SUM(col) FROM mytable");
                ResultSet sum = stmt.executeQuery("SELECT SUM(Price) FROM Cart");

                if (sum.next()) {
                    double total = sum.getDouble(1);
                    System.out.println("Total: " + total);
                }
            }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }
}
