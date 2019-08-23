package exemplo01;

import java.sql.*;
import java.util.Scanner;

public class Principal {

    private final String caminhoDB = "src/main/resources/lab01.db";

    public void inserir() throws Exception {
        Class.forName("org.sqlite.JDBC"); //indica o driver a ser usado

        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+caminhoDB); //conexão com o banco
        Statement statement = connection.createStatement(); //abrir o criete sqlite para comandos

        String nome = "Jose";
        double peso = 65;
        int altura = 165;
        String email = "jose@email.com";

        String query = "INSERT INTO Pessoa ( nome, peso, altura, email) " +
                "VALUES ('"+ nome+"'," // VALUES('Jose')
                +peso+","+altura+",'"+email+"'"+")";

        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }


    public void listar() throws Exception{
        Class.forName("org.sqlite.JDBC"); //indica o driver a ser usado

        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+caminhoDB); //conexão com o banco
        Statement statement = connection.createStatement(); //abrir o criete sqlite para comandos

        String query = "SELECT * FROM Pessoa";

        ResultSet resultSet = statement.executeQuery(query); // resultset

        while (resultSet.next()){
            System.out.println("Nome: "+ resultSet.getString("Nome"));
            System.out.println("Peso: "+ resultSet.getDouble("Peso"));
            System.out.println("Altura: "+ resultSet.getInt("Altura"));
            System.out.println("Email: "+ resultSet.getString("Email"));
            System.out.println("-------------------------------------------");
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    public void buscarPessoa(String nome) throws Exception{
        Class.forName("org.sqlite.JDBC"); //indica o driver a ser usado
        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+caminhoDB); //conexão com o banco
        Statement statement = connection.createStatement(); //abrir o criete sqlite para comandos

        String query = "SELECT * FROM Pessoa WHERE Nome = '"+nome+"'";

        ResultSet resultSet = statement.executeQuery(query); // resultset

        while (resultSet.next()){
            System.out.println("Nome: "+ resultSet.getString("Nome"));
            System.out.println("Peso: "+ resultSet.getDouble("Peso"));
            System.out.println("Altura: "+ resultSet.getInt("Altura"));
            System.out.println("Email: "+ resultSet.getString("Email"));
            System.out.println("-------------------------------------------");
        }

        resultSet.close();
        statement.close();
        connection.close();

    }

    public static void main(String[] args) throws Exception {

        Principal p = new Principal();
        //p.inserir();
        //p.listar();
        String pessoa = "Juca' OR '1' = '1";
        //System.out.println("Digite o nome que deseja buscar:");
        Scanner scanner = new Scanner(System.in);
        p.buscarPessoa(pessoa);
    }

}
