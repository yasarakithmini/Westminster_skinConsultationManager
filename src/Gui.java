import javax.crypto.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Gui extends JFrame{
    JLabel heading;
    JLabel tableHeading;
    JTable doctorTable;
    JScrollPane sp;
    JButton alphButton;
    JButton checkAvailabilityButton;
    JLabel headingConsul;
    JTextField patientName;
    JTextField patientLName;
    JTextField patientDOB;
    JTextField patientMobile;
    JTextField date;
    JTextField patientId;
    JTextField startingTime;
    JTextField endingTime;
    JButton uploadImage;
    JLabel picLabel;
    JTextArea note;
    JButton addConsultation;
    JButton viewConsultation;

    //Constructor of the JFrame
    public Gui(WestminsterSkinConsultationManager skinConsultationManager) throws NoSuchAlgorithmException {
        setTitle("Westminster Skin Consultation Manager"); //set the title of the GUI
        getContentPane().setBackground(Color.white);
        //Heading of the GUI
        heading = new JLabel();
        heading.setText("------WELCOME TO WESTMINSTER SKIN CONSULTATION MANAGER GUI------");
        heading.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        heading.setBounds(90, 0, 700, 30);

        //Table heading
        tableHeading = new JLabel();
        tableHeading.setText("Available doctors");
        tableHeading.setBounds(350, 25, 600, 30);

        //Table with details of all the doctors
        //Holds column headings of the table
        String[] columns = {"First Name", "Last Name", "Date of Birth", "Mobile Number", "Licence Number", "Specialization"};
        //Holds data of the table
        String[][] rows = new String[skinConsultationManager.getDoctors().size()][6];
        guiTableData(rows, skinConsultationManager);
        doctorTable = new JTable(rows, columns);
        doctorTable.setDefaultEditor(Object.class, null); //makes the table uneditable
        sp = new JScrollPane(doctorTable);
        sp.setBounds(25, 50, 750, 150);

        //Button to sort the above table in ascending order regarding achieved points
        alphButton = new JButton("Sort By Alphabetical Order");
        alphButton.setBounds(25, 210, 365, 50);
        //adds functionality to the button
        alphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Collections.sort(skinConsultationManager.getDoctors(), new FirstNameComparator()); //Alphabetical order
                guiTableData(rows, skinConsultationManager);
                doctorTable.repaint();
            }
        });

        //Button to display doctor appointments
        checkAvailabilityButton = new JButton("Check the Availability of the doctor");
        checkAvailabilityButton.setBounds(405,210,375,50);
        //Adds functionality to the button
        checkAvailabilityButton.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = doctorTable.getSelectedRow();
                try{
                    String name = doctorTable.getModel().getValueAt(row, 0).toString();
                    new DoctorAvailability(skinConsultationManager,name); //Opens another window
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("------Please select a doctor!------");
                }
            }
        });

        //Text fields to fill the form to add a consultation
        headingConsul = new JLabel("Request an Appointment for the Doctor :");
        headingConsul.setBounds(25,280,375,30);
        patientName = new JTextField("Enter Patient's First Name Here");
        patientName.setBounds(25,310,375,30);
        patientName.setBackground(Color.lightGray);
        patientName.setOpaque(true);
        patientLName = new JTextField("Enter Patient's Last Name Here");
        patientLName.setBounds(405,310,375,30);
        patientLName.setBackground(Color.lightGray);
        patientLName.setOpaque(true);
        patientDOB = new JTextField("Enter Patient's Date of Birth Here");
        patientDOB.setBounds(25,350,375,30);
        patientDOB.setBackground(Color.lightGray);
        patientDOB.setOpaque(true);
        patientMobile = new JTextField("Enter Patient's Mobile No Here");
        patientMobile.setBounds(405,350,375,30);
        patientMobile.setBackground(Color.lightGray);
        patientMobile.setOpaque(true);
        date = new JTextField("Enter the Date Here (dd-MM-yyyy)");
        date.setBounds(25,390,375,30);
        date.setBackground(Color.lightGray);
        date.setOpaque(true);
        patientId = new JTextField("Enter Patient's ID Here");
        patientId.setBounds(405,390,375,30);
        patientId.setBackground(Color.lightGray);
        patientId.setOpaque(true);
        startingTime = new JTextField("Enter Starting Time Here (HH:mm:ss)");
        startingTime.setBounds(25,430,375,30);
        startingTime.setBackground(Color.lightGray);
        startingTime.setOpaque(true);
        endingTime = new JTextField("Enter Ending Time Here (HH:mm:ss)");
        endingTime.setBounds(405,430,375,30);
        endingTime.setBackground(Color.lightGray);
        endingTime.setOpaque(true);
        picLabel = new JLabel();
        picLabel.setBounds(405,470,375,200);
        note = new JTextArea(" Enter Additional Notes Here");
        note.setBounds(25,470,375,140);
        note.setBackground(Color.lightGray);
        note.setOpaque(true);
        //Button to upload a image
        uploadImage = new JButton("Upload Image");
        uploadImage.setBounds(100,620,150,50);
        uploadImage.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(getParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        //Opens the file chooser to choose a file
                        File file = fileChooser.getSelectedFile();
                        BufferedImage picture = ImageIO.read(file);
                        //Resize the image
                        Image image = picture.getScaledInstance(375, 200, Image.SCALE_DEFAULT);
                        //Set the image to a label in the gui
                        picLabel.setIcon(new ImageIcon(image));
                        File outputFile = new File(patientName.getText()+".jpg");
                        //Save the image
                        ImageIO.write(picture, "jpg", outputFile);
                        add(picLabel);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        JOptionPane.showMessageDialog(null, "ERROR");
                    }
                }
            }
        });

        //Button add a consultation
        addConsultation = new JButton("Add a Consultation");
        addConsultation.setBounds(25,670,375,50);
        addConsultation.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = doctorTable.getSelectedRow();
                try{
                    //Adds a consultation
                    String docName = doctorTable.getModel().getValueAt(row, 0).toString();
                    addConsultation(docName, patientName.getText(), patientLName.getText(), patientDOB.getText(), patientMobile.getText(), patientId.getText(), date.getText(),
                            startingTime.getText(), endingTime.getText(), note.getText(), skinConsultationManager);
                    picLabel.setIcon(null);
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("------Please select a doctor!------");
                }
            }
        });

        viewConsultation = new JButton("View All Consultations");
        viewConsultation.setBounds(405,670,375,50);
        viewConsultation.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new ViewAllConsultations(skinConsultationManager);
            }
        });

        //Adds swing components to the interface
        add(heading);
        add(tableHeading);
        add(sp);
        add(alphButton);
        add(checkAvailabilityButton);
        add(headingConsul);
        add(patientName);
        add(patientLName);
        add(patientDOB);
        add(patientMobile);
        add(date);
        add(patientId);
        add(startingTime);
        add(endingTime);
        add(picLabel);
        add(note);
        add(uploadImage);
        add(addConsultation);
        add(viewConsultation);

        setSize(800,800);
        setLayout(null);
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stop the programme when the close button is clicked but haven't used
    }

    //fills the 2D array to contain data of the table
    public void guiTableData(String[][] rows,WestminsterSkinConsultationManager skinConsultationManager) {
        //Initialise values to the 2D array
        int i=0;
        for (Doctor temp : skinConsultationManager.getDoctors()){
            rows [i][0] = temp.getName();
            rows [i][1] = temp.getSurname();
            rows [i][2] = temp.getDateOfBirth();
            rows [i][3] = temp.getMobile();
            rows [i][4] = Integer.toString(temp.getLicenceNum());
            rows [i][5] = temp.getSpecialization();
            i++;
        }
    }

    //Method to create Patient object, check doctor's availability, and create Consultation object
    public void addConsultation(String doctorName, String patientFName, String patientLName, String patientDOB, String patientMobile, String patientId,
                                String date, String startingTime, String endingTime, String addedNote, WestminsterSkinConsultationManager skinConsultationManager) {
        boolean proceed = true;
        Doctor docTemp = null;
        Consultation consultation = null;
        try {

            Date startDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date + " " + startingTime);
            Date endDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date + " " + endingTime);
            int cost = calculateCost(skinConsultationManager, startingTime, endingTime, patientId);
            Patient patient = new Patient(patientFName, patientLName, patientDOB, patientMobile, Integer.parseInt(patientId));
            docTemp = assignDoctor(skinConsultationManager,doctorName,startDateTime,endDateTime);


            // Creating object of Cipher
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");
            // Creating byte array to store string
            byte[] text = addedNote.getBytes("UTF8");
            // Encrypting text
            desCipher.init(Cipher.ENCRYPT_MODE, skinConsultationManager.myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);

            if(docTemp != null) {
                consultation = new Consultation(docTemp, patient, startDateTime, endDateTime, cost, textEncrypted);
            }else{
                proceed = false;
                System.out.println("----Cant find any available doctors for the selected time!----");
            }

        }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e){
            System.out.println("---Encryption Failed---");
        }

        catch (RuntimeException | ParseException e){
            proceed = false;
            System.out.println("----Error Occurred! Please Check Entered Values and TRY AGAIN!----");
        }
        if(proceed){
            skinConsultationManager.setConsultations(consultation);
        }

    }

    //Assigns the selected doctor or a random doctor after checking the availability
    public Doctor assignDoctor(WestminsterSkinConsultationManager skinConsultationManager,String doctorName, Date startingTime, Date endingDate){
        boolean available = true;
        Doctor doctor = null;

        //Check whether selected doctor is available
        for(Consultation temp : skinConsultationManager.getConsultations()){
            if((temp.getDoctor().getName().equals(doctorName)) && (temp.getStartingDateTime().compareTo(startingTime) <= 0) && (temp.getEndingDateTime().compareTo(startingTime) > 0)){
                available = false;
                break;
            }
        }


        //Assign the selected doctor if available
        if(available){
            for(Doctor temp : skinConsultationManager.getDoctors()){
                if(temp.getName().equals(doctorName)){
                    doctor = temp;
                    break;
                }
            }
        }
        else{
            //Assign a random doctor
            ArrayList<Doctor> doctorsCopy = new ArrayList<>();
            doctorsCopy.addAll(skinConsultationManager.getDoctors());
            while(!available && (doctorsCopy.size() != 0)){
                boolean notAvailable = false;
                Collections.shuffle(skinConsultationManager.getDoctors()); //Shuffle the doctors array to get a random doctor
                Doctor randomDoc = skinConsultationManager.getDoctors().get(0);
                for(Consultation temp : skinConsultationManager.getConsultations()){
                    if((temp.getDoctor().getName().equals(randomDoc.getName())) && (temp.getStartingDateTime().compareTo(startingTime) <= 0) && (temp.getEndingDateTime().compareTo(startingTime) > 0)){
                        notAvailable = true;
                        doctorsCopy.remove(randomDoc);
                        break;
                    }
                }
                if (!notAvailable){
                    available = true;
                    doctor = skinConsultationManager.getDoctors().get(0);
                }
            }
        }
        return doctor;
    }

    //Calculate the cost for the consultation
    public int calculateCost(WestminsterSkinConsultationManager skinConsultationManager,String startingTime, String endingTime, String patientId) {
        int cost = 0;
        int startingHour = Integer.parseInt(startingTime.substring(0,2));
        int endingHour = Integer.parseInt(endingTime.substring(0,2));
        int startingMinute = Integer.parseInt(startingTime.substring(3,5));
        int endingMinute = Integer.parseInt(endingTime.substring(3,5));

        //Check whether patient had created a consultation before
        for (Consultation temp : skinConsultationManager.getConsultations()){
            if (String.valueOf(temp.getPatient().getPatientId()).equals(patientId)) {
                cost += 10;
                break;
            }
        }

        if ((startingHour == endingHour) && (startingMinute < endingMinute)){
            cost += 15;
        }
        else if(startingHour < endingHour){
            cost += 15;
            cost = cost * (endingHour - startingHour);
        }
        else{
            cost = 0;
        }
        return cost;
    }
}