package task1;

import javax.swing.JOptionPane;

abstract class Funcionario {
    protected String nome;
    protected String id;

    Funcionario(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    abstract double calcularSalario();

    String fichaHtml() {
        return String.format(
                "<html><b>Funcionário</b><br>" +
                "Nome: %s<br>" +
                "ID: %s<br>" +
                "Tipo: %s<br>" +
                "Salário: <b>R$ %.2f</b></html>",
                nome, id, getClass().getSimpleName(), calcularSalario()
        );
    }
}

class Horista extends Funcionario {
    private int horas;
    private double valorHora;

    Horista(String nome, String id, int horas, double valorHora) {
        super(nome, id);
        this.horas = horas;
        this.valorHora = valorHora;
    }

    @Override
    double calcularSalario() {
        return horas * valorHora;
    }
}

class Assalariado extends Funcionario {
    private double salarioFixo;

    Assalariado(String nome, String id, double salarioFixo) {
        super(nome, id);
        this.salarioFixo = salarioFixo;
    }

    @Override
    double calcularSalario() {
        return salarioFixo;
    }
}

class Comissionado extends Funcionario {
    private double salarioBase;
    private double vendas;
    private double taxa;

    Comissionado(String nome, String id, double salarioBase, double vendas, double taxa) {
        super(nome, id);
        this.salarioBase = salarioBase;
        this.vendas = vendas;
        this.taxa = taxa;
    }

    @Override
    double calcularSalario() {
        return salarioBase + vendas * (taxa / 100.0);
    }
}

public class SistemaFuncionarios {

    public static void main(String[] args) {
        String[] tipos = {"Horista", "Assalariado", "Comissionado", "Sair"};

        while (true) {
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Tipo de funcionário:",
                    "Novo Funcionário",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tipos,
                    tipos[0]
            );

            if (escolha >= 3 || escolha == JOptionPane.CLOSED_OPTION) {
                break;
            }

            String nome = JOptionPane.showInputDialog("Nome do funcionário:");
            if (nome == null) break;

            String id = JOptionPane.showInputDialog("ID do funcionário:");
            if (id == null) break;

            try {
                Funcionario funcionario;

                if (escolha == 0) {
                    int horas = Integer.parseInt(
                            JOptionPane.showInputDialog("Quantidade de horas:")
                    );
                    double valorHora = Double.parseDouble(
                            JOptionPane.showInputDialog("Valor por hora:")
                    );

                    funcionario = new Horista(nome, id, horas, valorHora);

                } else if (escolha == 1) {
                    double salario = Double.parseDouble(
                            JOptionPane.showInputDialog("Salário fixo:")
                    );

                    funcionario = new Assalariado(nome, id, salario);

                } else {
                    double salarioBase = Double.parseDouble(
                            JOptionPane.showInputDialog("Salário base:")
                    );
                    double vendas = Double.parseDouble(
                            JOptionPane.showInputDialog("Valor total das vendas:")
                    );
                    double taxa = Double.parseDouble(
                            JOptionPane.showInputDialog("Taxa de comissão (%):")
                    );

                    funcionario = new Comissionado(
                            nome, id, salarioBase, vendas, taxa
                    );
                }

                JOptionPane.showMessageDialog(
                        null,
                        funcionario.fichaHtml(),
                        "Ficha do Funcionário",
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
}
