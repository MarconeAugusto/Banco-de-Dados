package exemplo03;

import exemplo02.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Principal {

    public void listar(){
        String query = "SELECT *FROM Pessoa";

        try(Connection conexao = ConnectionFactory.getConnection()){

            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                System.out.println("Nome: "+ resultSet.getString("Nome"));
                System.out.println("Peso: "+ resultSet.getDouble("Peso"));
                System.out.println("Altura: "+ resultSet.getInt("Altura"));
                System.out.println("Email: "+ resultSet.getString("Email"));
                System.out.println("-------------------------------------------");
            }

            resultSet.close();

        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
    }

    public void inserir(){

        String nome = "Ana";
        double peso = 65;
        int altura = 160;
        String email = "ana@email.com";

        String query = "INSERT INTO Pessoa ( nome, peso, altura, email) " +
                "VALUES (?,?,?,?)";

        try(Connection conexao = ConnectionFactory.getConnection()){
            PreparedStatement statement = conexao.prepareStatement(query);

            statement.setString(1,nome);
            statement.setDouble(2,peso);
            statement.setInt(3,altura);
            statement.setString(4,email);

            int linhas = statement.executeUpdate(query); //função retorna um int de quantidade de linhas alteradas

        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.inserir();
        principal.listar();

    }


}
