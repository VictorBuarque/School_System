package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Cronogramas {

    private JFrame frmCadastroDeCronogramas;
    private JTextField txtFldData;
    private JTextField txtFldIdProfessor;
    private JTextField textFldIdDisciplina;
    private JTextField txtFldIdDIsponibilidade;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;
    private JTextField txtFldTurma;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadastro_Cronogramas window = new Cadastro_Cronogramas();
                    window.frmCadastroDeCronogramas.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadastro_Cronogramas() {
        initialize();
    }

    private void initialize() {
        frmCadastroDeCronogramas = new JFrame();
        frmCadastroDeCronogramas.getContentPane().setBounds(new Rectangle(0, 0, 800, 600));
        frmCadastroDeCronogramas.getContentPane().setBackground(SystemColor.activeCaption);
        frmCadastroDeCronogramas.setBackground(SystemColor.inactiveCaption);
        frmCadastroDeCronogramas.setForeground(SystemColor.inactiveCaption);
        frmCadastroDeCronogramas.setFont(new Font("Arial", Font.PLAIN, 14));
        frmCadastroDeCronogramas.setTitle("Cadastro de Cronogramas");
        frmCadastroDeCronogramas.setBounds(100, 100, 800, 500);
        frmCadastroDeCronogramas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmCadastroDeCronogramas.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("Data Crono.");
        model.addColumn("ID Prof.");
        model.addColumn("ID Disc.");
        model.addColumn("Hora Ini.");
        model.addColumn("Hora Fim");

        txtFldData = new JTextField();
        txtFldData.setBounds(101, 69, 163, 35);
        txtFldData.setColumns(10);
        frmCadastroDeCronogramas.getContentPane().add(txtFldData);

        txtFldIdProfessor = new JTextField();
        txtFldIdProfessor.setBounds(101, 113, 163, 35);
        txtFldIdProfessor.setColumns(10);
        frmCadastroDeCronogramas.getContentPane().add(txtFldIdProfessor);

        textFldIdDisciplina = new JTextField();
        textFldIdDisciplina.setBounds(101, 158, 163, 35);
        textFldIdDisciplina.setColumns(10);
        frmCadastroDeCronogramas.getContentPane().add(textFldIdDisciplina);

        txtFldIdDIsponibilidade = new JTextField();
        txtFldIdDIsponibilidade.setBounds(101, 203, 163, 35);
        txtFldIdDIsponibilidade.setColumns(10);
        frmCadastroDeCronogramas.getContentPane().add(txtFldIdDIsponibilidade);

        JLabel lblNewLabel = new JLabel("Data/Hora:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(30, 71, 66, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("ID Professor:");
        lblNewLabel_1.setBounds(16, 115, 87, 31);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("ID Disciplina:");
        lblNewLabel_2.setBounds(13, 160, 87, 31);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Hora Inicio:");
        lblNewLabel_3.setBounds(23, 205, 75, 31);
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_3);

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    String sql = "INSERT INTO cronograma(dt_Cronograma, id_Professor, id_Disciplina, hr_Inicio, hr_Fim) VALUES (?, ?, ?, ?, ?)";
                    try {
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, txtFldData.getText());
                        mypst.setString(2, txtFldIdProfessor.getText());
                        mypst.setString(3, textFldIdDisciplina.getText());
                        mypst.setString(4, txtFldIdDIsponibilidade.getText());
                        mypst.setString(5, txtFldTurma.getText());
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Cronograma cadastrado com sucesso!");
                        updateTable();
                        txtFldData.setText("");
                        txtFldIdProfessor.setText("");
                        textFldIdDisciplina.setText("");
                        txtFldIdDIsponibilidade.setText("");
                        txtFldTurma.setText("");
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
        frmCadastroDeCronogramas.getContentPane().add(btnInserir);

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
                        String dataHora = table.getValueAt(row, 0).toString();
                        String sql = "UPDATE cronograma SET id_Professor=?, id_Disciplina=?, hr_Inicio=?, hr_Fim=? WHERE dt_Cronograma=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, txtFldIdProfessor.getText());
                            mypst.setString(2, textFldIdDisciplina.getText());
                            mypst.setString(3, txtFldIdDIsponibilidade.getText());
                            mypst.setString(4, txtFldTurma.getText());
                            mypst.setString(5, dataHora);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldData.setText("");
                            txtFldIdProfessor.setText("");
                            textFldIdDisciplina.setText("");
                            txtFldIdDIsponibilidade.setText("");
                            txtFldTurma.setText("");
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
        frmCadastroDeCronogramas.getContentPane().add(btnAlterar);

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
                        String dataHora = table.getValueAt(row, 0).toString();
                        String sql = "DELETE FROM cronograma WHERE dt_Cronograma=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, dataHora);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Professor excluído com sucesso!");
                            updateTable();
                            txtFldData.setText("");
                            txtFldIdProfessor.setText("");
                            textFldIdDisciplina.setText("");
                            txtFldIdDIsponibilidade.setText("");
                            txtFldTurma.setText("");
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
        frmCadastroDeCronogramas.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		frmCadastroDeCronogramas.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dataHora = JOptionPane.showInputDialog(null, "Informe a matrícula do aluno:");
				if (dataHora != null && !dataHora.isEmpty()) {
					String sql = "SELECT dt_Cronograma, id_Professor, id_Disciplina, hr_Inicio, hr_Fim FROM cronograma WHERE dt_Cronograma = ?";
					conexao = Controle_EscolarConnection.ConnectDb();
					if (conexao != null) {
						try {
							mypst = conexao.prepareStatement(sql);
							mypst.setString(1, dataHora);
							myrs = mypst.executeQuery();
							if (myrs.next()) {
								JOptionPane.showMessageDialog(null, "Data_Cronograma: " + myrs.getString("dt_Cronograma")
										+ "\nID_Professor: " + myrs.getString("id_Professor")
										+ "\nID_Disciplina: " + myrs.getString("id_Disciplina")
										+ "\nID_Disponibilidade: " + myrs.getString("hr_Inicio")
										+ "\nID_Turma: " + myrs.getString("hr_Fim"));
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
		frmCadastroDeCronogramas.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldData.setText("");
		        txtFldIdProfessor.setText("");
		        textFldIdDisciplina.setText("");
		        txtFldIdDIsponibilidade.setText("");
		        txtFldTurma.setText("");
		    }
		});
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        frmCadastroDeCronogramas.getContentPane().add(btnImprimir);
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
        frmCadastroDeCronogramas.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldData.setText(table.getValueAt(row, 0).toString());
                    txtFldIdProfessor.setText(table.getValueAt(row, 1).toString());
                    textFldIdDisciplina.setText(table.getValueAt(row, 2).toString());
                    txtFldIdDIsponibilidade.setText(table.getValueAt(row, 3).toString());
                    txtFldTurma.setText(table.getValueAt(row, 4).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Cronogramas");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_11_1);
        
        txtFldTurma = new JTextField();
        txtFldTurma.setColumns(10);
        txtFldTurma.setBounds(101, 248, 163, 35);
        frmCadastroDeCronogramas.getContentPane().add(txtFldTurma);
        
        JLabel lblTurma = new JLabel("Hora Fim:");
        lblTurma.setLabelFor(txtFldTurma);
        lblTurma.setFont(new Font("Arial", Font.BOLD, 13));
        lblTurma.setBounds(34, 250, 64, 31);
        frmCadastroDeCronogramas.getContentPane().add(lblTurma);
        updateTable();
    }
    	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM cronograma";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String dataHora = myrs.getString("dt_Cronograma");
                    String idProfessor = myrs.getString("id_Professor");
                    String idDisciplina = myrs.getString("id_Disciplina");
                    String horaInicio = myrs.getString("hr_Inicio");
                    String horaFim = myrs.getString("hr_Fim");
                    model.addRow(new Object[]{dataHora, idProfessor, idDisciplina, horaInicio,horaFim});
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