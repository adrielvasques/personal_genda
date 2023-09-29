package agendajava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayWindow extends JFrame {
    private static String data;
    private NewAppointmentWindow newAppointmentWindow; // Referência para a janela NewAppointmentWindow

    public DayWindow(String data) {
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

        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir a tela de visualização de compromissos do dia
                // Você pode criar uma nova tela para isso
            }
        });

        addAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifica se já existe uma instância de NewAppointmentWindow
                if (newAppointmentWindow == null) {
                    newAppointmentWindow = new NewAppointmentWindow(data);
                }

                // Torna a NewAppointmentWindow visível
                newAppointmentWindow.setVisible(true);
            }
        });

        add(panel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DayWindow window = new DayWindow(data);
                window.setVisible(true);
            }
        });
    }
}
