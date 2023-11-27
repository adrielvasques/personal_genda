package agendajava;
import javax.swing.*;
import agendajava.FramePanel.ButtonListener4;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.*;
public class FramePanel extends JFrame {
  //Panels
  JPanel monthsNav = new JPanel(); //panel for displaying the month, year and two position buttons
  JPanel days = new JPanel(); //panel for the days of the month
  JPanel currentDate = new JPanel(); //panel for showing the current date
  JButton consultButton = new JButton("Consultas Avançadas");

  //________
  //attitudes
  int sira = 0; //the order of the month in the meses array
  int year = 2017; //current year
  String month = ""; //name of the month
  int dayofweek = 0; //the name of the current day
  int nextFirstday = 0; //first day of the next month
  int bugun = 0; //today
  int previousDay = 0; //the name of the last day of the previous month
  int nextMonth, nextYear; //appointment month and year
  String appointmentView = ""; //appointment list
  String appointmentDate = ""; //appointment date
  int appmonth = 0;
  int appyear = 0;
  int appday = 0;
  //______
  //Arrays
  int newdays[] = new int[7]; //the changing order of the days of the week
  String[] meses = {
    "Janeiro",
    "Fevereiro",
    "Marco",
    "Abril",
    "Maio",
    "Junho",
    "Julho",
    "Agosto",
    "Setembro",
    "Outubro",
    "Novembro",
    "Dezembro"
  }; //Array de meses
  String[] week = {
    "   Domingo",
    "   Segunda",
    "   Terça",
    "   Quarta",
    "   Quinta",
    "   Sexta",
    "   Sabado"
  }; //array for the days of the week
  //_________
  Calendar appointmentDay = new GregorianCalendar();
  ArrayList < Days > dates = new ArrayList < Days > (); //the arraylist of the monthsNav and their total days
  ArrayList < JRadioButton > silinecekler = new ArrayList < JRadioButton > (); //appointments that will be erased
  //Buttons
  JButton next = new JButton(">>"); //next button
  JButton previous = new JButton("<<"); //previous button
  JButton button; //buttons for displaying days
  JButton label = new JButton(); //label for showing the month and year
  ButtonGroup group = new ButtonGroup(); //group for radio buttons
  //______
  ButtonListener4 listener4 = new ButtonListener4(this);
  JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
  private static ConsultaAvancada consultaPersonalizada = null; // Variável para rastrear a janela

  public FramePanel() //adds all the methods to the constructer
  {
    this.setCurrentDate();
    this.setMonths();
    this.loadMonth(true);
    this.add(monthsNav, BorderLayout.NORTH);
    this.add(days, BorderLayout.CENTER);
    this.add(currentDate, BorderLayout.SOUTH);
    buttonPanel.add(consultButton);

  }

