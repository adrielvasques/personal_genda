package agendajava;
import agendajava.AppointmentDAO.AppointmentTaskDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Days {
    // attributes
    int today; // the number of days in that month
    JButton selectedButton; // Variável para rastrear o botão selecionado

    // constructor
    public Days(int daycount) {
        today = daycount;
    }

    // methods
    public int getCount() // gets the number of days
    {
        return today;
    }

    public JButton createBox(String label, int year, int month) {
        String dataSQL = null; // Declare a variável fora do bloco try

        try {
            int day = Integer.parseInt(label);
            dataSQL = String.format("%04d-%02d-%02d", year, month, day);
            // Agora você pode usar 'dataSQL' como a data no formato SQL
            //System.out.println(dataSQL);
        } catch (NumberFormatException e) {
            // Trate o caso em que 'label' não é um número válido
        }

        JButton button = new JButton(label);// creates a button with a label on it

        // Verifica se o dia possui compromissos e define a cor do botão
        boolean possuiCompromissos = AppointmentTaskDAO.possuiCompromissosNaData(dataSQL);
        if (possuiCompromissos) {
            button.setBackground(Color.RED); // Define a cor para vermelho se houver compromissos
        } else {
            button.setBackground(Color.WHITE); // Define a cor para branco se não houver compromissos
        }

        button.setOpaque(true);
        button.setBorderPainted(true);

        class ButtonListener implements ActionListener // creates a subclass for the action
        {
            public void actionPerformed(ActionEvent event) // method for changing the background color
            {
                String text = button.getText();// checks if the button has a day or not
                if (!(text.equals(""))) {
                    if (selectedButton != null && selectedButton != button) {
                        selectedButton.setBackground(Color.WHITE); // Retorna o botão anterior para branco
                    }
                    if (button.getBackground() == Color.WHITE) {
                        if (button.getBackground() != Color.RED) {
                            button.setBackground(Color.BLUE);// the statement for changing the background to blue
                        }
                        selectedButton = button; // Define o botão atualmente selecionado
                    } else {
                        if (button.getBackground() == Color.RED) {
                            button.setBackground(Color.RED);// the statement for changing the background to blue
                      }
                    if (button.getBackground() == Color.BLUE) {
                            button.setBackground(Color.WHITE);// the statement for changing the background to blue
                      }
                   if (button.getBackground() == Color.BLUE) {
                            button.setBackground(Color.BLUE);// the statement for changing the background to blue
                      }
                        selectedButton = null; // Nenhum botão selecionado
                    }
                } else {
                    if (selectedButton != null) {
                        selectedButton.setBackground(Color.WHITE); // Retorna o botão anterior para branco
                        selectedButton = null; // Nenhum botão selecionado
                    }
                }


                button.setOpaque(true);
                button.setBorderPainted(true);
            }
        }
        

        ButtonListener listener = new ButtonListener();// creates an action listener
        button.addActionListener(listener);// sets the actionlistener to the button
        return button;// creates the button
    }
}
