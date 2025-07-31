package com.example.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

public class Database {
    static String url="jdbc:sqlite:passwords.db";

    public static Connection getConnection(String url){
        try {
            Connection connection = DriverManager.getConnection(url);
            return connection;
        }catch(Exception e){
            System.out.println("Exception in getConnection");
        }
        return null;

    }



    public static void createTable()throws Exception{
        Connection connection=getConnection(url);
        String createTableUrl="CREATE TABLE IF NOT EXISTS logs(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "site TEXT NOT NULL,"+
                "username TEXT NOT NULL,"+
                "password TEXT NOT NULL);";
        Statement statement=connection.createStatement();
        statement.execute(createTableUrl);
    }

    public static void insertLog(String siteName,String username,String password)throws Exception{
        Connection connection=getConnection(url);
        String insertUrl="INSERT INTO logs(site,username,password) VALUES(?,?,?);";
        PreparedStatement preparedStatement=connection.prepareStatement(insertUrl);
        preparedStatement.setString(1,siteName);
        preparedStatement.setString(2,username);
        password=AES.encrypt(password,LoginView.global);
        preparedStatement.setString(3,password);
        preparedStatement.executeUpdate();
    }

    public static String getLog(Connection connection,String siteName)throws Exception{
        String getUrl="SELECT password FROM logs WHERE site=?;";
        PreparedStatement preparedStatement=connection.prepareStatement(getUrl);
        preparedStatement.setString(1,siteName);
        ResultSet results=preparedStatement.executeQuery();
        while(results.next()){
            return results.getString("password");
        }
        return null;
    }

    public static void getAllSites(Connection connection)throws Exception{
        String query="SELECT id,site FROM logs;";
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet results=preparedStatement.executeQuery();
        while(results.next()){
            System.out.print(results.getString(1)+"  "+results.getString(2));
            System.out.println();
        }
        System.out.println();
    }

    public static void createMasterPassword(String password) throws SQLException, NoSuchAlgorithmException {
        String urlMaster="jdbc:sqlite:masterPassword.db";
        Connection connection=DriverManager.getConnection(urlMaster);
        String url="CREATE TABLE  IF NOT EXISTS master(password TEXT NOT NULL)";
        Statement statement=connection.createStatement();
        statement.execute(url);

        String insert="INSERT INTO master(password) VALUES (?)";
        PreparedStatement preparedStatement=connection.prepareStatement(insert);
        preparedStatement.setString(1,calculateHash(password));
        preparedStatement.executeUpdate();
    }

    public static boolean compareMasterPasswordHashes(String password) throws SQLException, NoSuchAlgorithmException {
        String urlMaster="jdbc:sqlite:masterPassword.db";
        Connection connection=DriverManager.getConnection(urlMaster);
        Statement statement=connection.createStatement();
        String query="SELECT*FROM master";
        ResultSet hash=statement.executeQuery(query);
        String currentHash=calculateHash(password);
        while(hash.next()){
            return currentHash.equals(hash.getString(1));
        }
        return false;
    }


    private static String calculateHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes());

        return Base64.getEncoder().encodeToString(hashBytes);
    }








}