  public void setCurrentDate() //gets the current day
  {
    Calendar cal = Calendar.getInstance(); //gets the time and the date from the computer
    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //formats the day
    Date day = new Date();
    String date = dateFormat.format(day); //gets the current day
    year = Integer.parseInt(date.substring(6, 10)); //gets the current year
    sira = Integer.parseInt(date.substring(3, 5)) - 1; //gets the current month 
    month = meses[sira]; ////gets the current month and turns it into a name
    String gun = date.substring(0, 2); //gets the day
    bugun = Integer.parseInt(gun); //turns the day into an integer
    JLabel current = new JLabel("Hoje é " + gun + " " + month + " " + year);


    currentDate.setLayout(new GridLayout(2, 2));
    consultButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (consultaPersonalizada == null || !consultaPersonalizada.isVisible()) {
          consultaPersonalizada = new ConsultaAvancada();
          consultaPersonalizada.setResizable(false); // Impede redimensionamento
          consultaPersonalizada.setVisible(true);
      }
      }
    });

    current.setHorizontalAlignment(SwingConstants.CENTER);
    currentDate.add(current); //adds the day into the panel
    currentDate.add(buttonPanel);
    appmonth = sira + 1; //variable that will be used for limiting the appointment times
    appyear = year; //variable that will be used for limitin the appointment times
    appday = bugun; //variable that will be used for limitin the appointment times
  }

  public void setMonths() //enters each month with their number of days
  {
    Days january = new Days(31);
    dates.add(january);
    Days february = new Days(28);
    dates.add(february);
    Days march = new Days(31);
    dates.add(march);
    Days april = new Days(30);
    dates.add(april);
    Days may = new Days(31);
    dates.add(may);
    Days june = new Days(30);
    dates.add(june);
    Days july = new Days(31);
    dates.add(july);
    Days august = new Days(31);
    dates.add(august);
    Days september = new Days(30);
    dates.add(september);
    Days october = new Days(31);
    dates.add(october);
    Days november = new Days(30);
    dates.add(november);
    Days december = new Days(31);
    dates.add(december);
  }

  //the major method for defining the days of the month
  public void loadMonth(Boolean initialLoad) {

    monthsNav.setLayout(new GridLayout(1, 3));
    int selectedYear = year;
    int selectedMonth = sira;
    Calendar cal = Calendar.getInstance(); //gets the time and the date from the computer
    cal.set(Calendar.YEAR, selectedYear); // Ano
    cal.set(Calendar.MONTH, selectedMonth); // Mês (Janeiro é 0, Fevereiro é 1, ..., Dezembro é 11)
    cal.set(Calendar.DAY_OF_MONTH, 1); // Dia do mês
    cal.set(Calendar.HOUR_OF_DAY, 14); // Hora do dia (formato 24 horas)
    dayofweek = cal.get(Calendar.DAY_OF_WEEK);
    bugun = cal.get(Calendar.DAY_OF_MONTH);
    String mes = meses[sira]; //gets the name of the month

    label.setText(" " + mes + " " + selectedYear); //sets the text for the displaying month and year
    String day = "";
    days.setLayout(new GridLayout(6, 7));
    int counter = 0; //counter for understanding how many times if the newarray loops run
    int arraycounter = 0; //counter for the sequence of the newarray list
    int firstday = 0; //first day of the month
    //setting the first day
    boolean found2 = true;

    while (found2) {
      bugun = bugun - 7; //decreases the date of the current day until the first week of the month
      if (bugun < 0) //when it reaches the first month
      {
        bugun += 7; //fix the number
        firstday = dayofweek - bugun; //substract the day from the order of the date in order to find which date is the first day of the month 
        if (firstday < 0) //fixes the value if it fall under 0
        {
          firstday += 7;
        }
        found2 = false;
      }
    }

    for (int e = firstday; e <= 7; e++) //loop for creating the new order of dates for that spesific month
    {
      if (e == 7) //fixes the value after saturday
      {
        e = 0;
      }
      newdays[arraycounter] = e; //sets the new order of dates
      arraycounter++;
      JLabel l = new JLabel(week[e]); //prints the name of the dates
      days.add(l); //adds the label to the panel

      counter++;
      if (counter == 7) //if loop runs for seven times it stops
      {
        break;
      }
    }

    int limit = dates.get(sira).getCount(); //gets the maximum number of days
    for (int i = 1; i <= 35; i++) //the loop runs and creates the days of the month 
    {
      if ((dates.get(sira).getCount() == 28) && ((year - 1988) % 4 == 0)) //special case for february for once in each 4 years
      {
        limit = 29;
      }
      day = Integer.toString(i);
      if (i == limit + 1) //when the days of the month ends it stores the value for the next month
      {
        int copyi = i;
        boolean found = true;
        while (found) //same procedure of finding the first day of the next month
        {
          copyi = copyi - 7; //decreases the number of days by 7 for going back by one whole week
          if (copyi < 0) {
            copyi += 7; //fixes the value if the number is less than zero
            nextFirstday = newdays[copyi]; //sets the firstday of the next month
            found = false;
          }
        }
      }
      if (i >= limit + 1) //after the maximum day number, rest of the table will be filled with emphty boxes
      {
        day = "";
      }
      button = dates.get(sira).createBox(day, year, sira + 1); //creates the button
      if (!(button.getText().equals(""))) //checks if the button is has an actual day
      {
        if ((year > appyear) || ((year == appyear) && (sira + 1 >= appmonth)) || ((year == appyear) && (sira + 1 >= appmonth) && (i > appday))) //checks if the day that the button resembles is in the correct range for an appointment
        {
          button.addActionListener(listener4); //adds the action listener
          button.putClientProperty("date", year + "-" + (sira + 1) + "-" + i);
        }
      }
      days.add(button); //adds the button to the panel
    }
    previousDay = newdays[6]; //sets the last day of the previous month

    //action of the next button

    class ButtonListener1 implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        sira++; //order of the number increases by one
        if (sira == 12) //increases the year after december
        {
          sira = 0;
          year++;
        }
        month = meses[sira]; //resets the month
        label.setText(" " + month + " " + year); //resets the calendar text
        days.removeAll(); //cleans the day panel
        days.setLayout(new GridLayout(6, 7));
        int counter = 0; //counter for understanding how many times does the newarray list loop run
        int arraycounter = 0; //counter for setting the sequence of the newarray list
        for (int e = nextFirstday - 1; e <= 7; e++) //creates the new array of dates for that specific month
        {

          if (e == 7) //fixes the value after saturday
          {
            e = 0;
          }
          if (e == -1) //fixes the value after sunday
          {
            e = 6;
          }
          newdays[arraycounter] = e; //adds the names to the array list
          arraycounter++;
          JLabel l = new JLabel(week[e]); //prints the name of the dates
          days.add(l); //adds the label to the panel
          counter++;
          if (counter == 7) {
            break;
          }
        }
        String day = "";
        int limit = dates.get(sira).getCount(); //gets the maximum number of days for that specific month
        for (int i = 1; i <= 35; i++) //creates the days of the month
        {
          if ((dates.get(sira).getCount() == 28) && ((year - 2012) % 4 == 0)) //special case for february for once in each 4 years
          {
            limit = 29;
          }
          day = Integer.toString(i);
          if (i == limit + 1) //finds the next first day of the next month
          {
            int copyi = i;
            boolean found = true;
            while (found) {
              copyi = copyi - 7; //goes back by one whole week to find the same day on the first week
              if (copyi < 0) {
                copyi += 7; //fixes the value if it falls under 0
                nextFirstday = newdays[copyi]; //gets the value of first day of the next month
                found = false;
              }
            }
          }
          if (i >= limit + 1) //fills the rest of the table with empthy boxes
          {
            day = "";
          }
          button = dates.get(sira).createBox(day, year, sira + 1);; //creates the button
          //print day
          /*System.out.println(day);
          System.out.println(year);
          System.out.println(sira+1);*/
          if (!(button.getText().equals(""))) //checks if the button has an actual date
          {
            if ((year > appyear) || ((year == appyear) && (sira + 1 >= appmonth)) || ((year == appyear) && (sira + 1 >= appmonth) && (i > appday))) //checks if the day that the button resembles is in the correct range for an appointment
            {
              button.addActionListener(listener4); //adds the action listener
              button.putClientProperty("date", year + "-" + (sira + 1) + "-" + i);

            }
          }
          days.add(button); //adds the button to the panel
        }
        previousDay = newdays[6]; //sets the last day of the previous month
      }
    }

    //action of the previous button

    class ButtonListener2 implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        sira--; //order of the monthsNav decreased by one
        if (sira == -1) //changes the year after january
        {
          sira = 11;
          year--;
        }
        String month = meses[sira]; //finds the last month
        label.setText(" " + month + " " + year); //resets the calendar text
        days.removeAll(); //cleans the days
        int limit = dates.get(sira).getCount(); //gets the maximum number of days of that month
        if ((dates.get(sira).getCount() == 28) && ((year - 2012) % 4 == 0)) //special case for february
        {
          limit = 29;
        }
        int limit2 = limit; //copies the day number for manipulation
        //setting the first day
        boolean found2 = true;
        while (found2) //finds the first day of the past month with the same logic in next button
        {
          limit2 = limit2 - 7;
          if (limit2 < 0) {
            limit2 += 7;
            nextFirstday = previousDay - limit2;
            if (nextFirstday < 0) {
              nextFirstday += 7;
            }
            found2 = false;
          }
        }
        //____________________
        days.setLayout(new GridLayout(6, 7));
        int counter = 0; //counter for checking how many times does the newarray loop runs
        int arraycounter = 0; //counter for the sequence of the newarray list
        for (int e = nextFirstday + 1; e <= 7; e++) //creates the new sequance of dates
        {
          if (e == 7) {
            e = 0;
          }
          if (e == -1) {
            e = 6;
          }
          newdays[arraycounter] = e; //adds the days to the array
          arraycounter++;
          JLabel l = new JLabel(week[e]); //prints the dates
          days.add(l);
          counter++;
          if (counter == 7) {
            break;
          }
        }
        String day = "";

        for (int i = 1; i <= 35; i++) //recreates the days of the month
        {
          if ((dates.get(sira).getCount() == 28) && ((year - 2012) % 4 == 0)) //special case for february
          {
            limit = 29;
          }
          day = Integer.toString(i);
          if (i == limit + 1) //finds the next first day of the next month with the same logic
          {
            int copyi = i;
            boolean found = true;
            while (found) {
              copyi = copyi - 7; //goes back by one whole week each time to find the day on the first week
              if (copyi < 0) {
                copyi += 7; //fixes the value if it falls below 0
                nextFirstday = newdays[copyi]; //gets the next first day
                found = false;
              }
            }
          }
          if (i >= limit + 1) {
            day = "";
          }
          button = dates.get(sira).createBox(day, year, sira + 1); // creates the button
          /*System.out.println(day);
          System.out.println(year);
          System.out.println(sira+1*9);*/

          if (!(button.getText().equals(""))) //checks if the button actually has a day value
          {
            if ((year > appyear) || ((year == appyear) && (sira + 1 >= appmonth)) || ((year == appyear) && (sira + 1 >= appmonth) && (i > appday))) //checks if the day that the button resembles is in the correct range for an appointment
            {
              button.addActionListener(listener4); //adds the action listener
              button.putClientProperty("date", year + "-" + (sira + 1) + "-" + i);
            }
          }
          days.add(button); //adds the button to the days panel
        }
        previousDay = newdays[6]; //sets the first day of the previous month
      }
    }

    if (initialLoad) {
      ButtonListener1 listener = new ButtonListener1(); //creates listener1
      ButtonListener2 listener2 = new ButtonListener2(); //creates listener2
      next.addActionListener(listener); //adds the listener1 to next button 
      previous.addActionListener(listener2); //adds the listener2 to previous button
    }

    monthsNav.add(previous); //adds the button to the panel
    monthsNav.add(label); //adds the label to the panel
    monthsNav.add(next); //adds the button to the panel
  }

  public void reloadDays() {

    // instancia DayWindow
    this.remove(monthsNav);
    this.remove(days);
    monthsNav = new JPanel();
    days = new JPanel();
    //appointments = new JPanel();
    this.loadMonth(false); // Recria o painel de dias
    this.add(monthsNav, BorderLayout.NORTH);
    this.add(days, BorderLayout.CENTER);
    repaint(); // Redesenha a tela
    revalidate(); // Atualiza o layout

  }

  class ButtonListener4 implements ActionListener {
    FramePanel parent;
    public ButtonListener4(FramePanel parent) {
      this.parent = parent;
    }

    // Lista para rastrear as janelas DayWindow abertas
    private List < DayWindow > dayWindows = new ArrayList < > ();

    public void handleReload() {
      this.parent.reloadDays();
    }

    public void actionPerformed(ActionEvent event) {
      String appointmentDate = (String)((JButton) event.getSource()).getClientProperty("date");

      // Verifique se já existe uma janela DayWindow aberta com a mesma data
      for (DayWindow window: dayWindows) {
        if (window.getData().equals(appointmentDate)) {
          // Janela já aberta, não faça nada
          return;
        }
      }

      // Crie uma nova janela DayWindow
      DayWindow newAppointmentWindow = new DayWindow(appointmentDate, () -> handleReload());
      newAppointmentWindow.setResizable(false); // Não redimensionável
      newAppointmentWindow.setTitle(appointmentDate); // Define o título
      newAppointmentWindow.setLocationRelativeTo(null); // Centraliza na tela
      newAppointmentWindow.setVisible(true); // Torna visível

      // Defina um ouvinte de fechamento para remover a janela da lista quando ela for fechada
      newAppointmentWindow.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
          dayWindows.remove(newAppointmentWindow);
        }
      });

      // Adicione a nova janela à lista de janelas abertas
      dayWindows.add(newAppointmentWindow);
    }
  }
}