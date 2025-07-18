import java.sql.*;
import java.util.Scanner;

public class PasswordVault {
    static String url="jdbc:sqlite:passwordVaultDatabase2.db";
    static String createTable="CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "site TEXT NOT NULL,"+
            "encrypted_password TEXT NOT NULL);";

    static String insertValue="INSERT INTO users(site,encrypted_password) VALUES (?,?);";
    static String getPassword="SELECT*FROM users WHERE site=?";


    public static void setInsertValue(String site,String password,Connection connection) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement(insertValue);
        preparedStatement.setString(1,site);
        preparedStatement.setString(2,password);
        preparedStatement.executeUpdate();
    }


    public static void createTable(Connection connection) throws SQLException {
        Statement statement=connection.createStatement();
        statement.execute(createTable);
    }

    public static void getPassword(String site,Connection connection) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement(getPassword);
        preparedStatement.setString(1,site);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println("id:"+resultSet.getString(1));
            System.out.println("site:"+resultSet.getString(2));
            System.out.println("Password:"+resultSet.getString(3));
            System.out.println("\n\n");
        }
    }




    public static void main(String[] args) throws SQLException {
        Scanner scanner=new Scanner(System.in);
        Connection connection=DriverManager.getConnection(url);
        createTable(connection);

        while(true){
            System.out.println("1)Save password");
            System.out.println("2)Get a password");
            System.out.println("3)Exit");
            String input=scanner.nextLine();
            switch(input){
                case "1":
                    System.out.println("Site name:");
                    String site=scanner.nextLine();
                    System.out.println("Password:");
                    String password=scanner.nextLine();
                    setInsertValue(site,password,connection);
                    break;
                case "2":
                    System.out.println("Site name:");
                    String getSite=scanner.nextLine();
                    getPassword(getSite,connection);
                    break;
                case "3":
                    return;
            }

        }




    }
}
