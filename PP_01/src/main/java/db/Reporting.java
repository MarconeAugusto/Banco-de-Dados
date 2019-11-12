package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Reporting {
    /**
     * Consulta os emprestimos em andamento
     */
    public void emprestimosEmAndamento() {
        String query = "SELECT a.matricula, a.nome, a.sobrenome, b.dataEmprestimo, c.nome, c.tipo, e.nome, e.modelo FROM" +
                " Aluno a, Emprestimo b,Equipamentos c, Componentes e INNER JOIN Emprestimo_has_Equipamentos d WHERE" +
                " (a.matricula = b.Aluno_matricula AND b.situacao = 1 AND b.idEmprestimo = d.Emprestimo_idEmprestimo AND c.patrimonio = d.Equipamentos_patrimonio AND e.Equipamentos_patrimonio = c.patrimonio)";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-15s %-15s %-25s %-20s %-10s %-25s %-20s  %n", "Matricula","Nome","Sobrenome","Data Empréstimo","Nome Equipamento","Tipo","Nome Componente","Modelo");
            while (resultSet.next()) {
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-10s %-15s %-15s %-25s %-20s %-10s %-25s %-20s  %n",resultSet.getInt("matricula"),resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),resultSet.getString("dataEmprestimo"),resultSet.getString(5),
                        resultSet.getString("tipo"),resultSet.getString(7),resultSet.getString("modelo"));
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }

    /**
     * Consulta o emprestimo atribuído a determinado equipamento
     * @param patrimonio
     */
    public void emprestimosPorEquipamento(int patrimonio){
        String query = "SELECT a.matricula, a.nome, a.sobrenome, b.dataEmprestimo, c.nome, c.tipo, e.nome, e.modelo FROM" +
                " Aluno a, Emprestimo b,Equipamentos c, Componentes e INNER JOIN Emprestimo_has_Equipamentos d WHERE" +
                " (a.matricula = b.Aluno_matricula AND b.situacao = 1 AND b.idEmprestimo = d.Emprestimo_idEmprestimo AND c.patrimonio = d.Equipamentos_patrimonio AND c.patrimonio = " + patrimonio+ " AND e.Equipamentos_patrimonio = c.patrimonio)";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-15s %-15s %-25s %-20s %-10s %-25s %-20s  %n", "Matricula","Nome","Sobrenome","Data Empréstimo","Nome Equipamento","Tipo","Nome Componente","Modelo");
            while (resultSet.next()) {
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-10s %-15s %-15s %-25s %-20s %-10s %-25s %-20s  %n",resultSet.getInt("matricula"),resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),resultSet.getString("dataEmprestimo"),resultSet.getString(5),
                        resultSet.getString("tipo"),resultSet.getString(7),resultSet.getString("modelo"));
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }

    /**
     * Consulta o emprestimo atrubuído a determinado aluno
     * @param matricula
     */
    public void emprestimosPorAluno(int matricula) {
        String query = "SELECT a.matricula, a.nome, a.sobrenome, b.dataEmprestimo, c.nome, c.tipo, e.nome, e.modelo  FROM" +
                " Aluno a, Emprestimo b,Equipamentos c, Componentes e INNER JOIN Emprestimo_has_Equipamentos d WHERE" +
                " (a.matricula = b.Aluno_matricula AND b.situacao = 1 AND b.idEmprestimo = d.Emprestimo_idEmprestimo AND c.patrimonio = d.Equipamentos_patrimonio AND a.matricula = " + matricula+ " AND e.Equipamentos_patrimonio = c.patrimonio)";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-15s %-15s %-25s %-20s %-10s %-25s %-20s  %n", "Matricula","Nome","Sobrenome","Data Empréstimo","Nome Equipamento","Tipo","Nome Componente","Modelo");
            while (resultSet.next()) {
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-10s %-15s %-15s %-25s %-20s %-10s %-25s %-20s  %n",resultSet.getInt("matricula"),resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),resultSet.getString("dataEmprestimo"),resultSet.getString(5),
                        resultSet.getString("tipo"),resultSet.getString(7),resultSet.getString("modelo"));
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }

    /**
     * Retorna os empréstimos que já estão vencidos
     */
    public void emprestimosVencidos() {
        String query = "SELECT a.matricula, a.nome, a.sobrenome, b.dataEmprestimo, c.nome, c.tipo, e.nome, e.modelo, b.previsaoDevolucao FROM" +
                " Aluno a, Emprestimo b,Equipamentos c, Componentes e INNER JOIN Emprestimo_has_Equipamentos d WHERE" +
                " (a.matricula = b.Aluno_matricula AND b.situacao = 1 AND b.idEmprestimo = d.Emprestimo_idEmprestimo AND c.patrimonio = d.Equipamentos_patrimonio AND e.Equipamentos_patrimonio = c.patrimonio)";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long millis=System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            while (resultSet.next()) {
                java.sql.Date prevDevolucao = new Date(dateFormat.parse(resultSet.getString("previsaoDevolucao")).getTime());
                if (date.after(prevDevolucao)) {
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-10s %-15s %-15s %-25s %-25s %-20s %-10s %-25s %-20s  %n", "Matricula","Nome","Sobrenome","Data Empréstimo","Data de vencimento","Nome Equipamento","Tipo","Nome Componente","Modelo");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-10s %-15s %-15s %-25s %-25s %-20s %-10s %-25s %-20s  %n",resultSet.getInt("matricula"),resultSet.getString("nome"),
                            resultSet.getString("sobrenome"),resultSet.getString("dataEmprestimo"),resultSet.getString("previsaoDevolucao"),resultSet.getString(5),
                            resultSet.getString("tipo"),resultSet.getString(7),resultSet.getString("modelo"));
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }

    /**
     * Consultar os alunos que possuem emprestimos ativos
     */
    public void alunosQuePossuemEmprestimo() {
        String query = "SELECT a.matricula, a.nome, a.sobrenome FROM Aluno a INNER JOIN Emprestimo b ON a.matricula = b.Aluno_matricula WHERE b.situacao = 1";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("--------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %n", "Matricula","Nome","Sobrenome");
            while (resultSet.next()) {
                System.out.println("--------------------------------------------");
                System.out.printf("%-15s %-15s %-15s %n",resultSet.getInt("matricula"),resultSet.getString("nome"),resultSet.getString("sobrenome"));
            }
            System.out.println("--------------------------------------------");
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }

    public void equipamentosQuePossuemEmprestimo() {
        String query = "SELECT patrimonio, nome, tipo FROM Equipamentos WHERE situacao = 1";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("-------------------------------------------------------------");
            System.out.printf("%-15s %-25s %-15s %n", "Patrimônio","Nome","Tipo");
            while (resultSet.next()) {
                System.out.println("-------------------------------------------------------------");
                System.out.printf("%-15s %-25s %-15s %n",resultSet.getInt("patrimonio"),resultSet.getString("nome"),resultSet.getString("tipo"));
            }
            System.out.println("-------------------------------------------------------------");
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }
}
