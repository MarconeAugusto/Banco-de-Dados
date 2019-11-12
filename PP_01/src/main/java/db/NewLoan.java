package db;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class NewLoan {
    static String finalSemestre = "2019-12-17 23:59:59";
    /**
     * Verificar se existe a matrícula desejada no banco
     * @param matricula
     * @return
     */
    public boolean contemMatricula(int matricula) {
        String query = "SELECT  Aluno.matricula FROM Aluno";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("matricula") == matricula) {
                    return true;
                }
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }

    /**
     * Verificar se existe o patrimônio desejado no banco
     * @param patrimonio
     * @return
     */
    public boolean contemPatrimonio(int patrimonio) {
        String query = "SELECT Equipamentos.patrimonio FROM Equipamentos WHERE situacao = 0";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("patrimonio") == patrimonio) {
                    return true;
                }
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }

    /**
     * Verificar se existe o atividade desejado para o aluno no banco
     * @param finalidade
     * @param matricula
     * @return
     */
    public boolean contemAtividade(int finalidade, int matricula) {
        String query = "SELECT Atividade_idAtividade, Aluno_matricula FROM Atividade_has_Aluno";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if ((resultSet.getInt("Atividade_idAtividade") == finalidade) && (resultSet.getInt("Aluno_matricula") == matricula )) {
                    return true;
                }
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }

    /**
     * Consultar tabela equipamentos
     */
    public void consultaEquipamentos() {
        String query = "SELECT * FROM Equipamentos WHERE (Reserva_idReserva IS NULL AND situacao = 0)";
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
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }

    /**
     * Consultar tabela aluno
     */
    public void consultaAluno() {
        updatePenalidade();
        String query = "SELECT * FROM Aluno WHERE estadoMatricula = TRUE AND penalidade IS NULL OR penalidade = 0 ";
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("-------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %n", "Matrícula","Nome","Sobrenome");
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

    /**
     * Atualizar a penalidade de um aluno toda vez que for cadastrar um novo empréstimo
     */
    private void updatePenalidade() {
        String query = "SELECT penalidade, matricula FROM Aluno WHERE estadoMatricula = TRUE";
        String dataPenalidade, matricula;
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate agora = LocalDate.now();
            while (resultSet.next()) {
                dataPenalidade = resultSet.getString("penalidade");
                if(dataPenalidade != null && dataPenalidade != String.valueOf(0)){
                    matricula = resultSet.getString("matricula");
                    LocalDate penalidade = LocalDate.parse(dataPenalidade.substring(0,10),dateFormat);
                    if(penalidade.equals(agora)){
                        try (Connection cx = ConnectionFactory.getConnection(); Statement stm = cx.createStatement();) {
                            String query2 = "UPDATE Aluno SET penalidade = NULL WHERE matricula = " + matricula;
                            stm.executeUpdate(query2);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            resultSet.close();
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }

    /**
     *Função destinada a realizar o empréstimo, atualizando a tabela empréstimo, a tabela Empréstimo_has_Equipamento
     * e a tabela Equipamentos
     * @param matricula
     * @param patrimonio
     * @param finalidade
     */
    public void realizarEmprestimo(int matricula, int patrimonio, int finalidade) {
        String query, m = String.valueOf(matricula);
        try (Connection conexao = ConnectionFactory.getConnection(); Statement statement = conexao.createStatement();) {
            if (finalidade == 4) {
                query = "INSERT INTO Emprestimo (`dataEmprestimo`, `previsaoDevolucao`, `devolucao`, `renovacao`, `penalidade`, `Aluno_matricula`, `situacao`,finalidade) " +
                        "VALUES (NOW(),DATE_ADD(NOW(), INTERVAL 15 DAY), null, 0, 0, " + m + ", 1, " + finalidade + ")";
            } else {
                query = "INSERT INTO Emprestimo (`dataEmprestimo`, `previsaoDevolucao`, `devolucao`, `renovacao`, `penalidade`, `Aluno_matricula`, `situacao`,finalidade)" +
                        " VALUES (NOW(), '"+ finalSemestre + "', null , 0, 0, " + m + ",  1, " + finalidade + ")";
            }
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        // obter o id do empréstimo para atualizar a tabela Emprestimo_has_Equipamento
        query = "SELECT Emprestimo.idEmprestimo FROM Emprestimo where (Aluno_matricula = " + m + " AND situacao = 1)";
        int idEmprestimo = 0;
        try (Connection cn = ConnectionFactory.getConnection()) {
            PreparedStatement st = cn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            if(resultSet.next()){
                idEmprestimo = resultSet.getInt("idEmprestimo");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection cx = ConnectionFactory.getConnection(); Statement stm = cx.createStatement();) {
            query = "INSERT INTO Emprestimo_has_Equipamentos (Emprestimo_idEmprestimo,Equipamentos_patrimonio) VALUE ("+ idEmprestimo +", "+ patrimonio +")";
            String query2 = "UPDATE ControleDeEmprestimos.Equipamentos t SET t.situacao = 1 WHERE t.patrimonio = " + patrimonio;
            stm.executeUpdate(query);
            stm.executeUpdate(query2);
            System.out.println("\nEmpréstimo efetuado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *Consultar as atividades que o aluno participa
     * @param matricula
     * @return
     */
    public boolean consultaAtividade(int matricula) {
        String valor  = String.valueOf(matricula);
        String query = "SELECT N.idAtividade, N.nomeAtividade FROM Atividade N INNER JOIN Atividade_has_Aluno A " +
                "ON N.idAtividade = A.Atividade_idAtividade WHERE A.Aluno_matricula = " + valor;
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("-------------------------------------------");
                System.out.println(resultSet.getInt("idAtividade") + " - " + resultSet.getString("nomeAtividade"));
            }
            System.out.println("-------------------------------------------");
            resultSet.close();
            return true;
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }

    /**
     *Verificar se o aluno tem algum empréstimo pendente
     * @param matricula
     * @return
     */
    public boolean consultaEmprestimo(int matricula){
        String valor  = String.valueOf(matricula);
        String query = "SELECT A.matricula, A.nome, A.sobrenome FROM Aluno A INNER JOIN Emprestimo E ON " +
                "A.matricula = E.Aluno_matricula WHERE E.situacao = 1 AND A.matricula = " + valor;
        try (Connection conexao = ConnectionFactory.getConnection()) {
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() == true){
                return false;
            }else return true;
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
        return false;
    }
}
