package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Cursos {

    private JFrame CadastroDeCursos;
    private JTextField txtFldid_Curso;
    private JTextField txtFldNomeCursos;
    private JTextField textFldid_Coordenador;
    private JTable table;
    private JComboBox<String> comboBox;
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cadastro_Cursos window = new Cadastro_Cursos();
                    window.CadastroDeCursos.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cadastro_Cursos() {
        initialize();
    }

    private void initialize() {
        CadastroDeCursos = new JFrame();
        CadastroDeCursos.getContentPane().setBackground(SystemColor.activeCaption);
        CadastroDeCursos.setBackground(SystemColor.inactiveCaption);
        CadastroDeCursos.setForeground(SystemColor.inactiveCaption);
        CadastroDeCursos.setFont(new Font("Arial", Font.PLAIN, 14));
        CadastroDeCursos.setTitle("Cadastro de Cursos");
        CadastroDeCursos.setBounds(100, 100, 800, 500);
        CadastroDeCursos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CadastroDeCursos.getContentPane().setLayout(null);

        model = new DefaultTableModel();
        model.addColumn("ID_Curso");
        model.addColumn("Nome_Curso");
        model.addColumn("ID_Coordenador");
        
        txtFldid_Curso = new JTextField();
        txtFldid_Curso.setFont(new Font("Arial", Font.PLAIN, 12));
        txtFldid_Curso.setBounds(101, 68, 163, 35);
        txtFldid_Curso.setColumns(10);
        CadastroDeCursos.getContentPane().add(txtFldid_Curso);

        txtFldNomeCursos = new JTextField();
        txtFldNomeCursos.setFont(new Font("Arial", Font.PLAIN, 12));
        txtFldNomeCursos.setBounds(101, 113, 163, 35);
        txtFldNomeCursos.setColumns(10);
        CadastroDeCursos.getContentPane().add(txtFldNomeCursos);

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBounds(101, 159, 163, 31);
        CadastroDeCursos.getContentPane().add(comboBox);
        try {
            conexao = Controle_EscolarConnection.ConnectDb();
            if (conexao != null) {
                String sql = "SELECT * FROM coordenacao";
                try (Statement stmt = conexao.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        String idCoordenador = rs.getString("id_Coordenador");
                        comboBox.addItem(idCoordenador);
                    }
                }
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(CadastroDeCursos, erro);
        }

        CadastroDeCursos.setVisible(true);

        JLabel lblNewLabel = new JLabel("ID Curso:");
        lblNewLabel.setForeground(SystemColor.infoText);
        lblNewLabel.setBounds(41, 70, 58, 31);
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 13));
        CadastroDeCursos.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Curso:");
        lblNewLabel_1.setBounds(57, 118, 42, 31);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 13));
        CadastroDeCursos.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("ID Coord.:");
        lblNewLabel_2.setBounds(32, 160, 66, 31);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        CadastroDeCursos.getContentPane().add(lblNewLabel_2);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(SystemColor.text);
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String idCurso = txtFldid_Curso.getText();
                String nomeCurso = txtFldNomeCursos.getText();
                String idCoordenador = comboBox.getSelectedItem().toString();
                try {
                    conexao = Controle_EscolarConnection.ConnectDb();
                    String sql = "INSERT INTO curso (id_curso, nome_curso, id_coordenador) VALUES (?,?,?)";
                    mypst = conexao.prepareStatement(sql);
                    mypst.setString(1, idCurso);
                    mypst.setString(2, nomeCurso);
                    mypst.setString(3, idCoordenador);
                    mypst.executeUpdate();
                    JOptionPane.showMessageDialog(CadastroDeCursos, "Dados inseridos com sucesso!");
                    mypst.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(CadastroDeCursos, e);
                }
                updateTable();
            }
        });
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalvar.setBounds(686, 69, 89, 31);
        CadastroDeCursos.getContentPane().add(btnSalvar);

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.setBackground(SystemColor.text);
        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(CadastroDeCursos, "Selecione um curso para alterar.");
                } else {
                    String idCurso = txtFldid_Curso.getText();
                    String nomeCurso = txtFldNomeCursos.getText();
                    String idCoordenador = comboBox.getSelectedItem().toString();
                    try {
                        conexao = Controle_EscolarConnection.ConnectDb();
                        String sql = "UPDATE curso SET nome_curso=?, id_coordenador=? WHERE id_curso=?";
                        mypst = conexao.prepareStatement(sql);
                        mypst.setString(1, nomeCurso);
                        mypst.setString(2, idCoordenador);
                        mypst.setString(3, idCurso);
                        mypst.executeUpdate();
                        JOptionPane.showMessageDialog(CadastroDeCursos, "Dados alterados com sucesso!");
                        mypst.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(CadastroDeCursos, ex);
                    }
                    updateTable();
                }
            }
        });
        btnAlterar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAlterar.setBounds(686, 114, 90, 31);
        CadastroDeCursos.getContentPane().add(btnAlterar);

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
                        String id_Curso = table.getValueAt(row, 0).toString();
                        String sql = "DELETE FROM curso WHERE id_Curso=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, id_Curso);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Cursos excluído com sucesso!");
                            updateTable();
                            txtFldid_Curso.setText("");
                            txtFldNomeCursos.setText("");
                            textFldid_Coordenador.setText("");
                            
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
                        JOptionPane.showMessageDialog(null, "Selecione um curso na tabela para realizar a exclusão.");
                    }
                }
            }
        });
        CadastroDeCursos.getContentPane().add(btnExcluir);
        
        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBackground(new Color(255, 255, 255));
		btnConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btnConsulta.setBounds(686, 203, 89, 35);
		CadastroDeCursos.getContentPane().add(btnConsulta);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id_Curso = JOptionPane.showInputDialog(null, "Informe a matrícula do aluno:");
				if (id_Curso != null && !id_Curso.isEmpty()) {
					String sql = "SELECT id_Curso, nome_Curso, id_Coordenador FROM curso WHERE id_Curso = ?";
					conexao = Controle_EscolarConnection.ConnectDb();
					if (conexao != null) {
						try {
							mypst = conexao.prepareStatement(sql);
							mypst.setString(1, id_Curso);
							myrs = mypst.executeQuery();
							if (myrs.next()) {
								JOptionPane.showMessageDialog(null, "Matrícula: " + myrs.getString("id_Curso")
										+ "\nNome: " + myrs.getString("nome_Curso")
										+ "\nid_Coordenador: " + myrs.getString("id_Coordenador")
										);
							} else {
								JOptionPane.showMessageDialog(null, "Curso não encontrado.");
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
		CadastroDeCursos.getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        txtFldid_Curso.setText("");
		        txtFldNomeCursos.setText("");
		        textFldid_Coordenador.setText("");
		        
		    }
		});
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setBackground(new Color(255, 255, 255));
        btnImprimir.setFont(new Font("Arial", Font.BOLD, 12));
        btnImprimir.setBounds(686, 292, 90, 35);
        CadastroDeCursos.getContentPane().add(btnImprimir);
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
        CadastroDeCursos.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	CadastroDeCursos.getContentPane().add(lblNewLabel_11);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldid_Curso.setText(table.getValueAt(row, 0).toString());
                    txtFldNomeCursos.setText(table.getValueAt(row, 1).toString());
                    textFldid_Coordenador.setText(table.getValueAt(row, 2).toString());
                    
                }
            }
        });
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Cursos");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        CadastroDeCursos.getContentPane().add(lblNewLabel_11_1);
        updateTable();
    }
    	
    private void updateTable() {
        conexao = Controle_EscolarConnection.ConnectDb();
        if (conexao != null) {
            String sql = "SELECT * FROM curso";
            try {
                mypst = conexao.prepareStatement(sql);
                myrs = mypst.executeQuery();

                model.setRowCount(0); // Limpar tabela antes de atualizar

                while (myrs.next()) {
                    String id_Curso = myrs.getString("id_Curso");
                    String nome = myrs.getString("nome_Curso");
                    String id_Coordenador = myrs.getString("id_Coordenador");
                    
                    model.addRow(new Object[]{id_Curso, nome, id_Coordenador});
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