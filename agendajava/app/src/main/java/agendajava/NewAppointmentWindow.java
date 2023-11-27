package agendajava;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import agendajava.entity.AppointmentTask;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import agendajava.AppointmentDAO.AppointmentTaskDAO;

public class NewAppointmentWindow extends JFrame {
    private JTextField titleField;
    private JTextField descriptionField;
    private JFormattedTextField timeField;
    private JComboBox<String> categoriaDropdown;
    private JComboBox<String> statusDropdown;
    private JButton saveButton;
    private static String data;

    public interface OnClose {
        void onClose();
    }

    public NewAppointmentWindow(String data, OnClose onClose) {
        NewAppointmentWindow.data = data;
        setTitle("Nova Atividade");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setExtendedState(JFrame.MAXIMIZED_VERT);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        JLabel titlelLabel = new JLabel("Título:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(titlelLabel, constraints);

        titleField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        panel.add(titleField, constraints);

        JLabel descriptionLabel = new JLabel("Descrição:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(descriptionLabel, constraints);

        descriptionField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        panel.add(descriptionField, constraints);

        JLabel timeLabel = new JLabel("Horário:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(timeLabel, constraints);

        timeField = new JFormattedTextField(createFormatter("##:##"));
        timeField.setColumns(4);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(timeField, constraints);

        String[] categorias = {
            "Trabalho", "Pessoal", "Educação", "Saúde e Bem-Estar", "Social",
            "Viagens", "Financeiro", "Lembretes", "Projetos", "Cultural e Entretenimento",
            "Família", "Esportes", "Outros"
        };

        String[] status = {"Planejada", "Ativa", "Concluída"};

        JLabel categoriaLabel = new JLabel("Categoria:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(categoriaLabel, constraints);

        categoriaDropdown = new JComboBox<>(categorias);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(categoriaDropdown, constraints);

        JLabel statusLabel = new JLabel("Status:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(statusLabel, constraints);

        statusDropdown = new JComboBox<>(status);
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(statusDropdown, constraints);

        saveButton = new JButton("Salvar");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, constraints);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String timeText = timeField.getText();

                try {
                    LocalTime selectedTime = LocalTime.parse(timeText);

                    if (selectedTime.getHour() >= 0 && selectedTime.getHour() <= 23
                            && selectedTime.getMinute() >= 0 && selectedTime.getMinute() <= 59) {


                        AppointmentTask appointment = new AppointmentTask();
                        appointment.setData(data);
                        appointment.setHora(timeText);
                        appointment.setDescricao(description);
                        appointment.setCategoria(categoriaDropdown.getSelectedItem().toString());
                        appointment.setStatus(statusDropdown.getSelectedItem().toString());
                        appointment.setTitulo(title);

                        AppointmentTaskDAO taskDAO = new AppointmentTaskDAO();
                        taskDAO.inserirTarefa(appointment);

                        JOptionPane.showMessageDialog(null, "Compromisso adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Horário inválido. Certifique-se de que as horas estão entre 00 e 23 e os minutos entre 00 e 59.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato de hora inválido. Use o formato HH:mm.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                onClose.onClose();
            }
        });

        add(panel);
        setLocationRelativeTo(null);

        // onClose callback
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onClose.onClose();
            }
        });
    }

    private MaskFormatter createFormatter(String format) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    private void limparCampos() {
        titleField.setText("");
        descriptionField.setText("");
        timeField.setText("");
        categoriaDropdown.setSelectedIndex(0);
        statusDropdown.setSelectedIndex(0);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                NewAppointmentWindow window = new NewAppointmentWindow(data, () -> {});
                window.setVisible(true);
                window.setResizable(false);
                window.setExtendedState(JFrame.MAXIMIZED_HORIZ);
                window.setExtendedState(JFrame.MAXIMIZED_VERT);

            }
        });
    }
}
