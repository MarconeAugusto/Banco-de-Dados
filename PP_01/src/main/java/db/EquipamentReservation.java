package db;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EquipamentReservation {

    static String finalSemestre = "2019-12-17 23:59:59";

    /**
     *Consultar os emprestimos vigentes que o aluno participa
     * @return
     */
    public boolean consultaEmprestimos() {

        String query = "SELECT N.matricula, N.nome, N.sobrenome, A.previsaoDevolucao FROM Aluno N INNER JOIN Emprestimo A " +
                "ON N.matricula = A.Aluno_matricula WHERE (A.situacao = 1 AND A.renovacao < 3)";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("-------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-35s %n", "Matrícula","Nome","Sobrenome","Vencimento");
            while (resultSet.next()) {
                System.out.println("--------------------------------------------------------------------------");
                System.out.printf("%-15d %-15s %-15s %-35s %n",resultSet.getInt("matricula"),resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),resultSet.getString("previsaoDevolucao"));
            }
            System.out.println("-------------------------------------------------------------------------");
            resultSet.close();
            return true;
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }

    /**
     * Obter o empréstimo que deseja renovar e renova alterando a tabela
     * @param matricula
     */
    public boolean renovaEmprestimo(int matricula){
        int finalidade = 0;
        String previsao = null;
        String query = "SELECT A.previsaoDevolucao, A.finalidade FROM Emprestimo A INNER JOIN Aluno E ON " +
                "A.Aluno_matricula = E.matricula WHERE A.situacao = 1 AND A.Aluno_matricula = " + matricula ;
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() == true){
                //System.out.println("Previsão: "+ resultSet.getString("previsaoDevolucao")+ " "+ resultSet.getInt("finalidade") );
                previsao = resultSet.getString("previsaoDevolucao");
                finalidade = resultSet.getInt("finalidade");
            }else{
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate agora = LocalDate.now();
        LocalDate prevDevolucao = LocalDate.parse(previsao.substring(0,10),dateFormat);
        if (finalidade == 4 && agora.isBefore(prevDevolucao)){
            query = "UPDATE Emprestimo  SET previsaoDevolucao = DATE_ADD(NOW(), INTERVAL 15 DAY), renovacao = renovacao + 1 WHERE (situacao = 1 AND Aluno_matricula = " + matricula+
                    ") AND (DATE_ADD(NOW(), INTERVAL 15 DAY) < '" + finalSemestre + "')";
            try (Connection cx = ConnectionFactory.getConnection(); Statement stm = cx.createStatement();) {
                stm.executeUpdate(query);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }if(finalidade != 4  && agora.isBefore(prevDevolucao)){
            query = "UPDATE Emprestimo SET previsaoDevolucao = DATE_ADD(NOW(), INTERVAL 60 DAY), renovacao = renovacao + 1  WHERE (situacao = 1 AND Aluno_matricula = " + matricula+ ")";
            try (Connection cx = ConnectionFactory.getConnection(); Statement stm = cx.createStatement();) {
                stm.executeUpdate(query);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            return false;
        }
        return false;
    }

    /**
     * Função para verificar se existe alguma reserva para o equipamento
     * @param matricula
     * @return
     */
    public boolean verificaReserva(int matricula){
        int idEmprestimo = 0;
        String query = "SELECT idEmprestimo  FROM Emprestimo WHERE (situacao = 1 AND renovacao < 3 AND Aluno_matricula = " + matricula + ")";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idEmprestimo = resultSet.getInt("idEmprestimo");
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        query = "SELECT N.Reserva_idReserva FROM Equipamentos N INNER JOIN Emprestimo_has_Equipamentos A ON N.patrimonio = A.Equipamentos_patrimonio" +
                " WHERE  A.Emprestimo_idEmprestimo = " + idEmprestimo;
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() == true) {
                if(resultSet.getBoolean("Reserva_idReserva")){
                    return false;
                }else return true;
            }else return false;
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }
}
