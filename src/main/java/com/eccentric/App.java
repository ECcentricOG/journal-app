package com.eccentric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {

    public static void main( String[] args ) throws IOException {
        
        System.out.println("Press [I]nsert info");
        System.out.println("Press [D]isplay info");
        System.out.println("Press [Q]uit");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = br.readLine();
            if(input.trim().equals("I") || input.trim().equals("i")) {
                System.out.print("Enter Info : ");
                String data = br.readLine();
                insertData(data);
            } else if (input.trim().equals("D") || input.trim().equals("d")) {
                diaplayData();
            } else if(input.trim().equals("Q") || input.trim().equals("q")) {
                break;
            } else {
                System.out.println("Invalid Selection");
            }
        }
    }

    public static void insertData(String data) {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://loalhost:3306/journal", "root", "root");
            Statement stml = con.createStatement();
            String qry = "insert into entries (info) values" + "'" + data + "'";
            stml.execute(qry);
            System.out.println("Entry added");
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void diaplayData() {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:mysql://loalhost:3306/journal", "root", "root");
            String sql = "select * from entries order by desc day";
            PreparedStatement stml = con.prepareStatement(sql);
            ResultSet result = stml.executeQuery();
            boolean hasEntries = false;
            while (result.next()) {
                System.out.println("<------------------------------------------------------------------------->");
                String info = result.getString("info");
                String day = result.getTimestamp("day").toString();
                System.out.println(day + " --> " + info);
                System.out.println();
            }
            if (!hasEntries) {
               System.out.println("No entries found"); 
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
