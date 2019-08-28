package exemplo05;

import exemplo05.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Principal {
    public void listar(){

        String query = "SELECT *FROM Aluno";

        try(Connection conexao = ConnectionFactory.getConnection()){
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println("Id Aluno: "+ resultSet.getInt("idAluno"));
                System.out.println("Nome: "+ resultSet.getString("Nome"));
                System.out.println("Telefone: "+ resultSet.getInt("Telefone"));
                System.out.println("Email: "+ resultSet.getString("Email"));
                System.out.println("-------------------------------------------");
            }

            resultSet.close();

        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.listar();
    }

}
