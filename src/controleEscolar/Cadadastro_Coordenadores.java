package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadadastro_Coordenadores {

    private JFrame CadadastroDeCoordenadores;
    private JTextField txtFldMatricula;
    private JTextField txtFldNomeCoordenadores;
    private JTextField textFldemail_Coordenador;
    private JTextField txtFldcel_Coordenador;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadadastro_Coordenadores window = new Cadadastro_Coordenadores();
                    window.CadadastroDeCoordenadores.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadadastro_Coordenadores() {
        initialize();
    }

    private void initialize() {
        CadadastroDeCoordenadores = new JFrame();
        CadadastroDeCoordenadores.getContentPane().setBackground(SystemColor.activeCaption);
        CadadastroDeCoordenadores.setBackground(SystemColor.inactiveCaption);
        CadadastroDeCoordenadores.setForeground(SystemColor.inactiveCaption);
        CadadastroDeCoordenadores.setFont(new Font("Arial", Font.PLAIN, 14));
        CadadastroDeCoordenadores.setTitle("Cadastro de Coordenadores");
        CadadastroDeCoordenadores.setBounds(100, 100, 800, 500);
        CadadastroDeCoordenadores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CadadastroDeCoordenadores.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("id_Coordenador");
        model.addColumn("Nome");
        model.addColumn("email_Coordenador");
        model.addColumn("Celular");

        txtFldMatricula = new JTextField();
        txtFldMatricula.setBounds(101, 69, 163, 35);
        txtFldMatricula.setColumns(10);
        CadadastroDeCoordenadores.getContentPane().add(txtFldMatricula);

        txtFldNomeCoordenadores = new JTextField();
        txtFldNomeCoordenadores.setBounds(101, 113, 163, 35);
        txtFldNomeCoordenadores.setColumns(10);
        CadadastroDeCoordenadores.getContentPane().add(txtFldNomeCoordenadores);

        textFldemail_Coordenador = new JTextField();
        textFldemail_Coordenador.setBounds(101, 158, 163, 35);
        textFldemail_Coordenador.setColumns(10);
        CadadastroDeCoordenadores.getContentPane().add(textFldemail_Coordenador);

        txtFldcel_Coordenador = new JTextField();
        txtFldcel_Coordenador.setBounds(101, 203, 163, 35);
        txtFldcel_Coordenador.setColumns(10);
        CadadastroDeCoordenadores.getContentPane().add(txtFldcel_Coordenador);

        JLabel lblNewLabel = new JLabel("ID Coord.:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(29, 71, 62, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDeCoordenadores.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Nome:");
        lblNewLabel_1.setBounds(49, 115, 42, 31);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDeCoordenadores.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("E-Mail:");
        lblNewLabel_2.setBounds(49, 160, 42, 31);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDeCoordenadores.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Celular:");
        lblNewLabel_3.setBounds(42, 207, 50, 31);
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDeCoordenadores.getContentPane().add(lblNewLabel_3);

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    String sql = "INSERT INTO coordenacao(id_Coordenador, nome_Coordenador, email_Coordenador, cel_Coordenador) VALUES (?, ?, ?, ?)";
                    try {
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, txtFldMatricula.getText());
                        mypst.setString(2, txtFldNomeCoordenadores.getText());
                        mypst.setString(3, textFldemail_Coordenador.getText());
                        mypst.setString(4, txtFldcel_Coordenador.getText());
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Coordenadores inserido com sucesso!");
                        updateTable();
                        txtFldMatricula.setText("");
                        txtFldNomeCoordenadores.setText("");
                        textFldemail_Coordenador.setText("");
                        txtFldcel_Coordenador.setText("");
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
        CadadastroDeCoordenadores.getContentPane().add(btnInserir);

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
                        String matricula = table.getValueAt(row, 0).toString();
                        String sql = "UPDATE coordenacao SET nome_Coordenador=?, email_Coordenador=?, cel_Coordenador=? WHERE id_Coordenador=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, txtFldNomeCoordenadores.getText());
                            mypst.setString(2, textFldemail_Coordenador.getText());
                            mypst.setString(3, txtFldcel_Coordenador.getText());
                            mypst.setString(4, matricula);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldMatricula.setText("");
                            txtFldNomeCoordenadores.setText("");
                            textFldemail_Coordenador.setText("");
                            txtFldcel_Coordenador.setText("");
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
        CadadastroDeCoordenadores.getContentPane().add(btnAlterar);

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
                        String matricula = table.getValueAt(row, 0).toString();
                        String sql = "DELETE FROM coordenacao WHERE id_Coordenador=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, matricula);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Coordenadores excluído com sucesso!");
                            updateTable();
                            txtFldMatricula.setText("");
                            txtFldNomeCoordenadores.setText("");
                            textFldemail_Coordenador.setText("");
                            txtFldcel_Coordenador.setText("");
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
        CadadastroDeCoordenadores.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		CadadastroDeCoordenadores.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matricula = JOptionPane.showInputDialog(null, "Informe a matrícula do aluno:");
				if (matricula != null && !matricula.isEmpty()) {
					String sql = "SELECT id_Coordenador, nome_Coordenador, email_Coordenador, cel_Coordenador FROM coordenacao WHERE id_Coordenador = ?";
					conexao = Controle_EscolarConnection.ConnectDb();
					if (conexao != null) {
						try {
							mypst = conexao.prepareStatement(sql);
							mypst.setString(1, matricula);
							myrs = mypst.executeQuery();
							if (myrs.next()) {
								JOptionPane.showMessageDialog(null, "Matrícula: " + myrs.getString("id_Coordenador")
										+ "\nNome: " + myrs.getString("nome_Coordenador")
										+ "\nEmail_Coordenador: " + myrs.getString("email_Coordenador")
										+ "\nCelular: " + myrs.getString("cel_Coordenador"));
							} else {
								JOptionPane.showMessageDialog(null, "Coordenadores não encontrado.");
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
		CadadastroDeCoordenadores.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldMatricula.setText("");
		        txtFldNomeCoordenadores.setText("");
		        textFldemail_Coordenador.setText("");
		        txtFldcel_Coordenador.setText("");
		    }
		});
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        CadadastroDeCoordenadores.getContentPane().add(btnImprimir);
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
        CadadastroDeCoordenadores.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	CadadastroDeCoordenadores.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldMatricula.setText(table.getValueAt(row, 0).toString());
                    txtFldNomeCoordenadores.setText(table.getValueAt(row, 1).toString());
                    textFldemail_Coordenador.setText(table.getValueAt(row, 2).toString());
                    txtFldcel_Coordenador.setText(table.getValueAt(row, 3).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Coordenadores");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        CadadastroDeCoordenadores.getContentPane().add(lblNewLabel_11_1);
        updateTable();
    }
    	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM coordenacao";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String matricula = myrs.getString("id_Coordenador");
                    String nome = myrs.getString("nome_Coordenador");
                    String email_Coordenador = myrs.getString("email_Coordenador");
                    String cel_Coordenador = myrs.getString("cel_Coordenador");
                    model.addRow(new Object[]{matricula, nome, email_Coordenador, cel_Coordenador});
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
