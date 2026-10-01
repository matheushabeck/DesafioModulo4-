package task2;

import javax.swing.JOptionPane;
import java.util.ArrayList;

abstract class Person {
    protected String nome;
    protected String cpf;
    protected String dataNascimento;

    Person(String nome, String cpf, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    abstract String perfil();
}

class Aluno extends Person {
    private String matricula;
    private String curso;
    private int periodoAtual;

    Aluno(String nome, String cpf, String dataNascimento,
          String matricula, String curso, int periodo) {
        super(nome, cpf, dataNascimento);
        this.matricula = matricula;
        this.curso = curso;
        this.periodoAtual = periodo;
    }

    @Override
    String perfil() {
        return String.format(
                "<html><b>👩‍🎓 ALUNO</b><br>" +
                "Nome: %s<br>" +
                "CPF: %s<br>" +
                "Nascimento: %s<br>" +
                "Matrícula: %s<br>" +
                "Curso: %s<br>" +
                "Período atual: %dº</html>",
                nome, cpf, dataNascimento, matricula, curso, periodoAtual
        );
    }
}

class Professor extends Person {
    private String departamento;
    private String titulacao;
    private double salario;

    Professor(String nome, String cpf, String dataNascimento,
              String departamento, String titulacao, double salario) {
        super(nome, cpf, dataNascimento);
        this.departamento = departamento;
        this.titulacao = titulacao;
        this.salario = salario;
    }

    @Override
    String perfil() {
        return String.format(
                "<html><b>👩‍🏫 PROFESSOR</b><br>" +
                "Nome: %s<br>" +
                "CPF: %s<br>" +
                "Nascimento: %s<br>" +
                "Departamento: %s<br>" +
                "Titulação: %s<br>" +
                "Salário: R$ %.2f</html>",
                nome, cpf, dataNascimento, departamento, titulacao, salario
        );
    }
}

public class SistemaUniversidade {
    static ArrayList<Person> pessoas = new ArrayList<>();

    public static void main(String[] args) {
        String[] opcoes = {
                "Cadastrar Aluno",
                "Cadastrar Professor",
                "Listar Todos",
                "Filtrar por Tipo",
                "Sair"
        };

        while (true) {
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Escolha uma opção:",
                    "Sistema Universidade",
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
                case 0 -> cadastrarAluno();
                case 1 -> cadastrarProfessor();
                case 2 -> listarTodos();
                case 3 -> filtrarPorTipo();
            }
        }
    }

    static void cadastrarAluno() {
        String nome = JOptionPane.showInputDialog("Nome:");
        if (nome == null) return;

        String cpf = JOptionPane.showInputDialog("CPF:");
        if (cpf == null) return;

        String nascimento = JOptionPane.showInputDialog("Data de nascimento:");
        if (nascimento == null) return;

        String matricula = JOptionPane.showInputDialog("Matrícula:");
        if (matricula == null) return;

        String curso = JOptionPane.showInputDialog("Curso:");
        if (curso == null) return;

        try {
            int periodo = Integer.parseInt(
                    JOptionPane.showInputDialog("Período atual:")
            );

            pessoas.add(new Aluno(
                    nome, cpf, nascimento, matricula, curso, periodo
            ));

            JOptionPane.showMessageDialog(null, "Aluno cadastrado!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "O período deve ser um número.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    static void cadastrarProfessor() {
        String nome = JOptionPane.showInputDialog("Nome:");
        if (nome == null) return;

        String cpf = JOptionPane.showInputDialog("CPF:");
        if (cpf == null) return;

        String nascimento = JOptionPane.showInputDialog("Data de nascimento:");
        if (nascimento == null) return;

        String departamento = JOptionPane.showInputDialog("Departamento:");
        if (departamento == null) return;

        String[] titulacoes = {"Mestre", "Doutor", "PhD"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Titulação:",
                "Professor",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                titulacoes,
                titulacoes[0]
        );

        if (escolha == JOptionPane.CLOSED_OPTION) return;

        try {
            double salario = Double.parseDouble(
                    JOptionPane.showInputDialog("Salário:")
            );

            pessoas.add(new Professor(
                    nome,
                    cpf,
                    nascimento,
                    departamento,
                    titulacoes[escolha],
                    salario
            ));

            JOptionPane.showMessageDialog(null, "Professor cadastrado!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "O salário deve ser um número.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    static void listarTodos() {
        if (pessoas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma pessoa cadastrada.");
            return;
        }

        StringBuilder texto = new StringBuilder("<html><b>Lista de Pessoas</b><br><br>");

        for (Person pessoa : pessoas) {
            if (pessoa instanceof Aluno) {
                texto.append("👩‍🎓 ");
            } else if (pessoa instanceof Professor) {
                texto.append("👩‍🏫 ");
            }

            texto.append(pessoa.nome).append("<br>");
        }

        texto.append("</html>");

        JOptionPane.showMessageDialog(
                null,
                texto.toString(),
                "Todos",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    static void filtrarPorTipo() {
        String[] tipos = {"Aluno", "Professor"};

        int escolha = JOptionPane.showOptionDialog(
                null,
                "Qual tipo deseja listar?",
                "Filtrar por Tipo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );

        if (escolha == JOptionPane.CLOSED_OPTION) return;

        StringBuilder texto = new StringBuilder("<html><b>Resultado</b><br><br>");
        boolean encontrou = false;

        for (Person pessoa : pessoas) {
            if (escolha == 0 && pessoa instanceof Aluno) {
                texto.append(pessoa.perfil()).append("<br><br>");
                encontrou = true;
            } else if (escolha == 1 && pessoa instanceof Professor) {
                texto.append(pessoa.perfil()).append("<br><br>");
                encontrou = true;
            }
        }

        if (!encontrou) {
            JOptionPane.showMessageDialog(null, "Nenhuma pessoa desse tipo cadastrada.");
        } else {
            texto.append("</html>");
            JOptionPane.showMessageDialog(
                    null,
                    texto.toString(),
                    "Filtro",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
