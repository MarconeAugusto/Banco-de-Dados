package db;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CloseLoan {

    /**
     * Consulta os emprestimos ativos na tabela de Emprestimos
     * @return
     */
    public boolean consultaEmprestimosAtivos() {
        String query = "SELECT N.matricula, N.nome, N.sobrenome, A.previsaoDevolucao FROM Aluno N INNER JOIN Emprestimo A " +
                "ON N.matricula = A.Aluno_matricula WHERE (A.situacao = 1 AND A.renovacao <= 3 )";
        try (Connection con = ConnectionFactory.getConnection()) {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("---------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-35s %n", "Matrícula","Nome","Sobrenome","Vencimento");
            while (resultSet.next()) {
                System.out.println("----------------------------------------------------------------------------");
                System.out.printf("%-15d %-15s %-15s %-35s %n",resultSet.getInt("matricula"),resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),resultSet.getString("previsaoDevolucao"));
            }
            System.out.println("---------------------------------------------------------------------------");
            resultSet.close();
            return true;
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }

    /**
     *Finaliza o emprestimo atualizando as tabelas necessárias
     * @param matricula
     * @return
     */
    public boolean finalizarEmprestimo(int matricula){
        int patrimonio = 0, idEmprestimo = 0;
        String query = "SELECT N.Equipamentos_patrimonio, N.Emprestimo_idEmprestimo FROM Emprestimo_has_Equipamentos N INNER JOIN Emprestimo A " +
                "ON A.idEmprestimo = N.Emprestimo_idEmprestimo WHERE (A.situacao = 1 AND  A.Aluno_matricula = " + matricula +")";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                patrimonio = resultSet.getInt("Equipamentos_patrimonio");
                idEmprestimo = resultSet.getInt("Emprestimo_idEmprestimo");
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        query = "UPDATE Equipamentos SET situacao = 0 WHERE patrimonio = " + patrimonio;
        String query2 = "DELETE FROM Emprestimo_has_Equipamentos WHERE (Emprestimo_idEmprestimo = " + idEmprestimo + "  AND Equipamentos_patrimonio = "+ patrimonio +")";
        String query3 = "UPDATE Emprestimo SET situacao = 0 , devolucao = now() WHERE idEmprestimo = " + idEmprestimo;
        try (Connection cx = ConnectionFactory.getConnection(); Statement stm = cx.createStatement();) {
            stm.executeUpdate(query);
            stm.executeUpdate(query2);
            stm.executeUpdate(query3);
            int dias = verificaPenalidade(idEmprestimo);
            System.out.println("Empéstimo devolvido com " + dias + " dia(s) de atraso");
            if (dias > 0){
                dias = dias * 3;
                String query4 = "UPDATE Emprestimo SET penalidade = " + dias + " WHERE idEmprestimo = " + idEmprestimo;
                String query5 = "UPDATE Aluno SET penalidade = DATE_ADD(NOW(), INTERVAL " + dias + " DAY) WHERE matricula = " + matricula;
                stm.executeUpdate(query4);
                stm.executeUpdate(query5);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int verificaPenalidade(int idEmprestimo) {
        String dD = null, dP = null;
        String query = "SELECT previsaoDevolucao, devolucao FROM Emprestimo WHERE idEmprestimo = "+ idEmprestimo;
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dD = resultSet.getString("devolucao");
                dP = resultSet.getString("previsaoDevolucao");
            }
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate devolucao = LocalDate.parse(dD.substring(0,10),dateFormat);
            LocalDate prevista = LocalDate.parse(dP.substring(0,10),dateFormat);
            if(devolucao.isAfter(prevista)){
                final long days = ChronoUnit.DAYS.between(prevista, devolucao);
                return (int)days;
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return 0;
    }
}
