package exemplo02;

import exemplo02.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.Statement;

public class Principal {

    public void inserir(){



        try(Connection conexao = ConnectionFactory.getConnection(); Statement statement = conexao.createStatement();){

            String nome = "Ana";
            double peso = 65;
            int altura = 160;
            String email = "ana@email.com";

            String query = "INSERT INTO Pessoa ( nome, peso, altura, email) " +
                    "VALUES ('"+ nome+"'," // VALUES('Jose')
                    +peso+","+altura+",'"+email+"'"+")";

            statement.executeUpdate(query);

        }catch (Exception e){
            System.err.println("Erro: "+ e.toString());
        }


    }

    public static void main(String[] args) {

    }


}
