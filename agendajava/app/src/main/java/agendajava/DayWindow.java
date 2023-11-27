package agendajava;
import agendajava.AppointmentDAO.AppointmentTaskDAO;
import javax.swing.*;
import java.util.List;

import agendajava.entity.AppointmentTask;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class DayWindow extends JFrame {
    private static String data;
    private CompromissosGUI compromissosGUI;
    private NewAppointmentWindow newAppointmentWindow;
    private JButton selectedButton; // Variável para rastrear o botão selecionado
    private static boolean janelaAberta = false; // Variável para rastrear se a janela está aberta
    private JButton viewAppointmentsButton;

    public interface OnClose {
        void onClose();
    }

    public DayWindow(String data, OnClose onClose) {
        DayWindow.data = data;
        setTitle("Compromissos do Dia");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton viewAppointmentsButton = new JButton("Ver Compromissos do Dia");
        viewAppointmentsButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewAppointmentsButton.setPreferredSize(new Dimension(250, 40));
        viewAppointmentsButton.setBackground(Color.BLUE);
        viewAppointmentsButton.setForeground(Color.WHITE);
        viewAppointmentsButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(viewAppointmentsButton, constraints);

        JButton addAppointmentButton = new JButton("Adicionar Novo Compromisso");
        addAppointmentButton.setFont(new Font("Arial", Font.BOLD, 14));
        addAppointmentButton.setPreferredSize(new Dimension(250, 40));
        addAppointmentButton.setBackground(Color.GREEN);
        addAppointmentButton.setForeground(Color.WHITE);
        addAppointmentButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(addAppointmentButton, constraints);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onClose.onClose();
            }
        });

        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("viewAppointmentsButton.addActionListener janela: " + janelaAberta);

                if (!janelaAberta) {
                    List<AppointmentTask> tarefas = AppointmentTaskDAO.recuperarTarefasPorDia(data);
                    if (!tarefas.isEmpty()) {
                        compromissosGUI = new CompromissosGUI(tarefas, data, () -> { janelaAberta = false; });
                        compromissosGUI.setVisible(true);
                        janelaAberta = true;
                        compromissosGUI.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent windowEvent) {
                                janelaAberta = false;
                            }
                        });

                        // Mantenha o botão como vermelho se não estiver selecionado
                        if (selectedButton != viewAppointmentsButton) {
                            viewAppointmentsButton.setBackground(Color.RED);
                            selectedButton = viewAppointmentsButton;
                        }
                    } else {
                        JOptionPane.showMessageDialog(DayWindow.this, "Nenhuma tarefa encontrada para a data " + data);
                    }
                }
            }
        });

        addAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!janelaAberta) {
                    newAppointmentWindow = new NewAppointmentWindow(data, () -> { janelaAberta = false; System.out.println("novo onclose"); });
                    newAppointmentWindow.setVisible(true);
                    janelaAberta = true;
                    newAppointmentWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent windowEvent) {
                            janelaAberta = false;
                        }
                    });

                    // Mantenha o botão como verde se não estiver selecionado
                    if (selectedButton != addAppointmentButton) {
                        addAppointmentButton.setBackground(Color.GREEN);
                        selectedButton = addAppointmentButton;
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (compromissosGUI != null) {
                    compromissosGUI.dispose();
                }
                if (newAppointmentWindow != null) {
                    newAppointmentWindow.dispose();
                }
            }
        });

        add(panel);
        setLocationRelativeTo(null);
    }

    public static String getData() {
        return data;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DayWindow window = new DayWindow(data, () -> {});
                window.setVisible(true);
            }
        });
    }
}
