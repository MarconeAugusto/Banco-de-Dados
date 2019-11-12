package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class ConnectionFactory {

    private static Connection conexao;

    public static synchronized Connection getConnection(){

        Properties properties = new Properties();
        properties.setProperty("user","marcone");
        properties.setProperty("password","bcd29008");
        properties.setProperty("useSSL","false");

        String host = "localhost";
        String port = "3306";
        String dbName = "ControleDeEmprestimos";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

        try {
            //DriverManager.registerDriver(new JDBC());
            conexao = DriverManager.getConnection(url,properties);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conexao;
    }

}
