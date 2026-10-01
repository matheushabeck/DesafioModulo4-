package task3;

import javax.swing.JOptionPane;
import java.util.ArrayList;

abstract class Funcionario {
    protected String nome;
    protected String regime;

    Funcionario(String nome, String regime) {
        this.nome = nome;
        this.regime = regime;
    }

    abstract double calcularSalarioBruto();

    double calcularDescontos() {
        double bruto = calcularSalarioBruto();

        if ("CLT".equals(regime)) {
            return bruto * 0.11 + bruto * 0.075;
        }

        return bruto * 0.15;
    }

    double calcularSalarioLiquido() {
        return calcularSalarioBruto() - calcularDescontos();
    }

    String holerite() {
        double bruto = calcularSalarioBruto();
        double desc = calcularDescontos();
        double liquido = bruto - desc;

        return String.format(
                "<html><b>Holerite — %s</b><br>" +
                "Tipo: %s<br>" +
                "Regime: %s<br>" +
                "Bruto: R$ %.2f<br>" +
                "Descontos: - R$ %.2f<br>" +
                "<b>Líquido: R$ %.2f</b></html>",
                nome,
                getClass().getSimpleName(),
                regime,
                bruto,
                desc,
                liquido
        );
    }
}

abstract class FuncionarioEfetivo extends Funcionario {
    FuncionarioEfetivo(String nome) {
        super(nome, "CLT");
    }

    double calcularDecimoTerceiro() {
        return calcularSalarioBruto();
    }

    double calcularFerias() {
        return calcularSalarioBruto() + calcularSalarioBruto() / 3.0;
    }
}

class Horista extends FuncionarioEfetivo {
    private int horas;
    private double valorHora;

    Horista(String nome, int horas, double valorHora) {
        super(nome);
        this.horas = horas;
        this.valorHora = valorHora;
    }

    @Override
    double calcularSalarioBruto() {
        return horas * valorHora;
    }

    @Override
    String holerite() {
        double bruto = calcularSalarioBruto();
        double desc = calcularDescontos();
        double liquido = calcularSalarioLiquido();

        return String.format(
                "<html><b>Holerite — %s</b><br>" +
                "Tipo: Horista CLT<br>" +
                "Horas: %d<br>" +
                "Valor/hora: R$ %.2f<br>" +
                "Bruto: R$ %.2f<br>" +
                "INSS (11%%): R$ %.2f<br>" +
                "IR (7,5%%): R$ %.2f<br>" +
                "Descontos: - R$ %.2f<br>" +
                "13º: R$ %.2f<br>" +
                "Férias + 1/3: R$ %.2f<br>" +
                "<b>Líquido: R$ %.2f</b></html>",
                nome,
                horas,
                valorHora,
                bruto,
                bruto * 0.11,
                bruto * 0.075,
                desc,
                calcularDecimoTerceiro(),
                calcularFerias(),
                liquido
        );
    }
}

class Assalariado extends FuncionarioEfetivo {
    private double salarioFixo;

    Assalariado(String nome, double salarioFixo) {
        super(nome);
        this.salarioFixo = salarioFixo;
    }

    @Override
    double calcularSalarioBruto() {
        return salarioFixo;
    }

    @Override
    String holerite() {
        double bruto = calcularSalarioBruto();
        double desc = calcularDescontos();

        return String.format(
                "<html><b>Holerite — %s</b><br>" +
                "Tipo: Assalariado CLT<br>" +
                "Bruto: R$ %.2f<br>" +
                "INSS (11%%): R$ %.2f<br>" +
                "IR (7,5%%): R$ %.2f<br>" +
                "Descontos: - R$ %.2f<br>" +
                "13º: R$ %.2f<br>" +
                "Férias + 1/3: R$ %.2f<br>" +
                "<b>Líquido: R$ %.2f</b></html>",
                nome,
                bruto,
                bruto * 0.11,
                bruto * 0.075,
                desc,
                calcularDecimoTerceiro(),
                calcularFerias(),
                calcularSalarioLiquido()
        );
    }
}

class FuncionarioPJ extends Funcionario {
    private double valorContrato;

    FuncionarioPJ(String nome, double valorContrato) {
        super(nome, "PJ");
        this.valorContrato = valorContrato;
    }

