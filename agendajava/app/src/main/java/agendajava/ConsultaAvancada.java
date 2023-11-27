package agendajava;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;
import java.util.List;

import agendajava.AppointmentDAO.AppointmentTaskDAO;
import agendajava.entity.AppointmentTask;

import java.util.HashMap;
import java.util.Map;

public class ConsultaAvancada extends JFrame {
    private JComboBox<String> mesField; // JComboBox para os meses
    private JTextField diaField;
    private JTextField anoField;
    private JTextField horaField;
    private JTextField tituloField;
    private JTextField descricaoField;
    private JComboBox<String> categoriaField; // JComboBox para categorias
    private JComboBox<String> statusField; // JComboBox para status

    private JButton pesquisarButton; // Botão para pesquisar
    private static boolean janelaAberta = false;

    private Map<String, Integer> mesesMap; // Mapa para mapear nomes de meses para seus valores correspondentes

    public ConsultaAvancada() {
        setTitle("Pesquisar Avançada");
        setSize(380, 500); // Aumentei a altura para acomodar os campos adicionais
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mesesMap = new HashMap<>(); // Inicialize o mapa dos meses
        mesesMap.put(" ", 0);
        mesesMap.put("Janeiro", 1);
        mesesMap.put("Fevereiro", 2);
        mesesMap.put("Março", 3);
        mesesMap.put("Abril", 4);
        mesesMap.put("Maio", 5);
        mesesMap.put("Junho", 6);
        mesesMap.put("Julho", 7);
        mesesMap.put("Agosto", 8);
        mesesMap.put("Setembro", 9);
        mesesMap.put("Outubro", 10);
        mesesMap.put("Novembro", 11);
        mesesMap.put("Dezembro", 12);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Use GridBagConstraints to align components to the left
        constraints.anchor = GridBagConstraints.WEST;

        // Tamanho fixo para todos os campos de texto
        int fieldWidth = 200;

        // JComboBox para selecionar o mês
        JLabel mesLabel = new JLabel("Mês:");
        String[] nomesMeses = {
            " ", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        };
        mesField = new JComboBox<>(nomesMeses);
        mesField.setPreferredSize(new Dimension(fieldWidth, mesField.getPreferredSize().height));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(mesLabel, constraints);
        constraints.gridx = 1;
        panel.add(mesField, constraints);

        // Label and Text Field for Dia
        JLabel diaLabel = new JLabel("Dia:");
        diaField = new JTextField(10);
        diaField.setPreferredSize(new Dimension(fieldWidth, diaField.getPreferredSize().height));
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(diaLabel, constraints);
        constraints.gridx = 1;
        panel.add(diaField, constraints);

        // Label and Text Field for Ano
        JLabel anoLabel = new JLabel("Ano:");
        anoField = new JTextField(10);
        anoField.setPreferredSize(new Dimension(fieldWidth, anoField.getPreferredSize().height));
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(anoLabel, constraints);
        constraints.gridx = 1;
        panel.add(anoField, constraints);

        // Label and Text Field for Hora
        JLabel horaLabel = new JLabel("Hora (HH:mm):");
        horaField = new JTextField(10); // Alterado para JTextField
        horaField.setPreferredSize(new Dimension(fieldWidth, horaField.getPreferredSize().height)); // Defina o tamanho desejado
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(horaLabel, constraints);
        constraints.gridx = 1;
        panel.add(horaField, constraints);

        // Label and Text Field for Título
        JLabel tituloLabel = new JLabel("Título:");
        tituloField = new JTextField(10);
        tituloField.setPreferredSize(new Dimension(fieldWidth, tituloField.getPreferredSize().height));
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(tituloLabel, constraints);
        constraints.gridx = 1;
        panel.add(tituloField, constraints);

        // Label and Text Field for Descrição
        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoField = new JTextField(10);
        descricaoField.setPreferredSize(new Dimension(fieldWidth, descricaoField.getPreferredSize().height));
        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(descricaoLabel, constraints);
        constraints.gridx = 1;
        panel.add(descricaoField, constraints);

        // JComboBox para selecionar a categoria
        JLabel categoriaLabel = new JLabel("Categoria:");
        String[] categorias = {
            " ", "Trabalho", "Pessoal", "Educação", "Saúde e Bem-Estar", "Social",
            "Viagens", "Financeiro", "Lembretes", "Projetos", "Cultural e Entretenimento",
            "Família", "Esportes", "Outros"
        };
        categoriaField = new JComboBox<>(categorias);
        categoriaField.setPreferredSize(new Dimension(fieldWidth, categoriaField.getPreferredSize().height));
        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(categoriaLabel, constraints);
        constraints.gridx = 1;
        panel.add(categoriaField, constraints);

        // JComboBox para selecionar o status
        JLabel statusLabel = new JLabel("Status:");
        String[] status = {" ","Planejada", "Cancelada", "Concluída"};
        statusField = new JComboBox<>(status);
        statusField.setPreferredSize(new Dimension(fieldWidth, statusField.getPreferredSize().height));
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(statusLabel, constraints);
        constraints.gridx = 1;
        panel.add(statusField, constraints);

        // Pesquisar Button
        pesquisarButton = new JButton("Pesquisar");
        pesquisarButton.setFont(new Font("Arial", Font.BOLD, 14));
        pesquisarButton.setPreferredSize(new Dimension(250, 40));
        pesquisarButton.setBackground(Color.BLUE);
        pesquisarButton.setForeground(Color.WHITE);
        pesquisarButton.setFocusPainted(false);
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2; // Set gridwidth for this field
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(pesquisarButton, constraints);
        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Realizar a pesquisa com base nos valores dos campos
                String mesSelecionado = (String) mesField.getSelectedItem();
                int mes_int = mesesMap.get(mesSelecionado);
                String mes = mesesMap.get(mesSelecionado).toString(); // Converter para uma string

                String dia = diaField.getText();
                String ano = anoField.getText();
                String hora = horaField.getText();
                String titulo = tituloField.getText();
                String descricao = descricaoField.getText();
                String categoria = (String) categoriaField.getSelectedItem();
                String status = (String) statusField.getSelectedItem();

                // Verificar se os campos são válidos
                if (mes_int == 0) {
                    mes = " ";
                }
                if (!dia.isEmpty()) {
                    int diaInt = Integer.parseInt(dia);
                    if (diaInt < 0 || diaInt > 31) {
                        JOptionPane.showMessageDialog(null, "Digite um dia válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Verificar se a hora segue o formato "HH:mm"
                if (!hora.isEmpty() && !hora.matches("([0-1]?[0-9]|2[0-3]):[0-5][0-9]")) {
                    JOptionPane.showMessageDialog(null, "Digite uma hora válida no formato HH:mm.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                List<AppointmentTask> compromissosEncontrados = AppointmentTaskDAO.buscarCompromissos( dia,  mes,  ano,  hora,  titulo,  descricao,  categoria,  status);

                ListaBuscaAvancada listaBuscaAvancada = new ListaBuscaAvancada(compromissosEncontrados);
                listaBuscaAvancada.setVisible(true);
            }
        });

        add(panel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConsultaAvancada window = new ConsultaAvancada();
                window.setVisible(true);
            }
        });
    }
}
