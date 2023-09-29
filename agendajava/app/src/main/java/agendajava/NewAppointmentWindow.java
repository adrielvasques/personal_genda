package agendajava;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import agendajava.entity.AppointmentTask;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import agendajava.AppointmentDAO.AppointmentTaskDAO;

public class NewAppointmentWindow extends JFrame {
    private JTextField titleField; // Alterado o nome do campo de título
    private JTextField descriptionField;
    private JFormattedTextField timeField;
    private JComboBox<String> categoriaDropdown;
    private JComboBox<String> statusDropdown;
    private JButton saveButton;
    private static String data;

    public NewAppointmentWindow(String data) {
        NewAppointmentWindow.data = data;
        setTitle("Nova Atividade");
        setSize(400, 280); // Aumentamos a altura para acomodar os campos
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        JLabel titlelLabel = new JLabel("Título:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(titlelLabel, constraints);

        titleField = new JTextField(20); // Campo de título
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3; // Aumentamos o tamanho para ocupar mais espaço na largura
        panel.add(titleField, constraints);

        JLabel descriptionLabel = new JLabel("Descrição:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(descriptionLabel, constraints);

        descriptionField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 3; // Aumentamos o tamanho para ocupar mais espaço na largura
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

        String[] status = {"Cancelado", "Marcado", "Concluído"};

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
        constraints.gridwidth = 4; // Aumentamos o tamanho para ocupar mais espaço na largura
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, constraints);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText(); // Obtém o título
                String description = descriptionField.getText();
                String timeText = timeField.getText();

                try {
                    LocalTime selectedTime = LocalTime.parse(timeText);

                    if (selectedTime.getHour() >= 0 && selectedTime.getHour() <= 23
                            && selectedTime.getMinute() >= 0 && selectedTime.getMinute() <= 59) {
                        System.out.println(data);

                        AppointmentTask appointment = new AppointmentTask();
                        appointment.setData(data);
                        appointment.setHora(timeText);
                        appointment.setDescricao(description);
                        appointment.setCategoria(categoriaDropdown.getSelectedItem().toString());
                        appointment.setStatus(statusDropdown.getSelectedItem().toString());
                        appointment.setTitulo(title); // Define o título

                        AppointmentTaskDAO taskDAO = new AppointmentTaskDAO();
                        taskDAO.inserirTarefa(appointment);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Horário inválido. Certifique-se de que as horas estão entre 00 e 23 e os minutos entre 00 e 59.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato de hora inválido. Use o formato HH:mm.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
        setLocationRelativeTo(null);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                NewAppointmentWindow window = new NewAppointmentWindow(data);
                window.setVisible(true);
            }
        });
    }
}