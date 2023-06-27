package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Turmas {

    private JFrame frmCadastroDeTurmas;
    private JTextField txtFldIDTurma;
    private JTextField txtFldIdCurso;
    private JTextField textFldIdData;
    private JTextField txtFldIdturno_Turma;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;
    private JTextField txtFldMatricula;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadastro_Turmas window = new Cadastro_Turmas();
                    window.frmCadastroDeTurmas.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadastro_Turmas() {
        initialize();
    }

    private void initialize() {
        frmCadastroDeTurmas = new JFrame();
        frmCadastroDeTurmas.getContentPane().setBounds(new Rectangle(0, 0, 800, 600));
        frmCadastroDeTurmas.getContentPane().setBackground(SystemColor.activeCaption);
        frmCadastroDeTurmas.setBackground(SystemColor.inactiveCaption);
        frmCadastroDeTurmas.setForeground(SystemColor.inactiveCaption);
        frmCadastroDeTurmas.setFont(new Font("Arial", Font.PLAIN, 14));
        frmCadastroDeTurmas.setTitle("Cadastro de Turmas");
        frmCadastroDeTurmas.setBounds(100, 100, 800, 500);
        frmCadastroDeTurmas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmCadastroDeTurmas.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("ID Turma");
        model.addColumn("ID Curso");
        model.addColumn("Data Turma");
        model.addColumn("turno_Turma");
        model.addColumn("Nrº Matricula");

        txtFldIDTurma = new JTextField();
        txtFldIDTurma.setBounds(101, 69, 163, 35);
        txtFldIDTurma.setColumns(10);
        frmCadastroDeTurmas.getContentPane().add(txtFldIDTurma);

        txtFldIdCurso = new JTextField();
        txtFldIdCurso.setBounds(101, 113, 163, 35);
        txtFldIdCurso.setColumns(10);
        frmCadastroDeTurmas.getContentPane().add(txtFldIdCurso);

        textFldIdData = new JTextField();
        textFldIdData.setBounds(101, 158, 163, 35);
        textFldIdData.setColumns(10);
        frmCadastroDeTurmas.getContentPane().add(textFldIdData);

        txtFldIdturno_Turma = new JTextField();
        txtFldIdturno_Turma.setBounds(101, 203, 163, 35);
        txtFldIdturno_Turma.setColumns(10);
        frmCadastroDeTurmas.getContentPane().add(txtFldIdturno_Turma);

        JLabel lblNewLabel = new JLabel("ID Turma:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(35, 71, 64, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeTurmas.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("ID Curso:");
        lblNewLabel_1.setBounds(40, 115, 58, 31);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeTurmas.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Data Turma:");
        lblNewLabel_2.setBounds(18, 160, 80, 31);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeTurmas.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Turno:");
        lblNewLabel_3.setBounds(54, 205, 42, 31);
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeTurmas.getContentPane().add(lblNewLabel_3);

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    String sql = "INSERT INTO turmas(id_Turma, id_Curso, data_Turma, turno_Turma, nr_Matricula) VALUES (?, ?, ?, ?, ?)";
                    try {
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, txtFldIDTurma.getText());
                        mypst.setString(2, txtFldIdCurso.getText());
                        mypst.setString(3, textFldIdData.getText());
                        mypst.setString(4, txtFldIdturno_Turma.getText());
                        mypst.setString(5, txtFldMatricula.getText());
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "turmas cadastrado com sucesso!");
                        updateTable();
                        txtFldIDTurma.setText("");
                        txtFldIdCurso.setText("");
                        textFldIdData.setText("");
                        txtFldIdturno_Turma.setText("");
                        txtFldMatricula.setText("");
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
        frmCadastroDeTurmas.getContentPane().add(btnInserir);

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
                        String idTurma = table.getValueAt(row, 0).toString();
                        String sql = "UPDATE turmas SET id_Curso=?, data_Turma=?, turno_Turma=?, nr_Matricula=? WHERE id_Turma=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, txtFldIdCurso.getText());
                            mypst.setString(2, textFldIdData.getText());
                            mypst.setString(3, txtFldIdturno_Turma.getText());
                            mypst.setString(4, txtFldMatricula.getText());
                            mypst.setString(5, idTurma);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldIDTurma.setText("");
                            txtFldIdCurso.setText("");
                            textFldIdData.setText("");
                            txtFldIdturno_Turma.setText("");
                            txtFldMatricula.setText("");
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
        frmCadastroDeTurmas.getContentPane().add(btnAlterar);

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
                        String idTurma = table.getValueAt(row, 0).toString();
                        String sql = "DELETE FROM turmas WHERE id_Turma=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, idTurma);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Professor excluído com sucesso!");
                            updateTable();
                            txtFldIDTurma.setText("");
                            txtFldIdCurso.setText("");
                            textFldIdData.setText("");
                            txtFldIdturno_Turma.setText("");
                            txtFldMatricula.setText("");
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
                        JOptionPane.showMessageDialog(null, "Selecione um aluno na tabela para realizar a exclusão.");
                    }
                }
            }
        });
        frmCadastroDeTurmas.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		frmCadastroDeTurmas.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idTurma = JOptionPane.showInputDialog(null, "Informe a matrícula do aluno:");
				if (idTurma != null && !idTurma.isEmpty()) {
					String sql = "SELECT id_Turma, id_Curso, data_Turma, turno_Turma, nr_Matricula FROM turmas WHERE id_Turma = ?";
					conexao = Controle_EscolarConnection.ConnectDb();
					if (conexao != null) {
						try {
							mypst = conexao.prepareStatement(sql);
							mypst.setString(1, idTurma);
							myrs = mypst.executeQuery();
							if (myrs.next()) {
								JOptionPane.showMessageDialog(null, "Data_turmas: " + myrs.getString("id_Turma")
										+ "\nid_Curso: " + myrs.getString("id_Curso")
										+ "\ndata_Turma: " + myrs.getString("data_Turma")
										+ "\nID_Disponibilidade: " + myrs.getString("turno_Turma")
										+ "\nID_Turma: " + myrs.getString("nr_Matricula"));
							} else {
								JOptionPane.showMessageDialog(null, "Professor não encontrado.");
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
		frmCadastroDeTurmas.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldIDTurma.setText("");
		        txtFldIdCurso.setText("");
		        textFldIdData.setText("");
		        txtFldIdturno_Turma.setText("");
		        txtFldMatricula.setText("");
		    }
		});
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        frmCadastroDeTurmas.getContentPane().add(btnImprimir);
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
        frmCadastroDeTurmas.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	frmCadastroDeTurmas.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldIDTurma.setText(table.getValueAt(row, 0).toString());
                    txtFldIdCurso.setText(table.getValueAt(row, 1).toString());
                    textFldIdData.setText(table.getValueAt(row, 2).toString());
                    txtFldIdturno_Turma.setText(table.getValueAt(row, 3).toString());
                    txtFldMatricula.setText(table.getValueAt(row, 4).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Turmas");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        frmCadastroDeTurmas.getContentPane().add(lblNewLabel_11_1);
        
        txtFldMatricula = new JTextField();
        txtFldMatricula.setColumns(10);
        txtFldMatricula.setBounds(101, 248, 163, 35);
        frmCadastroDeTurmas.getContentPane().add(txtFldMatricula);
        
        JLabel lblTurma = new JLabel("Nrº Matricula:");
        lblTurma.setLabelFor(txtFldMatricula);
        lblTurma.setFont(new Font("Arial", Font.BOLD, 13));
        lblTurma.setBounds(10, 250, 89, 31);
        frmCadastroDeTurmas.getContentPane().add(lblTurma);
        updateTable();
    }
    	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM turmas";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String idTurma = myrs.getString("id_Turma");
                    String idProfessor = myrs.getString("id_Curso");
                    String idDisciplina = myrs.getString("data_Turma");
                    String turno_Turma = myrs.getString("turno_Turma");
                    String matricula = myrs.getString("nr_Matricula");
                    model.addRow(new Object[]{idTurma, idProfessor, idDisciplina, turno_Turma,matricula});
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