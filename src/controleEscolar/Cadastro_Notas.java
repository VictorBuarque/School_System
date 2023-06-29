package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Notas {

    private JFrame frmCadastroDenotas;
    private JTextField txtFldDataNota;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;
    private JTextField txtFldNota;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadastro_Notas window = new Cadastro_Notas();
                    window.frmCadastroDenotas.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadastro_Notas() {
        initialize();
    }

    private void initialize() {
        frmCadastroDenotas = new JFrame();
        frmCadastroDenotas.getContentPane().setBounds(new Rectangle(0, 0, 800, 600));
        frmCadastroDenotas.getContentPane().setBackground(SystemColor.activeCaption);
        frmCadastroDenotas.setBackground(SystemColor.inactiveCaption);
        frmCadastroDenotas.setForeground(SystemColor.inactiveCaption);
        frmCadastroDenotas.setFont(new Font("Arial", Font.PLAIN, 14));
        frmCadastroDenotas.setTitle("Cadastro de notas");
        frmCadastroDenotas.setBounds(100, 100, 800, 500);
        frmCadastroDenotas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmCadastroDenotas.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("ID Prof.");
        model.addColumn("ID Disc.");
        model.addColumn("Id Turma");
        model.addColumn("Nrº Matri.");
        model.addColumn("Data Nota");
        model.addColumn("Nota Disc.");

        JComboBox<String> comboBox_1 = new JComboBox<>();
        comboBox_1.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox_1.setBounds(101, 71, 163, 31);
        frmCadastroDenotas.getContentPane().add(comboBox_1);

        JComboBox<String> comboBox_2 = new JComboBox<>();
        comboBox_2.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox_2.setBounds(101, 112, 163, 31);
        frmCadastroDenotas.getContentPane().add(comboBox_2);

        JComboBox<String> comboBox_3 = new JComboBox<>();
        comboBox_3.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox_3.setBounds(101, 156, 163, 31);
        frmCadastroDenotas.getContentPane().add(comboBox_3);

        JComboBox<String> comboBox_4 = new JComboBox<>();
        comboBox_4.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox_4.setBounds(101, 203, 163, 31);
        frmCadastroDenotas.getContentPane().add(comboBox_4);

        try {
            conexao = Controle_EscolarConnection.ConnectDb();
            if (conexao != null) {
                JOptionPane.showMessageDialog(frmCadastroDenotas, "Conexão com o banco de dados estabelecida com sucesso!");
                String sql = "SELECT * FROM professor";
                try (Statement stmt = conexao.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    comboBox_1.addItem("");
                    while (rs.next()) {
                        String idProfessor = rs.getString("nome_Professor");
                        comboBox_1.addItem(idProfessor);
                    }
                }

                sql = "SELECT * FROM disciplina";
                try (Statement stmt = conexao.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    comboBox_2.addItem("");
                    while (rs.next()) {
                        String idDisciplina = rs.getString("nome_Disciplina");
                        comboBox_2.addItem(idDisciplina);
                    }
                }

                sql = "SELECT * FROM turmas";
                try (Statement stmt = conexao.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    comboBox_3.addItem("");
                    while (rs.next()) {
                        String idTurma = rs.getString("id_Turma");
                        comboBox_3.addItem(idTurma);
                    }
                }

                sql = "SELECT * FROM alunos";
                try (Statement stmt = conexao.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    comboBox_4.addItem("");
                    while (rs.next()) {
                        String nrMatricula = rs.getString("nome_Aluno");
                        comboBox_4.addItem(nrMatricula);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frmCadastroDenotas, "Não foi possível conectar ao banco de dados.");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(frmCadastroDenotas, erro);
        }

        frmCadastroDenotas.setVisible(true);


        txtFldDataNota = new JTextField();
        txtFldDataNota.setBounds(101, 248, 163, 35);
        txtFldDataNota.setColumns(10);
        frmCadastroDenotas.getContentPane().add(txtFldDataNota);

        JLabel lblNewLabel = new JLabel("ID Professor:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(18, 71, 81, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDenotas.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("ID Disc.:");
        lblNewLabel_1.setBounds(46, 115, 54, 31);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDenotas.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("ID Turma:");
        lblNewLabel_2.setBounds(35, 160, 61, 31);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDenotas.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Data Nota:");
        lblNewLabel_3.setBounds(31, 250, 68, 31);
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDenotas.getContentPane().add(lblNewLabel_3);

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	conexao = Controle_EscolarConnection.ConnectDb();
    	    String idProfessor = obterIdProfessor((String) comboBox_1.getSelectedItem());
    	    String idDisciplina = obterIdDisciplina((String) comboBox_2.getSelectedItem());
    	    String idTurma = obterIdTurma((String) comboBox_3.getSelectedItem());
    	    String nrMatricula = obterNomeAluno((String) comboBox_4.getSelectedItem());
    	    String data = txtFldDataNota.getText();
    	    String nota = txtFldNota.getText();
    	    try {
    	        if (conexao != null) {
    	            String sqlInsert = "INSERT INTO notas (id_Professor, id_Disciplina, id_Turma, nr_Matricula, data_Nota, nota_Disciplina) VALUES (?, ?, ?, ?, ?,?)";
    	            PreparedStatement pstmtInsert = conexao.prepareStatement(sqlInsert);
    	            pstmtInsert.setString(1, idProfessor);
    	            pstmtInsert.setString(2, idDisciplina);
    	            pstmtInsert.setString(3, idTurma);
    	            pstmtInsert.setString(4, nrMatricula);
    	            pstmtInsert.setString(5, data);
    	            pstmtInsert.setString(6, nota);
    	            pstmtInsert.executeUpdate();
    	            pstmtInsert.close();
    	            conexao.close();
    	            JOptionPane.showMessageDialog(frmCadastroDenotas, "Dados salvos com sucesso!");
    	            updateTable();
    	            comboBox_1.setToolTipText("");
    	            comboBox_2.setToolTipText("");
    	            comboBox_3.setToolTipText("");
    	            comboBox_4.setToolTipText("");
    	            txtFldDataNota.setText("");
    	            txtFldNota.setText("");
    	        } else {
    	            JOptionPane.showMessageDialog(frmCadastroDenotas, "Não foi possível conectar ao banco de dados.");
    	        }
    	    } catch (SQLException ex) {
    	        JOptionPane.showMessageDialog(frmCadastroDenotas, "Erro ao incluir registro: " + ex.getMessage());
    	    }
    	 }
    }
);
        frmCadastroDenotas.getContentPane().add(btnInserir);

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAlterar.setBackground(new Color(255, 255, 255));
        btnAlterar.setBounds(686, 115, 89, 35);
        btnAlterar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                String idProfessor = obterIdProfessor((String) comboBox_1.getSelectedItem());
        	    String idDisciplina = obterIdDisciplina((String) comboBox_2.getSelectedItem());
        	    String idTurma = obterIdTurma((String) comboBox_3.getSelectedItem());
        	    String nrMatricula = obterNomeAluno((String) comboBox_4.getSelectedItem());
        	    String data = txtFldDataNota.getText();
        	    String nota = txtFldNota.getText();
                if (conexao != null) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String sql = "UPDATE notas SET id_Professor=?, id_Disciplina=?, id_Turma=?, nr_Matricula=?, nota_Disciplina=?WHERE data_Nota=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, idProfessor);
                            mypst.setString(2, idDisciplina);
                            mypst.setString(3, idTurma);
                            mypst.setString(4, nrMatricula);
                            mypst.setString(5, nota);
                            mypst.setString(6, data);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            comboBox_1.setToolTipText("");
            	            comboBox_2.setToolTipText("");
            	            comboBox_3.setToolTipText("");
            	            comboBox_4.setToolTipText("");
            	            txtFldDataNota.setText("");
            	            txtFldNota.setText("");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        } finally {
                            try {
                                myrs.close();
                                mypst.close();
                                conexao.close();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecione um aluno na tabela para realizar a alteração.");
                    }
                }
            }
        });
        frmCadastroDenotas.getContentPane().add(btnAlterar);
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 12));
        btnExcluir.setBackground(new Color(255, 255, 255));
        btnExcluir.setBounds(686, 158, 89, 35);
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                    	String data = table.getValueAt(row, 5).toString();
                    	String sql = "DELETE FROM notas WHERE data_Nota=?";
                    	try {
                    	    mypst = conexao.prepareStatement(sql);
                    	    mypst.setString(1, data);
                    	    mypst.executeUpdate();
                    	    JOptionPane.showMessageDialog(null, "Nota excluída com sucesso!");
                    	    updateTable();
                    	    comboBox_1.setToolTipText("");
            	            comboBox_2.setToolTipText("");
            	            comboBox_3.setToolTipText("");
            	            comboBox_4.setToolTipText("");
            	            txtFldDataNota.setText("");
            	            txtFldNota.setText("");
                    	} catch (Exception ex) {
                    	    JOptionPane.showMessageDialog(null, ex);
                    	} finally {
                    	    try {
                    	        myrs.close();
                    	        mypst.close();
                    	        conexao.close();
                    	    } catch (Exception ex) {
                    	        JOptionPane.showMessageDialog(null, ex);
                    	    }
                    	}
                    }
                }	
            }
        });
        frmCadastroDenotas.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		frmCadastroDenotas.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String data = JOptionPane.showInputDialog(null, "Informe a data da prova:");
			    String nrMatricula = JOptionPane.showInputDialog(null, "Informe o número de matrícula:");
			    
			    if (data != null && !data.isEmpty() && nrMatricula != null && !nrMatricula.isEmpty()) {
			        String sql = "SELECT id_Professor, id_Disciplina, id_Turma, nr_Matricula, data_Nota, nota_Disciplina FROM notas WHERE data_Nota = ? AND nr_Matricula = ?";
			        conexao = Controle_EscolarConnection.ConnectDb();
			        
			        if (conexao != null) {
			            try {
			                mypst = conexao.prepareStatement(sql);
			                mypst.setString(1, data);
			                mypst.setString(2, nrMatricula);
			                myrs = mypst.executeQuery();
			                
			                if (myrs.next()) {
			                    JOptionPane.showMessageDialog(null, "id_Professor: " + myrs.getString("id_Professor")
			                            + "\nid_Disciplina: " + myrs.getString("id_Disciplina")
			                            + "\nid_Turma: " + myrs.getString("id_Turma")
			                            + "\nnr_Matricula: " + myrs.getString("nr_Matricula")
			                            + "\ndata_Nota: " + myrs.getString("data_Nota")
			                            + "\nnota_Disciplina: " + myrs.getString("nota_Disciplina"));
			                } else {
			                    JOptionPane.showMessageDialog(null, "Nota não encontrada.");
			                }
			            } catch (Exception ex) {
			                JOptionPane.showMessageDialog(null, ex);
			            } finally {
			                try {
			                    myrs.close();
			                    mypst.close();
			                    conexao.close();
			                } catch (Exception ex) {
			                    JOptionPane.showMessageDialog(null, ex);
			                }
			            }
			        }
			    }
			}

		});
        
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setForeground(new Color(0, 0, 0));
		btnLimpar.setBackground(new Color(255, 255, 255));
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 12));
		btnLimpar.setBounds(686, 248, 89, 35);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        comboBox_1.setSelectedIndex(0);
		        comboBox_2.setSelectedIndex(0);
		        comboBox_3.setSelectedIndex(0);
		        comboBox_4.setSelectedIndex(0);
		        txtFldDataNota.setText("");
		        txtFldNota.setText("");
		    }
		});
		frmCadastroDenotas.getContentPane().add(btnLimpar);
		
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        frmCadastroDenotas.getContentPane().add(btnImprimir);
        btnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    table.print();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao imprimir tabela: " + ex.getMessage());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(274, 67, 402, 342);
        frmCadastroDenotas.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	frmCadastroDenotas.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    comboBox_1.setToolTipText(table.getValueAt(row, 0).toString());
    	            comboBox_2.setToolTipText(table.getValueAt(row, 1).toString());
    	            comboBox_3.setToolTipText(table.getValueAt(row, 2).toString());
    	            comboBox_4.setToolTipText(table.getValueAt(row, 3).toString());
    	            txtFldDataNota.setText(table.getValueAt(row, 4).toString());
                    txtFldNota.setText(table.getValueAt(row, 5).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de notas");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        frmCadastroDenotas.getContentPane().add(lblNewLabel_11_1);
        
        txtFldNota = new JTextField();
        txtFldNota.setColumns(10);
        txtFldNota.setBounds(101, 293, 163, 35);
        frmCadastroDenotas.getContentPane().add(txtFldNota);
        
        JLabel lblTurma = new JLabel("Nrº nota:");
        lblTurma.setLabelFor(txtFldNota);
        lblTurma.setFont(new Font("Arial", Font.BOLD, 13));
        lblTurma.setBounds(42, 295, 54, 31);
        frmCadastroDenotas.getContentPane().add(lblTurma);
        
        JLabel lblNewLabel_3_1 = new JLabel("Nrº Matricula:");
        lblNewLabel_3_1.setFont(new Font("Arial", Font.BOLD, 13));
        lblNewLabel_3_1.setBounds(11, 205, 89, 31);
        frmCadastroDenotas.getContentPane().add(lblNewLabel_3_1);
        
        
        updateTable();
    }
    private String obterIdProfessor(String nomeProfessor) {
        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT id_Professor FROM professor WHERE nome_Professor = ?";
                try (PreparedStatement pstmt = myConn.prepareStatement(sql)) {
                    pstmt.setString(1, nomeProfessor);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("id_Professor");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmCadastroDenotas, "Erro ao obter ID do Professor: " + ex.getMessage());
        }
        return null;
    }

    private String obterIdDisciplina(String nomeDisciplina) {
        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT id_Disciplina FROM disciplina WHERE nome_Disciplina = ?";
                try (PreparedStatement pstmt = myConn.prepareStatement(sql)) {
                    pstmt.setString(1, nomeDisciplina);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("id_Disciplina");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmCadastroDenotas, "Erro ao obter ID da disciplina: " + ex.getMessage());
        }
        return null;
    }
    private String obterIdTurma(String idTurma) {
        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT id_Turma FROM turmas WHERE id_Turma = ?";
                try (PreparedStatement pstmt = myConn.prepareStatement(sql)) {
                    pstmt.setString(1, idTurma);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("id_Turma");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmCadastroDenotas, "Erro ao obter ID da Turma: " + ex.getMessage());
        }
        return null;
    }
    private String obterNomeAluno(String nrMatricula) {
        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT nr_Matricula FROM alunos WHERE nome_Aluno = ?";
                try (PreparedStatement pstmt = myConn.prepareStatement(sql)) {
                    pstmt.setString(1, nrMatricula);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("nr_Matricula");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmCadastroDenotas, "Erro ao obter o Nrº de Matricula do(a) aluno(a): " + ex.getMessage());
        }
        return null;
    }	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM notas";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String idProfessor = myrs.getString("id_Professor");
                    String idDisciplina = myrs.getString("id_Disciplina");
                    String idTurma = myrs.getString("id_Turma");
                    String nrMatricula = myrs.getString("nr_Matricula");
                    String data = myrs.getString("data_Nota");
                    String nota = myrs.getString("nota_Disciplina");
                    model.addRow(new Object[]{idProfessor, idDisciplina, idTurma, nrMatricula, data, nota});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            } finally {
                try {
                    myrs.close();
                    mypst.close();
                    conexao.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    }
 }