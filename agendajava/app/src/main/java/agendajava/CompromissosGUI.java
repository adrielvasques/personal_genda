package agendajava;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import agendajava.entity.AppointmentTask;
import agendajava.AppointmentDAO.AppointmentTaskDAO;

public class CompromissosGUI extends JFrame {
    private DefaultListModel<String> compromissosModel = new DefaultListModel<>();
    private JList<String> compromissosList = new JList<>(compromissosModel);
    private JButton paginaAnteriorButton = new JButton("◄ Anterior");
    private JButton proximaPaginaButton = new JButton("Próxima ►");
    private JLabel totalCompromissosLabel = new JLabel("Total de Compromissos: 0");
    private int totalCompromissos = 0;

    private List<AppointmentTask> compromissosCompleto;
    private int paginaAtual = 0;
    private int itensPorPagina = 5;
    private static String data;

    public interface OnClose {
        void onClose();
    }

    public CompromissosGUI(List<AppointmentTask> compromissos, String data, OnClose onClose) {
        this.data = data;

        compromissosCompleto = new ArrayList<>(compromissos);

        Collections.sort(compromissosCompleto, Comparator.comparing(AppointmentTask::getHora));

        setTitle("Lista de Compromissos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 210);

        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botoesPanel.add(paginaAnteriorButton);
        botoesPanel.add(proximaPaginaButton);

        compromissosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        paginaAnteriorButton.addActionListener(e -> irParaPagina(paginaAtual - 1));
        proximaPaginaButton.addActionListener(e -> irParaPagina(paginaAtual + 1));

        contentPanel.add(new JScrollPane(compromissosList), BorderLayout.CENTER);
        contentPanel.add(botoesPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(compromissosList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel totalCompromissosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalCompromissosPanel.add(totalCompromissosLabel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(totalCompromissosPanel, BorderLayout.SOUTH);

        add(mainPanel);

        paginaAnteriorButton.setMargin(new Insets(0, 0, 0, 0));
        proximaPaginaButton.setMargin(new Insets(0, 0, 0, 0));

        paginaAnteriorButton.setPreferredSize(new Dimension(30, 30));
        proximaPaginaButton.setPreferredSize(new Dimension(30, 30));
        paginaAnteriorButton.setFont(new Font("Arial", Font.BOLD, 14));
        proximaPaginaButton.setFont(new Font("Arial", Font.BOLD, 14));
        paginaAnteriorButton.setText("◄");
        proximaPaginaButton.setText("►");

        compromissosList.setForeground(Color.DARK_GRAY);
        compromissosList.setFont(new Font("Arial", Font.PLAIN, 14));

        compromissosList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = compromissosList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        int realIndex = (paginaAtual * itensPorPagina) + selectedIndex;
                        if (realIndex < compromissosCompleto.size()) {
                            int compromissoId = compromissosCompleto.get(realIndex).getIdCompromisso();
                            abrirTelaDeEdicao(compromissoId);
                        }
                    }
                }
            }
        });

        setResizable(false);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onClose.onClose();
            }
        });

        irParaPagina(0);
    }

    private void atualizarListaCompromissos() {
        int startIndex = paginaAtual * itensPorPagina;
        int endIndex = Math.min(startIndex + itensPorPagina, compromissosCompleto.size());

        compromissosModel.clear();

        for (int i = startIndex; i < endIndex; i++) {
            AppointmentTask compromisso = compromissosCompleto.get(i);
            String itemFormatted = "<html><b>" + compromisso.getHora() + "</b> : " + compromisso.getTitulo() + "</html>";
            compromissosModel.addElement(itemFormatted);
        }
    }

    private int irParaPagina(int pagina) {
        compromissosModel.clear();
        if (pagina >= 0 && pagina < Math.ceil((double) compromissosCompleto.size() / itensPorPagina)) {
            paginaAtual = pagina;

            Collections.sort(compromissosCompleto, Comparator.comparing(AppointmentTask::getHora));

        }
        atualizarListaCompromissos();

        totalCompromissosLabel.setText("Total de Compromissos: " + compromissosCompleto.size());

        paginaAnteriorButton.setEnabled(paginaAtual > 0);
        proximaPaginaButton.setEnabled(paginaAtual < Math.ceil((double) compromissosCompleto.size() / itensPorPagina) - 1);
        return paginaAtual;
    }

    private void abrirTelaDeEdicao(int compromissoId) {
        // Localize o compromisso com base no ID
        AppointmentTask compromissoSelecionado = null;
        for (AppointmentTask compromisso : compromissosCompleto) {
            if (compromisso.getIdCompromisso() == compromissoId) {
                compromissoSelecionado = compromisso;
                break;
            }
        }

        if (compromissoSelecionado != null) {
            // Abra a tela de edição com base no compromisso selecionado
            EdicaoCompromissoDialog dialog = new EdicaoCompromissoDialog(this, compromissoSelecionado, data);
            dialog.setVisible(true);
            dialog.setResizable(false);
        }
    }

    public static void main(String[] args) {
        new AppointmentTaskDAO();
        List<AppointmentTask> listaCompromissos = AppointmentTaskDAO.recuperarTarefas();

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new CompromissosGUI(listaCompromissos, data, () -> {}).setVisible(true));
    }

    public void recarregarCompromissos(String date) {
        compromissosCompleto = AppointmentTaskDAO.recuperarTarefasPorDia(date);
        data = date;
        reiniciarTela();
    }
    
    private void reiniciarTela() {
        irParaPagina(paginaAtual);
    }
}
