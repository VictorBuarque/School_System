package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Disciplinas {

    private JFrame CadadastroDeDisciplinas;
    private JTextField txtFldDIsciplina;
    private JTextField txtFldNomeDisciplinas;
    private JTextField textFldCurso;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;
    private JTextField textFieldDescricao;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadastro_Disciplinas window = new Cadastro_Disciplinas();
                    window.CadadastroDeDisciplinas.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadastro_Disciplinas() {
        initialize();
    }

    private void initialize() {
        CadadastroDeDisciplinas = new JFrame();
        CadadastroDeDisciplinas.setBounds(new Rectangle(0, 0, 800, 600));
        CadadastroDeDisciplinas.getContentPane().setBackground(SystemColor.activeCaption);
        CadadastroDeDisciplinas.setBackground(SystemColor.inactiveCaption);
        CadadastroDeDisciplinas.setForeground(SystemColor.inactiveCaption);
        CadadastroDeDisciplinas.setFont(new Font("Arial", Font.PLAIN, 14));
        CadadastroDeDisciplinas.setTitle("Cadastro de Disciplinas");
        CadadastroDeDisciplinas.setBounds(100, 100, 800, 500);
        CadadastroDeDisciplinas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CadadastroDeDisciplinas.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("ID Disciplina");
        model.addColumn("Disciplina");
        model.addColumn("Carga Horaria");
        model.addColumn("Descrição");
        

        txtFldDIsciplina = new JTextField();
        txtFldDIsciplina.setBounds(101, 69, 163, 35);
        txtFldDIsciplina.setColumns(10);
        CadadastroDeDisciplinas.getContentPane().add(txtFldDIsciplina);

        txtFldNomeDisciplinas = new JTextField();
        txtFldNomeDisciplinas.setBounds(101, 113, 163, 35);
        txtFldNomeDisciplinas.setColumns(10);
        CadadastroDeDisciplinas.getContentPane().add(txtFldNomeDisciplinas);

        textFldCurso = new JTextField();
        textFldCurso.setBounds(101, 158, 163, 35);
        textFldCurso.setColumns(10);
        CadadastroDeDisciplinas.getContentPane().add(textFldCurso);

        JLabel lblNewLabel = new JLabel("ID Disciplina:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(12, 71, 85, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDeDisciplinas.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Disciplina:");
        lblNewLabel_1.setBounds(29, 114, 65, 31);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDeDisciplinas.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Carga Hora:");
        lblNewLabel_2.setBounds(19, 160, 81, 31);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        CadadastroDeDisciplinas.getContentPane().add(lblNewLabel_2);

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                if (conexao != null) {
                    String sql = "INSERT INTO disciplina(id_Disciplina, nome_Disciplina, carga_Horaria, descricao_Disciplina) VALUES (?, ?, ?, ?)";
                    try {
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, txtFldDIsciplina.getText());
                        mypst.setString(2, txtFldNomeDisciplinas.getText());
                        mypst.setString(3, textFldCurso.getText());
                        mypst.setString(4,textFieldDescricao.getText());
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Disciplinas inserido com sucesso!");
                        updateTable();
                        txtFldDIsciplina.setText("");
                        txtFldNomeDisciplinas.setText("");
                        textFldCurso.setText("");
                        textFieldDescricao.setText("");
                        
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
        CadadastroDeDisciplinas.getContentPane().add(btnInserir);

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
                        String id_Disciplina = table.getValueAt(row, 0).toString();
                        String sql = "UPDATE disciplina SET nome_Disciplina=?, carga_Horaria=?, descricao_Disciplina=? WHERE id_Disciplina=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, txtFldNomeDisciplinas.getText());
                            mypst.setString(2, textFldCurso.getText());
                            mypst.setString(3, textFieldDescricao.getText());
                            mypst.setString(4, id_Disciplina);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldDIsciplina.setText("");
                            txtFldNomeDisciplinas.setText("");
                            textFldCurso.setText("");
                            textFieldDescricao.setText("");
                            
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
                        JOptionPane.showMessageDialog(null, "Selecione uma disciplina na tabela para realizar a alteração.");
                    }
                }
            }
        });
        CadadastroDeDisciplinas.getContentPane().add(btnAlterar);

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
                        String id_Disciplina = table.getValueAt(row, 0).toString();
                        String sql = "DELETE FROM disciplina WHERE id_Disciplina=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, id_Disciplina);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Disciplina excluída com sucesso!");
                            updateTable();
                            txtFldDIsciplina.setText("");
                            txtFldNomeDisciplinas.setText("");
                            textFldCurso.setText("");
                            textFieldDescricao.setText("");
                            
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
                        JOptionPane.showMessageDialog(null, "Selecione uma disciplina na tabela para realizar a exclusão.");
                    }
                }
            }
        });
        CadadastroDeDisciplinas.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		CadadastroDeDisciplinas.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id_Disciplina = JOptionPane.showInputDialog(null, "Informe o id da disciplina:");
				if (id_Disciplina != null && !id_Disciplina.isEmpty()) {
					String sql = "SELECT id_Disciplina, nome_Disciplina, carga_Horaria, descricao_Disciplina FROM disciplina WHERE id_Disciplina = ?";
					conexao = Controle_EscolarConnection.ConnectDb();
					if (conexao != null) {
						try {
							mypst = conexao.prepareStatement(sql);
							mypst.setString(1, id_Disciplina);
							myrs = mypst.executeQuery();
							if (myrs.next()) {
								JOptionPane.showMessageDialog(null, "Matrícula: " + myrs.getString("id_Disciplina")
										+ "\nNome: " + myrs.getString("nome_Disciplina")
										+ "\nCarga: " + myrs.getString("carga_Horaria")
										+ "\nDescrição: " + myrs.getString("descricao_Disciplina"));
							} else {
								JOptionPane.showMessageDialog(null, "Disciplina não encontrada.");
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
		CadadastroDeDisciplinas.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldDIsciplina.setText("");
		        txtFldNomeDisciplinas.setText("");
		        textFldCurso.setText("");
		        textFieldDescricao.setText("");
		        
		    }
		});
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        CadadastroDeDisciplinas.getContentPane().add(btnImprimir);
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
        CadadastroDeDisciplinas.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	CadadastroDeDisciplinas.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldDIsciplina.setText(table.getValueAt(row, 0).toString());
                    txtFldNomeDisciplinas.setText(table.getValueAt(row, 1).toString());
                    textFldCurso.setText(table.getValueAt(row, 2).toString());
                    textFieldDescricao.setText(table.getValueAt(row, 3).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Disciplinas");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        CadadastroDeDisciplinas.getContentPane().add(lblNewLabel_11_1);
        
        JLabel lblNewLabel_2_1 = new JLabel("Descrição:");
        lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 13));
        lblNewLabel_2_1.setBounds(29, 205, 71, 31);
        CadadastroDeDisciplinas.getContentPane().add(lblNewLabel_2_1);
        
        textFieldDescricao = new JTextField();
        textFieldDescricao.setColumns(10);
        textFieldDescricao.setBounds(101, 203, 163, 35);
        CadadastroDeDisciplinas.getContentPane().add(textFieldDescricao);
        updateTable();
    }
    	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM disciplina";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String id_Disciplina = myrs.getString("id_Disciplina");
                    String nome = myrs.getString("nome_Disciplina");
                    String carga = myrs.getString("carga_Horaria");
                    String descricao = myrs.getString("descricao_Disciplina");
                    
                    model.addRow(new Object[]{id_Disciplina, nome, carga,descricao});
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
