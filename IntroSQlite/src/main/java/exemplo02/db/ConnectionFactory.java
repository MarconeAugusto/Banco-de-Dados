package exemplo02.db;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

    private static final String caminhoDB = "src/main/resources/lab01.db";
    private static Connection conexao;

    public static synchronized Connection getConnection(){
        try {
            DriverManager.registerDriver(new JDBC());

            conexao = DriverManager.getConnection("jdbc:sqlite:" + caminhoDB);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conexao;
    }

}
