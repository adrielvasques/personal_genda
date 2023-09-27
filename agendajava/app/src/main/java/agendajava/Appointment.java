package agendajava;

public class Appointment {
    //attributes
    String day, description;
    
    //constructors
    public Appointment(String tarih, String aciklama) {  //creating appointment with parameters
        day = tarih;
        description = aciklama;  
    }
    
    public Appointment() {  //creating empty appointment
        day = "";
        description = "";
    }
    
    //methods
    public String getAppointment() {  //method for getting the appointment details
        String appointment = day + ":" + description;  //creates an appropriate display of the appointment
        return appointment;
    }
    
    public void setAppointment(String oldappointment) {  //method for getting the old appointment data
        if (oldappointment != null && oldappointment.contains(":")) {
            int colonIndex = oldappointment.indexOf(":");
            day = oldappointment.substring(0, colonIndex);
            description = oldappointment.substring(colonIndex + 1);
            description = description.replace(":", "");
        } else {
            // Handle the case where oldappointment is null or does not contain a colon
            System.err.println("Invalid appointment string: " + oldappointment);
        }
    }
}
