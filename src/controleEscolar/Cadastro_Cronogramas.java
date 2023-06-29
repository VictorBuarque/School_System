package controleEscolar;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import com.functions.util.Controle_EscolarConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Cadastro_Cronogramas {

    private JFrame frmCadastroDeCronogramas;
    private JTextField txtFldData;
    
    private JTextField txtFldHoraInicio;
    private JTable table;
   
    private DefaultTableModel model;
    private Connection conexao;
    private PreparedStatement mypst;
    private ResultSet myrs;
    private JTextField txtFldHoraFim;

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

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBounds(101, 114, 163, 31);
        frmCadastroDeCronogramas.getContentPane().add(comboBox);
        
        JComboBox<String> comboBox_1 = new JComboBox<String>();
        comboBox_1.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox_1.setBounds(101, 155, 163, 31);
        frmCadastroDeCronogramas.getContentPane().add(comboBox_1);
        try {
            conexao = Controle_EscolarConnection.ConnectDb();
            if (conexao != null) {
                String sql = "SELECT * FROM professor";
                try (Statement stmt = conexao.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    comboBox.addItem("");
                    while (rs.next()) {
                        String idProfessor = rs.getString("nome_Professor");
                        comboBox.addItem(idProfessor);
                    }
                }

                sql = "SELECT * FROM disciplina";
                try (Statement stmt = conexao.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    comboBox_1.addItem("");
                    while (rs.next()) {
                        String idDisciplina = rs.getString("nome_Disciplina");
                        comboBox_1.addItem(idDisciplina);
                    }
                }
                updateTable();
        } else {
            JOptionPane.showMessageDialog(frmCadastroDeCronogramas, "Não foi possível conectar ao banco de dados.");
        }
    } catch (Exception erro) {
        JOptionPane.showMessageDialog(frmCadastroDeCronogramas, erro);
    }

        frmCadastroDeCronogramas.setVisible(true);

        txtFldHoraInicio = new JTextField();
        txtFldHoraInicio.setBounds(101, 203, 163, 35);
        txtFldHoraInicio.setColumns(10);
        frmCadastroDeCronogramas.getContentPane().add(txtFldHoraInicio);
        
        txtFldHoraFim = new JTextField();
        txtFldHoraFim.setColumns(10);
        txtFldHoraFim.setBounds(101, 248, 163, 35);
        frmCadastroDeCronogramas.getContentPane().add(txtFldHoraFim);

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
        lblNewLabel_2.setBounds(13, 155, 87, 31);
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Hora Inicio:");
        lblNewLabel_3.setBounds(23, 205, 75, 31);
        lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 13));
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_11_1 = new JLabel("Cadastro de Cronogramas");
        lblNewLabel_11_1.setToolTipText("");
        lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11_1.setForeground(SystemColor.infoText);
        lblNewLabel_11_1.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel_11_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_11_1.setBounds(0, 10, 785, 42);
        frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_11_1);
        
        JLabel lblNewLabel_11 = new JLabel("Todos os direitos são reservados a V.G.R.B.S Serviços ");
    	lblNewLabel_11.setForeground(SystemColor.infoText);
    	lblNewLabel_11.setToolTipText("");
    	lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
    	lblNewLabel_11.setBackground(SystemColor.activeCaption);
    	lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblNewLabel_11.setBounds(10, 411, 765, 42);
    	frmCadastroDeCronogramas.getContentPane().add(lblNewLabel_11);
    	
    	JLabel lblTurma = new JLabel("Hora Fim:");
        lblTurma.setLabelFor(txtFldHoraFim);
        lblTurma.setFont(new Font("Arial", Font.BOLD, 13));
        lblTurma.setBounds(34, 250, 64, 31);
        frmCadastroDeCronogramas.getContentPane().add(lblTurma);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(274, 67, 402, 342);
        frmCadastroDeCronogramas.getContentPane().add(scrollPane);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtFldData.setText(table.getValueAt(row, 0).toString());
                    comboBox.setToolTipText(table.getValueAt(row, 1).toString());
                    comboBox_1.setToolTipText(table.getValueAt(row, 2).toString());
                    txtFldHoraInicio.setText(table.getValueAt(row, 3).toString());
                    txtFldHoraFim.setText(table.getValueAt(row, 4).toString());
                }
            }
        });
        scrollPane.setViewportView(table);
        
        updateTable();

        JButton btnInserir = new JButton("Salvar");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
        btnInserir.setBackground(new Color(255, 255, 255));
        btnInserir.setBounds(686, 69, 89, 35);
        btnInserir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    conexao = Controle_EscolarConnection.ConnectDb();
        	    String data = txtFldData.getText();
        	    String idProfessor = obterIdProfessor((String) comboBox.getSelectedItem());
        	    String idDisciplina = obterIdDisciplina((String) comboBox_1.getSelectedItem());
        	    String horaInicio = txtFldHoraInicio.getText();
        	    String horaFim = txtFldHoraFim.getText(); // Corrigido: txtFldHoraFim.getText() em vez de txtFldHoraInicio.getText()
        	    try {
        	        if (conexao != null) {
        	            String sqlInsert = "INSERT INTO cronograma (dt_Cronograma, id_Professor, id_Disciplina, hr_Inicio, hr_Fim) VALUES (?, ?, ?, ?, ?)";
        	            PreparedStatement pstmtInsert = conexao.prepareStatement(sqlInsert);
        	            pstmtInsert.setString(1, data);
        	            pstmtInsert.setString(2, idProfessor);
        	            pstmtInsert.setString(3, idDisciplina);
        	            pstmtInsert.setString(4, horaInicio);
        	            pstmtInsert.setString(5, horaFim);
        	            pstmtInsert.executeUpdate();
        	            pstmtInsert.close();
        	            conexao.close();
        	            JOptionPane.showMessageDialog(frmCadastroDeCronogramas, "Dados salvos com sucesso!");
        	            updateTable();
        	            txtFldData.setText("");
        	            comboBox.setToolTipText("");
        	            comboBox_1.setToolTipText("");
        	            txtFldHoraInicio.setText("");
        	            txtFldHoraFim.setText("");
        	        } else {
        	            JOptionPane.showMessageDialog(frmCadastroDeCronogramas, "Não foi possível conectar ao banco de dados.");
        	        }
        	    } catch (SQLException ex) {
        	        JOptionPane.showMessageDialog(frmCadastroDeCronogramas, "Erro ao incluir registro: " + ex.getMessage());
        	    }
        	 }
        }
	);
		btnInserir.setFont(new Font("Arial", Font.BOLD, 12));
		btnInserir.setBounds(686, 69, 89, 31);
		frmCadastroDeCronogramas.getContentPane().add(btnInserir);

        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAlterar.setBackground(new Color(255, 255, 255));
        btnAlterar.setBounds(686, 115, 89, 35);
        btnAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conexao = Controle_EscolarConnection.ConnectDb();
                String data = txtFldData.getText();
                String idProfessor = obterIdProfessor((String) comboBox.getSelectedItem());
                String idDisciplina = obterIdDisciplina((String) comboBox_1.getSelectedItem());
                String horaInicio = txtFldHoraInicio.getText();
                String horaFim = txtFldHoraFim.getText(); // Corrigido
                if (conexao != null) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String sql = "UPDATE cronograma SET id_Professor=?, id_Disciplina=?, hr_Inicio=?, hr_Fim=? WHERE dt_Cronograma=?";
                        try {
                            mypst = conexao.prepareStatement(sql);
                            mypst.setString(1, idProfessor);
                            mypst.setString(2, idDisciplina);
                            mypst.setString(3, horaInicio);
                            mypst.setString(4, horaFim);
                            mypst.setString(5, data);
                            mypst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                            updateTable();
                            txtFldData.setText("");
                            comboBox.setToolTipText("");
                            comboBox_1.setToolTipText("");
                            txtFldHoraInicio.setText("");
                            txtFldHoraFim.setText("");
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
                            comboBox.setToolTipText("");
                            comboBox_1.setToolTipText("");
                            txtFldHoraInicio.setText("");
                            txtFldHoraFim.setText("");
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
		        comboBox.setToolTipText("");
		        comboBox_1.setToolTipText("");
		        txtFldHoraInicio.setText("");
		        txtFldHoraFim.setText("");
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
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 12));
        btnFechar.setBackground(new Color(255, 255, 255));
        btnFechar.setBounds(686, 338, 89, 35);
        btnFechar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fecharPrograma(e);
            }
        });
        frmCadastroDeCronogramas.getContentPane().add(btnFechar);  
        
    }
    
    public void fecharPrograma(ActionEvent e) {
        int confirmacao = JOptionPane.showConfirmDialog(frmCadastroDeCronogramas, "Deseja realmente fechar o programa?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private String obterIdProfessor(String nomeCurso) {
        try {
            var myConn = Controle_EscolarConnection.ConnectDb();
            if (myConn != null) {
                String sql = "SELECT id_Professor FROM professor WHERE nome_Professor = ?";
                try (PreparedStatement pstmt = myConn.prepareStatement(sql)) {
                    pstmt.setString(1, nomeCurso);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("id_Professor");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frmCadastroDeCronogramas, "Erro ao obter ID do curso: " + ex.getMessage());
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
            JOptionPane.showMessageDialog(frmCadastroDeCronogramas, "Erro ao obter ID da disciplina: " + ex.getMessage());
        }
        return null;
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