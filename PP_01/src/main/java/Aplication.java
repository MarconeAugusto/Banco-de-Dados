import db.*;

import java.util.Scanner;

public class Aplication {

    public static void main(String[] args) {
        int op = 8;
        while (op != 0) {
            Scanner teclado = new Scanner(System.in);
            System.out.println("\n\n##############################################################");
            System.out.println("                  CONTROLE DE EMPRÉSTIMOS                     ");
            System.out.println("##############################################################");
            System.out.println("1 - Efetuar Empréstimo");
            System.out.println("2 - Renovar Empréstimo");
            System.out.println("3 - Finalizar Empréstimo");
            System.out.println("4 - Efetuar Reserva");
            System.out.println("5 - Emitir relatórios");
            System.out.println("\n6 - Encerrar Aplicação");
            System.out.println("Digite a opção desejada: ");
            op = teclado.nextInt();

            switch (op) {
                case 1:
                    System.out.println("\n##############################################################");
                    System.out.println("                      EFETUAR EMPRÉSTIMO                      ");
                    System.out.println("##############################################################");
                    NewLoan newLoan = new NewLoan();
                    newLoan.consultaAluno();                                                 // exibe a tabela de alunos
                    System.out.println("\nInforme a matrícula do aluno: ");
                    Scanner teclado2 = new Scanner(System.in);
                    int matricula = teclado2.nextInt();
                    if(newLoan.contemMatricula(matricula)) {                                  // verificar se o aluno está cadastrado
                        if (newLoan.consultaEmprestimo(matricula)) {
                            System.out.println("\nAluno cadastrado, informe a finalidade do empréstimo");
                            if (newLoan.consultaAtividade(matricula)) {                      // verificar as atividades que o aluno participa
                                int finalidade = teclado2.nextInt();
                                if (newLoan.contemAtividade(finalidade, matricula)) {         // verificar se a finalidade esta atrelada ao aluno
                                    int opcao = 1;
                                    while (opcao == 1) {
                                        Emprestimo:
                                        System.out.println("\nInforme o patrimônio do material que deseja pegar emprestado");
                                        newLoan.consultaEquipamentos();
                                        int patrimonio = teclado2.nextInt();
                                        if (newLoan.contemPatrimonio(patrimonio)) {                          // verificar se o patrimonio existe
                                            // efetivar o empréstimo
                                            newLoan.realizarEmprestimo(matricula, patrimonio, finalidade);
                                            System.out.println("Deseja solicitar mais algum material?");
                                            System.out.println("Digite 1 para SIM e 0 para NÂO?");
                                            opcao = teclado2.nextInt();
                                            if (opcao == 0) {
                                                break;
                                            }
                                        } else {
                                            System.out.println("\nPatrimônio inválido");
                                        }
                                    }
                                } else {
                                    System.out.println("Aluno não participa de nenhuma atividade");
                                }
                            } else {
                                System.out.println("Aluno selecionado está impossibilitado de pegar emprestimo");
                            }
                        }
                    }else {
                        System.out.println("\nMatrícula inválida");
                    }
                    break;

                case 2:
                    System.out.println("\n##############################################################");
                    System.out.println("                      RENOVAR EMPRÉSTIMO                      ");
                    System.out.println("##############################################################");
                    EquipamentReservation equipamentReservation = new EquipamentReservation();
                    if(equipamentReservation.consultaEmprestimos()){
                        System.out.println("\nInforme a matrícula do aluno: ");
                        Scanner teclado3 = new Scanner(System.in);
                        matricula = teclado3.nextInt();
                        if(equipamentReservation.verificaReserva(matricula)){
                            if(equipamentReservation.renovaEmprestimo(matricula)){
                                System.out.println("Emprestimo renovado");
                            }else{
                                System.out.println("Emprestimo não renovado");
                            }
                        }else{
                            System.out.println("Empréstimo não renovado, pois o equipamento está reservado");
                        }
                    }else{
                        System.out.println("Não existem empréstimos passíveis de renovação");
                    }
                    break;

                case 3:
                    System.out.println("\n##############################################################");
                    System.out.println("                     FINALIZAR EMPRÉSTIMO                     ");
                    System.out.println("##############################################################");
                    CloseLoan closeLoan = new CloseLoan();
                    closeLoan.consultaEmprestimosAtivos();
                    System.out.println("\nInforme a matrícula do aluno: ");
                    Scanner teclado4 = new Scanner(System.in);
                    matricula = teclado4.nextInt();
                    closeLoan.finalizarEmprestimo(matricula);
                    break;

                case 4:
                    System.out.println("\n##############################################################");
                    System.out.println("                        EFETUAR RESERVA                       ");
                    System.out.println("##############################################################");
                    MakeReservation makeReservation = new MakeReservation();
                    if(makeReservation.consultaEquipamentos()){
                        makeReservation.consultaAlunos();

                    }else{
                        System.out.println("Não existem equipamentos disponíveis para reserva");
                    }
                    break;

                case 5:
                    int opcao = 8;
                    while (opcao != 5) {
                        teclado = new Scanner(System.in);
                        System.out.println("\n##############################################################");
                        System.out.println("                       EMITIR RELATÓRIOS                       ");
                        System.out.println("##############################################################");
                        System.out.println("1 - Consultar empréstimos em andamento");
                        System.out.println("2 - Consultar empréstimos por equipamento");
                        System.out.println("3 - Consultar empréstimos por aluno");
                        System.out.println("4 - Consultar empréstimos em andamento porém vencidos");
                        System.out.println("\n5 - Retornar ao menu anterior");
                        System.out.println("Digite a opção de relatório que deseja consultar: \n");
                        opcao = teclado.nextInt();
                        Reporting reporting = new Reporting();
                        switch (opcao) {
                            case 1:
                                System.out.println("\n##############################################################");
                                System.out.println("                   EMPRÉSTIMOS EM ANDAMENTO                   ");
                                System.out.println("##############################################################");
                                reporting.emprestimosEmAndamento();
                                break;

                            case 2:
                                System.out.println("\n##############################################################");
                                System.out.println("                  EMPRÉSTIMOS POR EQUIPAMENTO                  ");
                                System.out.println("##############################################################");
                                reporting.equipamentosQuePossuemEmprestimo();
                                System.out.println("\nInforme o patrimônio do equipamento: ");
                                Scanner teclado6 = new Scanner(System.in);
                                int patrimonio = teclado6.nextInt();
                                reporting.emprestimosPorEquipamento(patrimonio);
                                break;

                            case 3:
                                System.out.println("\n##############################################################");
                                System.out.println("                     EMPRÉSTIMOS POR ALUNO                     ");
                                System.out.println("##############################################################");
                                reporting.alunosQuePossuemEmprestimo();
                                System.out.println("\nInforme a matrícula do aluno: ");
                                Scanner teclado7 = new Scanner(System.in);
                                matricula = teclado7.nextInt();
                                reporting.emprestimosPorAluno(matricula);
                                break;

                            case 4:
                                System.out.println("\n##############################################################");
                                System.out.println("                     EMPRÉSTIMOS VENCIDOS                     ");
                                System.out.println("##############################################################");
                                reporting.emprestimosVencidos();
                                break;

                            case 5:
                                System.out.println("\nRetornando...");
                                op = 8;
                                break;

                            default:
                                System.out.println("\nOpção Inválida!");
                                break;
                        }
                    }
                    break;

                case 6:
                    System.out.println("\nSaindo...");
                    return;

                default:
                    System.out.println("\nOpção Inválida!");
                    break;
            }
        }
    }
}
