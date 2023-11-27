package agendajava;
import javax.swing.*;
import agendajava.AppointmentDAO.AppointmentTaskDAO;
import agendajava.entity.AppointmentTask;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class EdicaoCompromissoDialog extends JDialog {
    private JTextField tituloField;
    private JTextField descricaoField;
    private JFormattedTextField horaField; // Declarar como um campo de classe
    private JComboBox<String> categoriaDropdown;
    private JComboBox<String> statusDropdown;
    private JButton salvarButton;
    private CompromissosGUI compromissosGUI;
    private static String date;
    

    public EdicaoCompromissoDialog(CompromissosGUI parent, AppointmentTask compromisso, String data) {
        super(parent, "Editar Compromisso", true);
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setResizable(false);
        date = data;

        compromissosGUI = parent;

        JPanel panel = new JPanel(new GridLayout(7, 2));

        panel.add(new JLabel("Título:"));
        tituloField = new JTextField(compromisso.getTitulo());
        panel.add(tituloField);

        panel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField(compromisso.getDescricao());
        panel.add(descricaoField);
        try {
            MaskFormatter mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter(' ');

            // Simule que você tenha um valor de compromisso.getHora()
            String horaValue = compromisso.getHora();

            horaField = new JFormattedTextField(mask); // Remova a declaração do tipo JFormattedTextField
            horaField.setColumns(5);
            horaField.setText(horaValue); // Define o valor obtido do compromisso.getHora()
            panel.add(new JLabel("Hora:"));
            panel.add(horaField);
            add(panel);
            setLocationRelativeTo(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    

        String[] categorias = {
            "Trabalho", "Pessoal", "Educação", "Saúde e Bem-Estar", "Social",
            "Viagens", "Financeiro", "Lembretes", "Projetos", "Cultural e Entretenimento",
            "Família", "Esportes", "Outros"
        };

        String[] status = {"Planejada", "Cancelada", "Concluída"};

        JLabel categoriaLabel = new JLabel("Categoria:");
        categoriaLabel.setBorder(new EmptyBorder(0, 0, 0, 10)); // Adicione uma margem à direita
        panel.add(categoriaLabel);

        categoriaDropdown = new JComboBox<>(categorias);
        categoriaDropdown.setSelectedItem(compromisso.getCategoria());
        panel.add(categoriaDropdown);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBorder(new EmptyBorder(0, 0, 0, 10)); // Adicione uma margem à direita
        panel.add(statusLabel);

        statusDropdown = new JComboBox<>(status);
        statusDropdown.setSelectedItem(compromisso.getStatus());
        panel.add(statusDropdown);

        salvarButton = new JButton("Salvar");
        panel.add(new JLabel());
        panel.add(salvarButton);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compromisso.setData(compromisso.getData());
                compromisso.setHora(horaField.getText());
                compromisso.setTitulo(tituloField.getText());
                compromisso.setDescricao(descricaoField.getText());
                compromisso.setCategoria(categoriaDropdown.getSelectedItem().toString());
                compromisso.setStatus(statusDropdown.getSelectedItem().toString());

                new AppointmentTaskDAO().atualizarTarefa(compromisso);

                JOptionPane.showMessageDialog(EdicaoCompromissoDialog.this, "Compromisso atualizado com sucesso!");
                dispose();
                compromissosGUI.recarregarCompromissos(date);
            }
        });

        add(panel);
    }

    public EdicaoCompromissoDialog(AppointmentTask compromissoSelecionado, String data) {
        setTitle("Editar Compromisso");
        setSize(300, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        adicionarCampo(panel, gbc, "Título:", compromissoSelecionado.getTitulo());
        adicionarCampo(panel, gbc, "Descrição:", compromissoSelecionado.getDescricao());
        adicionarCampoHora(panel, gbc, "Hora:", compromissoSelecionado.getHora());

        String[] categorias = {
            "Trabalho", "Pessoal", "Educação", "Saúde e Bem-Estar", "Social",
            "Viagens", "Financeiro", "Lembretes", "Projetos", "Cultural e Entretenimento",
            "Família", "Esportes", "Outros"
        };

        String[] status = {"Planejada", "Cancelada", "Concluída"};

        adicionarCampoDropdown(panel, gbc, "Categoria:", compromissoSelecionado.getCategoria(), categorias);
        adicionarCampoDropdown(panel, gbc, "Status:", compromissoSelecionado.getStatus(), status);

        //JButton fecharButton = new JButton("Fechar");
        //fecharButton.addActionListener(e -> dispose());
        //gbc.gridy++;
        //gbc.gridwidth = 2;
        //gbc.fill = GridBagConstraints.NONE;
        //panel.add(fecharButton, gbc);

        add(panel);
    }

    private void adicionarCampo(JPanel panel, GridBagConstraints gbc, String label, String valor) {
        JLabel labelComponent = new JLabel(label);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(labelComponent, gbc);

        JTextField campo = new JTextField(valor);
        campo.setEditable(false);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        panel.add(campo, gbc);

        // Reinicializa os valores de GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
    }

    private void adicionarCampoHora(JPanel panel, GridBagConstraints gbc, String label, String hora) {
        JLabel labelComponent = new JLabel(label);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(labelComponent, gbc);

        try {
            MaskFormatter mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter(' ');

            horaField = new JFormattedTextField(mask);
            horaField.setColumns(5);
            horaField.setText(hora);
            horaField.setEditable(false);
            gbc.gridx++;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            panel.add(horaField, gbc);

            // Reinicializa os valores de GridBagConstraints
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0.0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void adicionarCampoDropdown(JPanel panel, GridBagConstraints gbc, String label, String selecionado, String[] opcoes) {
        JLabel labelComponent = new JLabel(label);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(labelComponent, gbc);

        JComboBox<String> dropdown = new JComboBox<>(opcoes);
        dropdown.setSelectedItem(selecionado);
        dropdown.setEnabled(false);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        panel.add(dropdown, gbc);

        // Reinicializa os valores de GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
    }



    public static String getDate() {
        return date;
    }
}
