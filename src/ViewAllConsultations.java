import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.*;

public class ViewAllConsultations extends JFrame{
    //Declaring swing components
    JLabel heading;
    JTable searchResults;
    JScrollPane tableSP;
    JButton viewDetailsButton;
    JLabel patientName;
    JLabel patientLName;
    JLabel patientDOB;
    JLabel patientMobile;
    JLabel patientId;
    JLabel startingTime;
    JLabel endingTime;
    JLabel doctor;
    JLabel note;
    JLabel picLabel;
    JLabel cost;

    //Constructor of the JFrame
    public ViewAllConsultations(WestminsterSkinConsultationManager manager){
        //Heading of the GUI
        heading = new JLabel();
        heading.setText("-All Consultations-");
        heading.setBounds(470,20,250,30);

        //Table containing consultation details
        String [] columnHeading = {"Patient","Doctor","Starting Time", "Ending Time"}; //Holds column headings of the table
        String [][] rows = new String[manager.getConsultations().size()][4]; //Holds data of the table
        view(manager,rows);
        searchResults = new JTable(rows,columnHeading);
        searchResults.setDefaultEditor(Object.class,null);
        tableSP = new JScrollPane(searchResults);
        tableSP.setBounds(25, 50, 900, 150);

        //Components to display additional details
        patientName = new JLabel();
        patientName.setBounds(25,320,375,30);
        patientLName = new JLabel();
        patientLName.setBounds(435,320,375,30);
        patientDOB = new JLabel();
        patientDOB.setBounds(25,360,375,30);
        patientMobile = new JLabel();
        patientMobile.setBounds(435,360,375,30);
        doctor = new JLabel();
        doctor.setBounds(25,400,375,30);
        patientId = new JLabel();
        patientId.setBounds(435,400,375,30);
        startingTime = new JLabel();
        startingTime.setBounds(25,440,375,30);
        endingTime = new JLabel();
        endingTime.setBounds(435,440,375,30);
        note = new JLabel();
        note.setBounds(25,480,375,200);
        picLabel = new JLabel();
        picLabel.setBounds(435,480,375,200);
        cost = new JLabel();
        cost.setBounds(775,700,150,40);
        cost.setFont(new Font(Font.DIALOG,Font.BOLD,15));

        //Display all the details of the consultation
        viewDetailsButton = new JButton("View Details");
        viewDetailsButton.setBounds(775,230,150,70);
        viewDetailsButton.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = searchResults.getSelectedRow();
                try{
                    picLabel.setIcon(null);

                    String name = searchResults.getModel().getValueAt(row, 0).toString();
                    String dateTime = searchResults.getModel().getValueAt(row, 2).toString();
                    Consultation details = getConsultation(manager,name, dateTime);
                    patientName.setText("Patient's First Name: "+details.getPatient().getName());
                    patientLName.setText("Patient's Last Name: "+details.getPatient().getSurname());
                    patientDOB.setText("Patient's Date of Birth: "+details.getPatient().getDateOfBirth());
                    patientMobile.setText("Patient's Mobile No: "+details.getPatient().getMobile());
                    patientId.setText("Patient's ID No: "+details.getPatient().getPatientId());
                    startingTime.setText("Consultation Starting Time: "+details.getStartingDateTime().toString());
                    endingTime.setText("Consultation Ending Time: "+details.getEndingDateTime().toString());
                    doctor.setText("Name of the Doctor: "+details.getDoctor().getName());
                    cost.setText("Total Cost: "+details.getCost());

                    //Load an image if uploaded previously
                    File file = new File(details.getPatient().getName()+".jpg");
                    BufferedImage picture = new BufferedImage(375, 200, BufferedImage.TYPE_INT_ARGB);
                    picture = ImageIO.read(file);

                    //Set the image
                    Image image = picture.getScaledInstance(375, 200, Image.SCALE_DEFAULT);
                    picLabel.setIcon(new ImageIcon(image));

                    // Decrypting text
                    Cipher desCipher;
                    desCipher = Cipher.getInstance("DES");
                    desCipher.init(Cipher.DECRYPT_MODE, manager.myDesKey);
                    byte[] textDecrypted = desCipher.doFinal(details.getNote());

                    note.setText("Additional Note: "+new String(textDecrypted));
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("------Please select a Consultation!------");
                }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
                    System.out.println("---Decryption Failed---");
                } catch (IOException ignored) {}
            }
        });

        add(heading);
        add(tableSP);
        add(viewDetailsButton);
        add(patientName);
        add(patientLName);
        add(patientDOB);
        add(patientMobile);
        add(patientId);
        add(startingTime);
        add(endingTime);
        add(note);
        add(doctor);
        add(picLabel);
        add(cost);
        setSize(950,800);
        setTitle("All Consultations");
        setLayout(null);
        setVisible(true);
    }

    //Display the Doctors availability
    public void view(WestminsterSkinConsultationManager skinConsultationManager, String[][] rows) {
        int i = 0;
        for (Consultation temp : skinConsultationManager.getConsultations()){
            rows[i][0] = temp.getPatient().getName();
            rows[i][1] = temp.getDoctor().getName();
            rows[i][2] = temp.getStartingDateTime().toString();
            rows[i][3] = temp.getEndingDateTime().toString();
            i++;
        }
    }

    //Get the relevant consultation
    public Consultation getConsultation(WestminsterSkinConsultationManager skinConsultationManager, String patientName, String startingDateTime){
        for (Consultation temp : skinConsultationManager.getConsultations()){
            if((temp.getStartingDateTime().toString().equals(startingDateTime)) && temp.getPatient().getName().equals(patientName)){
                return temp;
            }
        }
        return null;
    }
}