    @Override
    double calcularSalarioBruto() {
        return valorContrato;
    }

    @Override
    String holerite() {
        double bruto = calcularSalarioBruto();
        double ir = bruto * 0.15;

        return String.format(
                "<html><b>Holerite — %s</b><br>" +
                "Tipo: Funcionário PJ<br>" +
                "Regime: PJ<br>" +
                "Valor do contrato: R$ %.2f<br>" +
                "Bruto: R$ %.2f<br>" +
                "IR (15%%): R$ %.2f<br>" +
                "Descontos: - R$ %.2f<br>" +
                "<b>Líquido: R$ %.2f</b><br>" +
                "Sem benefícios CLT.</html>",
                nome,
                valorContrato,
                bruto,
                ir,
                ir,
                calcularSalarioLiquido()
        );
    }
}

public class FolhaDePagamento {
    static ArrayList<Funcionario> equipe = new ArrayList<>();

    public static void main(String[] args) {
        String[] opcoes = {
                "Adicionar CLT",
                "Adicionar PJ",
                "Ver Holerites",
                "Relatório Final",
                "Sair"
        };

        while (true) {
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Escolha uma opção:",
                    "Folha de Pagamento",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            if (escolha == JOptionPane.CLOSED_OPTION || escolha == 4) {
                break;
            }

            switch (escolha) {
                case 0 -> adicionarCLT();
                case 1 -> adicionarPJ();
                case 2 -> verHolerites();
                case 3 -> gerarRelatorio();
            }
        }
    }

    static void adicionarCLT() {
        String[] tipos = {"Horista", "Assalariado"};

        int tipo = JOptionPane.showOptionDialog(
                null,
                "Tipo de funcionário CLT:",
                "Adicionar CLT",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );

        if (tipo == JOptionPane.CLOSED_OPTION) return;

        String nome = JOptionPane.showInputDialog("Nome:");
        if (nome == null) return;

        try {
            if (tipo == 0) {
                int horas = Integer.parseInt(
                        JOptionPane.showInputDialog("Horas trabalhadas:")
                );
                double valorHora = Double.parseDouble(
                        JOptionPane.showInputDialog("Valor por hora:")
                );

                equipe.add(new Horista(nome, horas, valorHora));

            } else {
                double salario = Double.parseDouble(
                        JOptionPane.showInputDialog("Salário fixo:")
                );

                equipe.add(new Assalariado(nome, salario));
            }

            JOptionPane.showMessageDialog(null, "Funcionário CLT adicionado!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Digite valores numéricos válidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    static void adicionarPJ() {
        String nome = JOptionPane.showInputDialog("Nome:");
        if (nome == null) return;

        try {
            double contrato = Double.parseDouble(
                    JOptionPane.showInputDialog("Valor do contrato:")
            );

            equipe.add(new FuncionarioPJ(nome, contrato));

            JOptionPane.showMessageDialog(null, "Funcionário PJ adicionado!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Digite um valor numérico válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    static void verHolerites() {
        if (equipe.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado.");
            return;
        }

        for (Funcionario funcionario : equipe) {
            JOptionPane.showMessageDialog(
                    null,
                    funcionario.holerite(),
                    "Holerite",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    static void gerarRelatorio() {
        if (equipe.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado.");
            return;
        }

        double total = 0;
        double totalCLT = 0;
        double totalPJ = 0;

        for (Funcionario funcionario : equipe) {
            double liquido = funcionario.calcularSalarioLiquido();
            total += liquido;

            if ("CLT".equals(funcionario.regime)) {
                totalCLT += liquido;
            } else {
                totalPJ += liquido;
            }
        }

        String relatorio = String.format(
                "<html><b>RELATÓRIO FINAL DA FOLHA</b><br><br>" +
                "Funcionários: %d<br>" +
                "Custo líquido CLT: R$ %.2f<br>" +
                "Custo líquido PJ: R$ %.2f<br>" +
                "<hr>" +
                "<b>Custo total da empresa: R$ %.2f</b></html>",
                equipe.size(),
                totalCLT,
                totalPJ,
                total
        );

        JOptionPane.showMessageDialog(
                null,
                relatorio,
                "Relatório Final",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
