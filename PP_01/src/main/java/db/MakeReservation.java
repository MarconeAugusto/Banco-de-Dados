package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MakeReservation {
    /**
     * Função que retorna os equipamentos disponíveis para empréstimo
     * @return
     */
    public boolean consultaEquipamentos() {
        String query = "SELECT * FROM Equipamentos WHERE Reserva_idReserva IS NULL AND Reserva_Aluno_matricula IS NULL";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-15s %-25s %-15s %n", "Patrimônio","Nome","Tipo");
            while (resultSet.next()) {
                System.out.println("----------------------------------------------------------------");
                System.out.printf("%-15s %-25s %-15s %n",resultSet.getInt("patrimonio"),resultSet.getString("nome"),resultSet.getString("tipo"));
            }
            System.out.println("----------------------------------------------------------------");
            resultSet.close();
            return true;
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }

    /**
     * falta terminar a implementação
     */
    public void consultaAlunos() {
    }
}
