package exemplo04;

import exemplo02.db.ConnectionFactory;
import exemplo04.entities.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public boolean inserir(Pessoa pessoa){
        String query = "INSERT INTO Pessoa ( nome, peso, altura, email) " +
                "VALUES (?,?,?,?)";
        int linhas = 0;
        try(Connection conexao = ConnectionFactory.getConnection()){
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1,pessoa.getNome());
            statement.setDouble(2,pessoa.getPeso());
            statement.setInt(3,pessoa.getAltura());
            statement.setString(4,pessoa.getEmail());
            linhas = statement.executeUpdate(query); //função retorna um int de quantidade de linhas alteradas
            statement.close();
        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
        return false;
    }

    public Pessoa obterPessoa(int idPessoa){
        String query = "SELECT * FROM Pessoa WHERE idPessoa = "+idPessoa;
        try(Connection conexao = ConnectionFactory.getConnection()){
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query); // resultset
            statement.close();
            return (Pessoa) resultSet;
        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
        return null;
    }

    public List<Pessoa> listarTodasPessoas(){
        List<Pessoa> pessoas = new ArrayList<>();
        //vai ter um while percorrendo o ResultSet
        //dentro do while vou criar uma pessoa e adicionar na lista
        try(Connection conexao = ConnectionFactory.getConnection()){
            String query = "SELECT * FROM Pessoa ";
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query); // resultset
            while (resultSet.next()){
                String nome = resultSet.getString("Nome");
                double peso = resultSet.getDouble("Peso");
                int altura = resultSet.getInt("Altura");
                String email = resultSet.getString("Email");
                Pessoa p = new Pessoa(nome,peso,altura,email); //Pegar do RS
                pessoas.add(p);
            }
            return pessoas;
        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
        return null;
    }

    public int atualizarPessoa(Pessoa pessoa){
        String query = "UPDATE Pessoa SET Nome = ?, Peso = ?, Altura = ? , Email = ? WHERE idPessoa";
        int linhas = 0;
        try(Connection conexao = ConnectionFactory.getConnection()){
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1,pessoa.getNome());
            statement.setDouble(2,pessoa.getPeso());
            statement.setInt(3,pessoa.getAltura());
            statement.setString(4,pessoa.getEmail());
            linhas = statement.executeUpdate(query); //função retorna um int de quantidade de linhas alteradas
            statement.close();
            return linhas;
        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
        return 0;
    }

    public int atualizarVariasPessoas(String query){
        int linhas = 0;
        try(Connection conexao = ConnectionFactory.getConnection()){
            PreparedStatement statement = conexao.prepareStatement(query);
            linhas = statement.executeUpdate(query); //função retorna um int de quantidade de linhas alteradas
            statement.close();
            return linhas;
        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
        return 0;
    }

    public int apagarPessoa(int idPessoa){
        String query = "DELETE * FROM Pessoa WHERE idPessoa = "+idPessoa;
        int linhas = 0;
        try(Connection conexao = ConnectionFactory.getConnection()){
            PreparedStatement statement = conexao.prepareStatement(query);
            linhas = statement.executeUpdate(query); //função retorna um int de quantidade de linhas alteradas
            statement.close();
            return linhas;
        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }
        return 0;
    }
}
