package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Notas {

    private JFrame frmCadastroDenotas;
    private JTextField txtFldidProfessor;
    private JTextField txtFldIdDisciplina;
    private JTextField textFldIdTurma;
    private JTextField txtFldIdDataNota;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;
    private JTextField txtFldNota;
    private JTextField txtFldMatricula;

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

        txtFldidProfessor = new JTextField();
        txtFldidProfessor.setBounds(101, 69, 163, 35);
        txtFldidProfessor.setColumns(10);
        frmCadastroDenotas.getContentPane().add(txtFldidProfessor);

        txtFldIdDisciplina = new JTextField();
        txtFldIdDisciplina.setBounds(101, 113, 163, 35);
        txtFldIdDisciplina.setColumns(10);
        frmCadastroDenotas.getContentPane().add(txtFldIdDisciplina);

        textFldIdTurma = new JTextField();
        textFldIdTurma.setBounds(101, 158, 163, 35);
        textFldIdTurma.setColumns(10);
        frmCadastroDenotas.getContentPane().add(textFldIdTurma);

        txtFldIdDataNota = new JTextField();
        txtFldIdDataNota.setBounds(101, 248, 163, 35);
        txtFldIdDataNota.setColumns(10);
        frmCadastroDenotas.getContentPane().add(txtFldIdDataNota);

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
                if (conexao != null) {
                    String sql = "INSERT INTO notas(id_Professor, id_Disciplina, id_Turma, nr_Matricula, data_Nota, nota_Disciplina) VALUES (?, ?, ?, ?, ?, ?)";
                    try {
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, txtFldidProfessor.getText());
                        mypst.setString(2, txtFldIdDisciplina.getText());
                        mypst.setString(3, textFldIdTurma.getText());
                        mypst.setString(4, txtFldMatricula.getText());
                        mypst.setString(5, txtFldIdDataNota.getText());
                        mypst.setString(6, txtFldNota.getText());
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Nota cadastrada com sucesso!");
                        updateTable();
                        txtFldidProfessor.setText("");
                        txtFldIdDisciplina.setText("");
                        textFldIdTurma.setText("");
                        txtFldIdDataNota.setText("");
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
        });
        frmCadastroDenotas.getContentPane().add(btnInserir);

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAlterar.setBackground(new Color(255, 255, 255));
        btnAlterar.setBounds(686, 115, 89, 35);
        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String idProfessor = table.getValueAt(row, 1).toString();
                        String sql = "UPDATE notas SET  id_Disciplina=?, id_Turma=?, nr_Matricula=?,data_Nota=?, nota_Disciplina=? WHERE id_Professor=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, txtFldIdDisciplina.getText());
                            mypst.setString(2, textFldIdTurma.getText());
                            mypst.setString(3, txtFldMatricula.getText());
                            mypst.setString(4, txtFldIdDataNota.getText());
                            mypst.setString(5, txtFldNota.getText());
                            mypst.setString(6, idProfessor);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldidProfessor.setText("");
                            txtFldIdDisciplina.setText("");
                            textFldIdTurma.setText("");
                            txtFldMatricula.setText("");
                            txtFldIdDataNota.setText("");
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
                        JOptionPane.showMessageDialog(null, "Selecione uma nota na tabela para realizar a alteração.");
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
                    	String data_Nota = table.getValueAt(row, 4).toString();
                    	String sql = "DELETE FROM notas WHERE data_Nota=?";
                    	try {
                    	    mypst = conexao.prepareStatement(sql);
                    	    mypst.setString(1, data_Nota);
                    	    mypst.executeUpdate();
                    	    JOptionPane.showMessageDialog(null, "Nota excluída com sucesso!");
                    	    updateTable();
                    	    txtFldidProfessor.setText("");
                    	    txtFldIdDisciplina.setText("");
                    	    txtFldMatricula.setText("");
                    	    textFldIdTurma.setText("");
                    	    txtFldIdDataNota.setText("");
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
			    String data_Nota = JOptionPane.showInputDialog(null, "Informe a data da prova:");
			    String nr_Matricula = JOptionPane.showInputDialog(null, "Informe o número de matrícula:");
			    
			    if (data_Nota != null && !data_Nota.isEmpty() && nr_Matricula != null && !nr_Matricula.isEmpty()) {
			        String sql = "SELECT id_Professor, id_Disciplina, id_Turma, nr_Matricula, data_Nota, nota_Disciplina FROM notas WHERE data_Nota = ? AND nr_Matricula = ?";
			        conexao = Controle_EscolarConnection.ConnectDb();
			        
			        if (conexao != null) {
			            try {
			                mypst = conexao.prepareStatement(sql);
			                mypst.setString(1, data_Nota);
			                mypst.setString(2, nr_Matricula);
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
		frmCadastroDenotas.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldidProfessor.setText("");
		        txtFldIdDisciplina.setText("");
		        textFldIdTurma.setText("");
		        txtFldMatricula.setText("");
		        txtFldIdDataNota.setText("");
		        txtFldNota.setText("");
		    }
		});
        
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
                    txtFldidProfessor.setText(table.getValueAt(row, 0).toString());
                    txtFldIdDisciplina.setText(table.getValueAt(row, 1).toString());
                    textFldIdTurma.setText(table.getValueAt(row, 2).toString());
                    txtFldMatricula.setText(table.getValueAt(row, 3).toString());
                    txtFldIdDataNota.setText(table.getValueAt(row, 4).toString());
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
        
        txtFldMatricula = new JTextField();
        txtFldMatricula.setColumns(10);
        txtFldMatricula.setBounds(101, 203, 163, 35);
        frmCadastroDenotas.getContentPane().add(txtFldMatricula);
        
        JLabel lblNewLabel_3_1 = new JLabel("Nrº Matricula:");
        lblNewLabel_3_1.setFont(new Font("Arial", Font.BOLD, 13));
        lblNewLabel_3_1.setBounds(11, 205, 89, 31);
        frmCadastroDenotas.getContentPane().add(lblNewLabel_3_1);
        updateTable();
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
                    String matricula = myrs.getString("nr_Matricula");
                    String data_Nota = myrs.getString("data_Nota");
                    String nota = myrs.getString("nota_Disciplina");
                    model.addRow(new Object[]{idProfessor, idDisciplina, idTurma, matricula, data_Nota, nota});
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