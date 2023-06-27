package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadadastro_Pagamentos {

    private JFrame CadadastroDePagamentos;
    private JTextField txtFldMatricula;
    private JTextField txtFldNomePagamentos;
    private JTextField textFldCpf;
    private JTextField txtFldEndereco;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadadastro_Pagamentos window = new Cadadastro_Pagamentos();
                    window.CadadastroDePagamentos.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadadastro_Pagamentos() {
        initialize();
    }

    private void initialize() {
        CadadastroDePagamentos = new JFrame();
        CadadastroDePagamentos.getContentPane().setBackground(SystemColor.activeCaption);
        CadadastroDePagamentos.setBackground(SystemColor.inactiveCaption);
        CadadastroDePagamentos.setForeground(SystemColor.inactiveCaption);
        CadadastroDePagamentos.setFont(new Font("Arial", Font.PLAIN, 14));
        CadadastroDePagamentos.setTitle("Cadastro de Pagamentos");
        CadadastroDePagamentos.setBounds(100, 100, 800, 500);
        CadadastroDePagamentos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CadadastroDePagamentos.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("Nr. Matrícula");
        model.addColumn("ID Curso");
        model.addColumn("Data Vencimento");
        model.addColumn("Valor R$");

        txtFldMatricula = new JTextField();
        txtFldMatricula.setBounds(101, 69, 163, 35);
        txtFldMatricula.setColumns(10);
        CadadastroDePagamentos.getContentPane().add(txtFldMatricula);

        txtFldNomePagamentos = new JTextField();
        txtFldNomePagamentos.setBounds(101, 113, 163, 35);
        txtFldNomePagamentos.setColumns(10);
        CadadastroDePagamentos.getContentPane().add(txtFldNomePagamentos);

        textFldCpf = new JTextField();
        textFldCpf.setBounds(101, 158, 163, 35);
        textFldCpf.setColumns(10);
        CadadastroDePagamentos.getContentPane().add(textFldCpf);

        txtFldEndereco = new JTextField();
        txtFldEndereco.setBounds(101, 203, 163, 35);
        txtFldEndereco.setColumns(10);
        CadadastroDePagamentos.getContentPane().add(txtFldEndereco);

        JLabel lblNewLabel = new JLabel("Nrº Matrícula:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(10, 71, 89, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDePagamentos.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("ID Curso:");
        lblNewLabel_1.setBounds(49, 123, 42, 14);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDePagamentos.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Data Vencimento:");
        lblNewLabel_2.setBounds(60, 168, 31, 14);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDePagamentos.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Valor R$:");
        lblNewLabel_3.setBounds(29, 213, 70, 14);
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDePagamentos.getContentPane().add(lblNewLabel_3);

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    String sql = "INSERT INTO pagamentos(nr_Matricula, id_Curso, data_Pagamento, valor) VALUES (?, ?, ?, ?)";
                    try {
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, txtFldMatricula.getText());
                        mypst.setString(2, txtFldNomePagamentos.getText());
                        mypst.setString(3, textFldCpf.getText());
                        mypst.setString(4, txtFldEndereco.getText());
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Pagamentos inserido com sucesso!");
                        updateTable();
                        txtFldMatricula.setText("");
                        txtFldNomePagamentos.setText("");
                        textFldCpf.setText("");
                        txtFldEndereco.setText("");
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
        CadadastroDePagamentos.getContentPane().add(btnInserir);

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
                        String sql = "UPDATE pagamentos SET id_Curso=?, data_Pagamento=?, valor=? WHERE nr_Matricula=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, txtFldNomePagamentos.getText());
                            mypst.setString(2, textFldCpf.getText());
                            mypst.setString(3, txtFldEndereco.getText());
                            mypst.setString(4, matricula);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldMatricula.setText("");
                            txtFldNomePagamentos.setText("");
                            textFldCpf.setText("");
                            txtFldEndereco.setText("");
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
        CadadastroDePagamentos.getContentPane().add(btnAlterar);

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
                        String sql = "DELETE FROM pagamentos WHERE nr_Matricula=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, matricula);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Pagamentos excluído com sucesso!");
                            updateTable();
                            txtFldMatricula.setText("");
                            txtFldNomePagamentos.setText("");
                            textFldCpf.setText("");
                            txtFldEndereco.setText("");
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
        CadadastroDePagamentos.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		CadadastroDePagamentos.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matricula = JOptionPane.showInputDialog(null, "Informe a matrícula do aluno:");
				if (matricula != null && !matricula.isEmpty()) {
					String sql = "SELECT nr_Matricula, id_Curso, data_Pagamento, valor FROM pagamentos WHERE nr_Matricula = ?";
					conexao = Controle_EscolarConnection.ConnectDb();
					if (conexao != null) {
						try {
							mypst = conexao.prepareStatement(sql);
							mypst.setString(1, matricula);
							myrs = mypst.executeQuery();
							if (myrs.next()) {
								JOptionPane.showMessageDialog(null, "Matrícula: " + myrs.getString("nr_Matricula")
										+ "\nNome: " + myrs.getString("id_Curso")
										+ "\nCPF: " + myrs.getString("data_Pagamento")
										+ "\nEndereço: " + myrs.getString("valor"));
							} else {
								JOptionPane.showMessageDialog(null, "Pagamentos não encontrado.");
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
		CadadastroDePagamentos.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldMatricula.setText("");
		        txtFldNomePagamentos.setText("");
		        textFldCpf.setText("");
		        txtFldEndereco.setText("");
		    }
		});
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        CadadastroDePagamentos.getContentPane().add(btnImprimir);
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
        CadadastroDePagamentos.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	CadadastroDePagamentos.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldMatricula.setText(table.getValueAt(row, 0).toString());
                    txtFldNomePagamentos.setText(table.getValueAt(row, 1).toString());
                    textFldCpf.setText(table.getValueAt(row, 2).toString());
                    txtFldEndereco.setText(table.getValueAt(row, 3).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Pagamentos");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        CadadastroDePagamentos.getContentPane().add(lblNewLabel_11_1);
        updateTable();
    }
    	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM pagamentos";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String matricula = myrs.getString("nr_Matricula");
                    String nome = myrs.getString("id_Curso");
                    String cpf = myrs.getString("data_Pagamento");
                    String endereco = myrs.getString("valor");
                    model.addRow(new Object[]{matricula, nome, cpf, endereco});
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
