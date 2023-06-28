package controleEscolar;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class Cadastro_Alunos_Turma {

    private JFrame frame;
    private JComboBox<String> comboBox;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> innerListModel;
    private JList<String> listMaterias;
    private JList<String> list;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadastro_Alunos_Turma window = new Cadastro_Alunos_Turma();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadastro_Alunos_Turma() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.inactiveCaption);
        frame.setForeground(SystemColor.activeCaption);
        frame.setBackground(SystemColor.activeCaption);
        frame.setBounds(100, 100, 800, 600);
        frame.getContentPane().setLayout(null);

        JLabel lblProfessores = new JLabel("Aluna(o)");
        lblProfessores.setHorizontalAlignment(SwingConstants.CENTER);
        lblProfessores.setFont(new Font("Arial", Font.BOLD, 14));
        lblProfessores.setBounds(97, 37, 234, 23);
        frame.getContentPane().add(lblProfessores);

        JLabel lblDisciplinas1 = new JLabel("Turmas Disponíveis");
        lblDisciplinas1.setBorder(null);
        lblDisciplinas1.setHorizontalAlignment(SwingConstants.CENTER);
        lblDisciplinas1.setFont(new Font("Arial Black", Font.BOLD, 14));
        lblDisciplinas1.setBounds(97, 96, 234, 23);
        frame.getContentPane().add(lblDisciplinas1);

        JLabel lblDisciplinas2 = new JLabel("Turmas Selecionadas");
        lblDisciplinas2.setBorder(null);
        lblDisciplinas2.setHorizontalAlignment(SwingConstants.CENTER);
        lblDisciplinas2.setFont(new Font("Arial Black", Font.BOLD, 14));
        lblDisciplinas2.setBounds(457, 96, 234, 23);
        frame.getContentPane().add(lblDisciplinas2);

        comboBox = new JComboBox<>();
        comboBox.setBounds(97, 63, 235, 23);
        frame.getContentPane().add(comboBox);

        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT * FROM alunos";
                try (Statement stmt = myConn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        String nomeAluno = rs.getString("nome_Aluno");
                        comboBox.addItem(nomeAluno);
                    }
                }
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(frame, erro);
        }

        frame.setVisible(true);

        listModel = new DefaultListModel<>();
        listMaterias = new JList<>(listModel);
        listMaterias.setBounds(97, 129, 235, 374);
        frame.getContentPane().add(listMaterias);

        innerListModel = new DefaultListModel<>();
        list = new JList<>(innerListModel);
        list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        list.setBounds(457, 126, 235, 374);
        frame.getContentPane().add(list);

        JButton btnPassar = new JButton(">>");
        btnPassar.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnPassar.setBounds(353, 202, 90, 30);
        frame.getContentPane().add(btnPassar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
            }
        });
        btnLimpar.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnLimpar.setBounds(353, 282, 90, 30);
        frame.getContentPane().add(btnLimpar);

        JButton btnRemover = new JButton("<<");
        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> elementosSelecionados = list.getSelectedValuesList();

                for (String elemento : elementosSelecionados) {
                    innerListModel.removeElement(elemento);
                }
            }
        });
        btnRemover.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnRemover.setBounds(353, 242, 90, 30);
        frame.getContentPane().add(btnRemover);

        JButton btnDefinir = new JButton("Definir");
        btnDefinir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Confirmar inclusão do registro?", "Definir cronograma",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        String idCurso = obterIdCurso((String) comboBox.getSelectedItem());
                        if (idCurso != null) {
                            List<String> elementosSelecionados = new ArrayList<>();
                            DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();

                            for (int i = 0; i < listModel.getSize(); i++) {
                                String elemento = listModel.getElementAt(i);
                                elementosSelecionados.add(elemento);
                            }

                            try {
                                var myConn = Controle_EscolarConnection.ConnectDb();
                                if (myConn != null) {
                                    String sqlInsert = "INSERT INTO aluno_turma (nr_Matricula, id_Turma) VALUES (?, ?)";
                                    PreparedStatement pstmtInsert = myConn.prepareStatement(sqlInsert);

                                    for (String elemento : elementosSelecionados) {
                                        String idTurma = obteridTurma(elemento);
                                        if (idTurma != null) {
                                            pstmtInsert.setString(1, idCurso);
                                            pstmtInsert.setString(2, idTurma);
                                            pstmtInsert.executeUpdate();
                                        }
                                    }

                                    pstmtInsert.close();
                                    myConn.close();

                                    JOptionPane.showMessageDialog(frame, "Definido com sucesso");
                                    listModel.clear();
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(frame, "Erro ao incluir registro: " + ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Preencha todos os campos obrigatórios");
                    }
                }
            }
        );
        btnDefinir.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnDefinir.setBounds(353, 322, 90, 30);
        frame.getContentPane().add(btnDefinir);

        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT * FROM turmas";
                try (Statement stmt = myConn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        String nomeDisciplina = rs.getString("id_Turma");
                        listModel.addElement(nomeDisciplina);
                    }
                }
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(frame, erro);
        }

        btnPassar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> elementosSelecionados = listMaterias.getSelectedValuesList();

                for (String elemento : elementosSelecionados) {
                    innerListModel.addElement(elemento);
                }
            }
        });
    }

    private String obterIdCurso(String nomeAluno) {
        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT nr_Matricula FROM alunos WHERE nome_Aluno = ?";
                try (PreparedStatement pstmt = myConn.prepareStatement(sql)) {
                    pstmt.setString(1, nomeAluno);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("nr_Matricula");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao obter ID do curso: " + ex.getMessage());
        }
        return null;
    }

    private String obteridTurma(String nomeDisciplina) {
        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT id_Turma FROM turmas WHERE id_Turma = ?";
                try (PreparedStatement pstmt = myConn.prepareStatement(sql)) {
                    pstmt.setString(1, nomeDisciplina);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("id_Turma");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao obter ID da disciplina: " + ex.getMessage());
        }
        return null;
    }
}