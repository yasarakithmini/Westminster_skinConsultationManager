import javax.swing.*;

public class DoctorAvailability  extends  JFrame{
    //Declaring swing components
    JLabel heading;
    JTable searchResults;
    JScrollPane tableSP;

    //Constructor of the JFrame
    public DoctorAvailability(WestminsterSkinConsultationManager manager,String name){
        //Heading of the GUI
        heading = new JLabel();
        heading.setText("-Booked Times of the Doctor-");
        heading.setBounds(350,20,250,30);

        //Table containing booked details
        String [] columnHeading = {"Patient Name","Starting Time", "Ending Time"}; //Holds column headings of the table
        String [][] rows = new String[manager.getConsultations().size()][3]; //Holds data of the table
        doctorsAvailability(manager,name,rows);
        searchResults = new JTable(rows,columnHeading);
        searchResults.setDefaultEditor(Object.class,null);
        tableSP = new JScrollPane(searchResults);
        tableSP.setBounds(40,60,740,400);

        add(heading);
        add(tableSP);
        setSize(800,500);
        setTitle("Search Results");
        setLayout(null);
        setVisible(true);
    }

    //Display the Doctors availability
    public void doctorsAvailability(WestminsterSkinConsultationManager skinConsultationManager, String doctorName,String[][] rows) {
        int i = 0;
        for (Consultation temp : skinConsultationManager.getConsultations()){
            if(temp.getDoctor().getName().equals(doctorName)){
                rows[i][0] = temp.getPatient().getName();
                rows[i][1] = temp.getStartingDateTime().toString();
                rows[i][2] = temp.getEndingDateTime().toString();
                i++;
                break;
            }
        }
    }
}
