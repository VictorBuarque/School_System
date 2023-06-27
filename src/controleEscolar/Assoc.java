package controleEscolar;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class Assoc {

    private JFrame frame;
    private JComboBox<String> comboBox;
    private JList<String> listMaterias;
    private JList<String> listMaterias_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Assoc window = new Assoc();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Assoc() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setForeground(SystemColor.activeCaption);
        frame.getContentPane().setBackground(SystemColor.inactiveCaption);
        frame.setBackground(SystemColor.activeCaption);
        frame.setBounds(100, 100, 800, 600);
        frame.getContentPane().setLayout(null);

        comboBox = new JComboBox<>();
        comboBox.setBounds(97, 63, 235, 23);
        frame.getContentPane().add(comboBox);

        listMaterias = new JList<>();
        listMaterias.setBounds(97, 126, 235, 374);
        frame.getContentPane().add(listMaterias);

        JButton btnPassar = new JButton(">>");
        btnPassar.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnPassar.setBounds(353, 202, 90, 30);
        frame.getContentPane().add(btnPassar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnLimpar.setBounds(353, 282, 90, 30);
        frame.getContentPane().add(btnLimpar);

        JButton btnRemover = new JButton("<<");
        btnRemover.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnRemover.setBounds(353, 242, 90, 30);
        frame.getContentPane().add(btnRemover);

        JButton btnDefinir = new JButton("Salvar");
        btnDefinir.setFont(new Font("Black Ops One", Font.PLAIN, 12));
        btnDefinir.setBounds(353, 373, 90, 30);
        frame.getContentPane().add(btnDefinir);

        JLabel lblMaterias_1 = new JLabel("Curso");
        lblMaterias_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblMaterias_1.setFont(new Font("Arial Black", Font.BOLD, 14));
        lblMaterias_1.setBounds(97, 30, 235, 23);
        frame.getContentPane().add(lblMaterias_1);

        listMaterias_1 = new JList<>();
        listMaterias_1.setBounds(457, 126, 235, 374);
        frame.getContentPane().add(listMaterias_1);

        JLabel lblMaterias_2 = new JLabel("Disciplinas Disponíveis");
        lblMaterias_2.setBorder(null);
        lblMaterias_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblMaterias_2.setFont(new Font("Arial Black", Font.BOLD, 14));
        lblMaterias_2.setBounds(97, 96, 234, 23);
        frame.getContentPane().add(lblMaterias_2);

        JLabel lblMaterias_3 = new JLabel("Disciplinas Selecionadas");
        lblMaterias_3.setBorder(null);
        lblMaterias_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblMaterias_3.setFont(new Font("Arial Black", Font.BOLD, 14));
        lblMaterias_3.setBounds(457, 96, 234, 23);
        frame.getContentPane().add(lblMaterias_3);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 784, 21);
        frame.getContentPane().add(menuBar);

        JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
        menuBar.add(mntmNewMenuItem);

        // Event listeners
        btnPassar.addActionListener(e -> moverDisciplina(true));
        btnRemover.addActionListener(e -> moverDisciplina(false));
        btnLimpar.addActionListener(e -> limparSelecao());
        btnDefinir.addActionListener(e -> salvarSelecao());
    }

    private void moverDisciplina(boolean paraSelecionadas) {
        JList<String> origem = paraSelecionadas ? listMaterias : listMaterias_1;
        JList<String> destino = paraSelecionadas ? listMaterias_1 : listMaterias;
        DefaultListModel<String> modelOrigem = (DefaultListModel<String>) origem.getModel();
        DefaultListModel<String> modelDestino = (DefaultListModel<String>) destino.getModel();
        int[] indicesSelecionados = origem.getSelectedIndices();
        for (int i = indicesSelecionados.length - 1; i >= 0; i--) {
            String disciplina = modelOrigem.remove(indicesSelecionados[i]);
            modelDestino.addElement(disciplina);
        }
    }

    private void limparSelecao() {
        DefaultListModel<String> modelSelecionadas = (DefaultListModel<String>) listMaterias_1.getModel();
        modelSelecionadas.clear();
    }

    private void salvarSelecao() {
        // Implemente a lógica de salvar a seleção no banco de dados aqui
    }
}
