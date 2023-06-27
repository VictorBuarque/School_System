package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Professores {

    private JFrame frmCadastroDeProfessores;
    private JTextField txtFldMatricula;
    private JTextField txtFldNomeProfessor;
    private JTextField textFldCpf;
    private JTextField txtFldEndereco;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;
    private JTextField txtFldTelefone;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadastro_Professores window = new Cadastro_Professores();
                    window.frmCadastroDeProfessores.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadastro_Professores() {
        initialize();
    }

    private void initialize() {
        frmCadastroDeProfessores = new JFrame();
        frmCadastroDeProfessores.getContentPane().setBackground(SystemColor.activeCaption);
        frmCadastroDeProfessores.setBackground(SystemColor.inactiveCaption);
        frmCadastroDeProfessores.setForeground(SystemColor.inactiveCaption);
        frmCadastroDeProfessores.setFont(new Font("Arial", Font.PLAIN, 14));
        frmCadastroDeProfessores.setTitle("Cadastro de Professores");
        frmCadastroDeProfessores.setBounds(100, 100, 800, 500);
        frmCadastroDeProfessores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmCadastroDeProfessores.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("Nr. Matrícula");
        model.addColumn("Nome");
        model.addColumn("CPF");
        model.addColumn("Endereço");
        model.addColumn("Telefone");

        txtFldMatricula = new JTextField();
        txtFldMatricula.setBounds(101, 69, 163, 35);
        txtFldMatricula.setColumns(10);
        frmCadastroDeProfessores.getContentPane().add(txtFldMatricula);

        txtFldNomeProfessor = new JTextField();
        txtFldNomeProfessor.setBounds(101, 113, 163, 35);
        txtFldNomeProfessor.setColumns(10);
        frmCadastroDeProfessores.getContentPane().add(txtFldNomeProfessor);

        textFldCpf = new JTextField();
        textFldCpf.setBounds(101, 158, 163, 35);
        textFldCpf.setColumns(10);
        frmCadastroDeProfessores.getContentPane().add(textFldCpf);

        txtFldEndereco = new JTextField();
        txtFldEndereco.setBounds(101, 203, 163, 35);
        txtFldEndereco.setColumns(10);
        frmCadastroDeProfessores.getContentPane().add(txtFldEndereco);

        JLabel lblNewLabel = new JLabel("Nrº Matrícula:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(9, 71, 89, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeProfessores.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Nome:");
        lblNewLabel_1.setBounds(54, 123, 42, 14);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeProfessores.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("CPF:");
        lblNewLabel_2.setBounds(65, 168, 31, 14);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeProfessores.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Endereço:");
        lblNewLabel_3.setBounds(32, 213, 70, 14);
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeProfessores.getContentPane().add(lblNewLabel_3);

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    String sql = "INSERT INTO professor(id_Professor, nome_Professor, cpf_Professor, email_Professor, telefone_Professor) VALUES (?, ?, ?, ?, ?)";
                    try {
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, txtFldMatricula.getText());
                        mypst.setString(2, txtFldNomeProfessor.getText());
                        mypst.setString(3, textFldCpf.getText());
                        mypst.setString(4, txtFldEndereco.getText());
                        mypst.setString(5, txtFldTelefone.getText());
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Professor inserido com sucesso!");
                        updateTable();
                        txtFldMatricula.setText("");
                        txtFldNomeProfessor.setText("");
                        textFldCpf.setText("");
                        txtFldEndereco.setText("");
                        txtFldTelefone.setText("");
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
        frmCadastroDeProfessores.getContentPane().add(btnInserir);

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
                        String sql = "UPDATE professor SET nome_Professor=?, cpf_Professor=?, email_Professor=?, telefone_Professor=? WHERE id_Professor=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, txtFldNomeProfessor.getText());
                            mypst.setString(2, textFldCpf.getText());
                            mypst.setString(3, txtFldEndereco.getText());
                            mypst.setString(4, txtFldTelefone.getText());
                            mypst.setString(5, matricula);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldMatricula.setText("");
                            txtFldNomeProfessor.setText("");
                            textFldCpf.setText("");
                            txtFldEndereco.setText("");
                            txtFldTelefone.setText("");
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
        frmCadastroDeProfessores.getContentPane().add(btnAlterar);

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
                        String sql = "DELETE FROM professor WHERE id_Professor=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, matricula);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Professor excluído com sucesso!");
                            updateTable();
                            txtFldMatricula.setText("");
                            txtFldNomeProfessor.setText("");
                            textFldCpf.setText("");
                            txtFldEndereco.setText("");
                            txtFldTelefone.setText("");
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
        frmCadastroDeProfessores.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		frmCadastroDeProfessores.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matricula = JOptionPane.showInputDialog(null, "Informe a matrícula do aluno:");
				if (matricula != null && !matricula.isEmpty()) {
					String sql = "SELECT id_Professor, nome_Professor, cpf_Professor, email_Professor, telefone_Professor FROM professor WHERE id_Professor = ?";
					conexao = Controle_EscolarConnection.ConnectDb();
					if (conexao != null) {
						try {
							mypst = conexao.prepareStatement(sql);
							mypst.setString(1, matricula);
							myrs = mypst.executeQuery();
							if (myrs.next()) {
								JOptionPane.showMessageDialog(null, "Matrícula: " + myrs.getString("id_Professor")
										+ "\nNome: " + myrs.getString("nome_Professor")
										+ "\nCPF: " + myrs.getString("cpf_Professor")
										+ "\nEndereço: " + myrs.getString("email_Professor")
										+ "\nTelefone: " + myrs.getString("telefone_Professor"));
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
		frmCadastroDeProfessores.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldMatricula.setText("");
		        txtFldNomeProfessor.setText("");
		        textFldCpf.setText("");
		        txtFldEndereco.setText("");
		        txtFldTelefone.setText("");
		    }
		});
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        frmCadastroDeProfessores.getContentPane().add(btnImprimir);
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
        frmCadastroDeProfessores.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	frmCadastroDeProfessores.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldMatricula.setText(table.getValueAt(row, 0).toString());
                    txtFldNomeProfessor.setText(table.getValueAt(row, 1).toString());
                    textFldCpf.setText(table.getValueAt(row, 2).toString());
                    txtFldEndereco.setText(table.getValueAt(row, 3).toString());
                    txtFldTelefone.setText(table.getValueAt(row, 4).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Professores");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        frmCadastroDeProfessores.getContentPane().add(lblNewLabel_11_1);
        
        txtFldTelefone = new JTextField();
        txtFldTelefone.setColumns(10);
        txtFldTelefone.setBounds(101, 248, 163, 35);
        frmCadastroDeProfessores.getContentPane().add(txtFldTelefone);
        
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setLabelFor(txtFldTelefone);
        lblTelefone.setFont(new Font("Arial", Font.BOLD, 13));
        lblTelefone.setBounds(36, 258, 64, 14);
        frmCadastroDeProfessores.getContentPane().add(lblTelefone);
        updateTable();
    }
    	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM professor";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String matricula = myrs.getString("id_Professor");
                    String nome = myrs.getString("nome_Professor");
                    String cpf = myrs.getString("cpf_Professor");
                    String endereco = myrs.getString("email_Professor");
                    String telefone = myrs.getString("telefone_Professor");
                    model.addRow(new Object[]{matricula, nome, cpf, endereco, telefone});
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

