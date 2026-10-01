package task4;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Date;

abstract class GeradorRelatorio {
    protected String titulo;
    protected String empresa;

    GeradorRelatorio(String titulo, String empresa) {
        this.titulo = titulo;
        this.empresa = empresa;
    }

    // Template Method: a estrutura do relatório não pode ser alterada.
    final String gerar() {
        return cabecalho() + dados() + rodape();
    }

    private String cabecalho() {
        return String.format(
                "<html><h2>%s</h2>" +
                "<b>Empresa:</b> %s<br><hr><br>",
                titulo, empresa
        );
    }

    abstract String dados();

    private String rodape() {
        return String.format(
                "<br><hr><small>Gerado em: %s | POO Academy</small></html>",
                new Date()
        );
    }
}

class RelatorioFuncionarios extends GeradorRelatorio {
    private ArrayList<String[]> funcionarios;

    RelatorioFuncionarios(String empresa) {
        super("Relatório de Funcionários", empresa);
        funcionarios = new ArrayList<>();
    }

    void addFuncionario(String nome, String cargo, double salario) {
        funcionarios.add(new String[]{
                nome,
                cargo,
                String.format("%.2f", salario)
        });
    }

    @Override
    String dados() {
        StringBuilder html = new StringBuilder();

        html.append("<table border='1' cellpadding='5'>");
        html.append("<tr><th>Nome</th><th>Cargo</th><th>Salário</th></tr>");

        for (String[] funcionario : funcionarios) {
            html.append("<tr>")
                    .append("<td>").append(funcionario[0]).append("</td>")
                    .append("<td>").append(funcionario[1]).append("</td>")
                    .append("<td>R$ ").append(funcionario[2]).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");

        if (funcionarios.isEmpty()) {
            return "<i>Nenhum funcionário informado.</i>";
        }

        return html.toString();
    }
}

class RelatorioFinanceiro extends GeradorRelatorio {
    private double receita;
    private double despesas;
    private String periodo;

    RelatorioFinanceiro(String empresa, double receita, double despesas, String periodo) {
        super("Relatório Financeiro", empresa);
        this.receita = receita;
        this.despesas = despesas;
        this.periodo = periodo;
    }

    @Override
    String dados() {
        double resultado = receita - despesas;

        return String.format(
                "<b>Período:</b> %s<br><br>" +
                "<b>Receita:</b> R$ %.2f<br>" +
                "<b>Despesas:</b> R$ %.2f<br>" +
                "<b>Resultado:</b> R$ %.2f",
                periodo,
                receita,
                despesas,
                resultado
        );
    }
}

class RelatorioAcademico extends GeradorRelatorio {
    private String aluno;
    private String curso;
    private String disciplina;
    private double media;
    private int faltas;

    RelatorioAcademico(
            String empresa,
            String aluno,
            String curso,
            String disciplina,
            double media,
            int faltas
    ) {
        super("Relatório Acadêmico", empresa);
        this.aluno = aluno;
        this.curso = curso;
        this.disciplina = disciplina;
        this.media = media;
        this.faltas = faltas;
    }

    @Override
    String dados() {
        String situacao = media >= 7 && faltas <= 25
                ? "Aprovado"
                : "Reprovado";

        return String.format(
                "<b>Aluno:</b> %s<br>" +
                "<b>Curso:</b> %s<br>" +
                "<b>Disciplina:</b> %s<br>" +
                "<b>Média:</b> %.2f<br>" +
                "<b>Faltas:</b> %d%%<br>" +
                "<b>Situação:</b> %s",
                aluno,
                curso,
                disciplina,
                media,
                faltas,
                situacao
        );
    }
}

public class SistemaRelatorios {

    public static void main(String[] args) {
        String empresa = JOptionPane.showInputDialog("Nome da empresa:");

        if (empresa == null || empresa.trim().isEmpty()) {
            return;
        }

        String[] tipos = {
                "Funcionários",
                "Financeiro",
                "Acadêmico",
                "Sair"
        };

        while (true) {
            int tipo = JOptionPane.showOptionDialog(
                    null,
                    "Tipo de Relatório:",
                    "Relatórios",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tipos,
                    tipos[0]
            );

            if (tipo >= 3 || tipo == JOptionPane.CLOSED_OPTION) {
                break;
            }

            try {
                GeradorRelatorio relatorio;

                if (tipo == 0) {
                    relatorio = criarRelatorioFuncionarios(empresa);

                } else if (tipo == 1) {
                    relatorio = criarRelatorioFinanceiro(empresa);

                } else {
                    relatorio = criarRelatorioAcademico(empresa);
                }

                JOptionPane.showMessageDialog(
                        null,
                        relatorio.gerar(),
                        "Relatório",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Digite valores numéricos válidos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    static RelatorioFuncionarios criarRelatorioFuncionarios(String empresa) {
        RelatorioFuncionarios relatorio =
                new RelatorioFuncionarios(empresa);

        int quantidade = Integer.parseInt(
                JOptionPane.showInputDialog("Quantidade de funcionários:")
        );

        for (int i = 1; i <= quantidade; i++) {
            String nome = JOptionPane.showInputDialog(
                    "Funcionário " + i + " - Nome:"
            );

            String cargo = JOptionPane.showInputDialog(
                    "Funcionário " + i + " - Cargo:"
            );

            double salario = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            "Funcionário " + i + " - Salário:"
                    )
            );

            relatorio.addFuncionario(nome, cargo, salario);
        }

        return relatorio;
    }

    static RelatorioFinanceiro criarRelatorioFinanceiro(String empresa) {
        String periodo = JOptionPane.showInputDialog("Período:");

        double receita = Double.parseDouble(
                JOptionPane.showInputDialog("Receita total:")
        );

        double despesas = Double.parseDouble(
                JOptionPane.showInputDialog("Despesas totais:")
        );

        return new RelatorioFinanceiro(
                empresa,
                receita,
                despesas,
                periodo
        );
    }

    static RelatorioAcademico criarRelatorioAcademico(String empresa) {
        String aluno = JOptionPane.showInputDialog("Nome do aluno:");
        String curso = JOptionPane.showInputDialog("Curso:");
        String disciplina = JOptionPane.showInputDialog("Disciplina:");

        double media = Double.parseDouble(
                JOptionPane.showInputDialog("Média final:")
        );

        int faltas = Integer.parseInt(
                JOptionPane.showInputDialog("Percentual de faltas:")
        );

        return new RelatorioAcademico(
                empresa,
                aluno,
                curso,
                disciplina,
                media,
                faltas
        );
    }
}
