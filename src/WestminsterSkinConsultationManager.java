import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    // Generating objects of KeyGenerator & SecretKey
    KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
    SecretKey myDesKey = keygenerator.generateKey();

    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Consultation> consultations = new ArrayList<>();
    private int currentNumOfDoctors = 0;

    Scanner input = new Scanner(System.in);

    public WestminsterSkinConsultationManager() throws NoSuchAlgorithmException {
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(Consultation consultation) {
        this.consultations.add(consultation);
    }

    @Override
    public void addNewDoctor(String fName, String lName, String dateOfBirth, String mobile, int licenseNumber, String specialization) {
        //this method adds a new doctor
        System.out.println("\t\t\t----------------------ADD A NEW DOCTOR-----------------------");
        if(currentNumOfDoctors >= 10) {
            System.out.println("----Process Failed! Exceeding the maximum number of doctors!----");
        }
        else {
            Doctor doctor = new Doctor(fName,lName,dateOfBirth,mobile,licenseNumber,specialization);
            doctors.add(doctor);
            currentNumOfDoctors++;
            System.out.println("----Process Successful!----");
        }
    }

    @Override
    public void deleteDoctor(int licenseNumber) {
        //this method removes a doctor
        boolean isDeleted =  false;
        System.out.println("\t\t\t----------------------REMOVE A DOCTOR-----------------------");
        Doctor toDelete = null;
        for (Doctor tempDoc : doctors){
            if(tempDoc.getLicenceNum() == licenseNumber){
                toDelete = tempDoc;
                isDeleted = true;
                break;
            }
        }
        if(isDeleted){
            doctors.remove(toDelete);
            currentNumOfDoctors--;
            System.out.println("\t\t\tDETAILS");
            System.out.println("\t\t\t\t\tName : " + toDelete.getName() + " " + toDelete.getSurname());
            System.out.println("\t\t\t\t\tDate of birth : " + toDelete.getDateOfBirth());
            System.out.println("\t\t\t\t\tMobile : " + toDelete.getMobile());
            System.out.println("\t\t\t\t\tLicence Number : " + toDelete.getLicenceNum());
            System.out.println("\t\t\t\t\tSpecialization : " + toDelete.getSpecialization() + "\n");
            System.out.println("----------------Doctor deleted successfully!----------------");
        }else{
            System.out.println("-------------Entered doctor licence does not exist-------------");
        }
    }

    @Override
    public void printDoctorsList() {
        //This method displays all the doctors
        int count = 0;
        boolean isEmpty = true;
        ArrayList<Doctor> arrayListCopy = new ArrayList<>();
        //Getting a copy of doctors array list
        arrayListCopy.addAll(doctors);
        System.out.println("\t\t\t------------------------DOCTORS LIST------------------------\n");
        Collections.sort(arrayListCopy,new SurnameComparator());
        for (Doctor temp : arrayListCopy){
            isEmpty = false;
            System.out.println("--------------DOCTOR " + String.format("%2d",count+1) + " ---------------");
            System.out.println("Name : " + temp.getName() + " " + temp.getSurname());
            System.out.println("Date of birth : " + temp.getDateOfBirth());
            System.out.println("Mobile : " + temp.getMobile());
            System.out.println("Licence Number : " + temp.getLicenceNum());
            System.out.println("Specialization : " + temp.getSpecialization() + "\n\n");
            count++;
        }
        if(isEmpty){
            System.out.println("----------------Doctor registry is empty-----------------");
        }
    }

    @Override
    public void saveToFile() {
        //This method saves necessary data to a file (Array lists containing doctors and consultations)
        try {
            File file = new File("SkinConsultation.txt");
            file.createNewFile(); // creates a new file if there isn't a file named as above

            //Output Streams
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(doctors); //write doctors array list to the file
            objOut.writeObject(consultations);  //write consultations array list to the file
            objOut.close();
            fileOut.close();
            System.out.println("------------------DATA SAVED SUCCESSFULLY-------------------");
        }catch (IOException e){
            System.out.println("-----------Error while saving data into the file------------");
        }
    }

    @Override
    public void readFromFile() {
        //This method reads necessary data from a file (Array lists containing doctors and consultations)

        //Input streams
        try {
            FileInputStream fileIn = new FileInputStream("SkinConsultation.txt");
            ObjectInputStream objOut = new ObjectInputStream(fileIn);
            doctors = (ArrayList<Doctor>) objOut.readObject(); //Read doctors array list from the file
            currentNumOfDoctors = doctors.size();
            consultations = (ArrayList<Consultation>) objOut.readObject(); //Read consultation array list from the file
            //input close
            objOut.close();
            fileIn.close();
            System.out.println("-----------------DATA LOADED SUCCESSFULLY-------------------");
        }catch (IOException|ClassNotFoundException e) {
            System.out.println("-------------Error occurred while loading data--------------");
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        WestminsterSkinConsultationManager skinConsultationManager = new WestminsterSkinConsultationManager();
        skinConsultationManager.readFromFile(); //Reads data from the previously saved file
        boolean noExit = true;
        while (noExit) {
            //Display Menu
            System.out.println("\t\t\t----------WELCOME TO WESTMINSTER SKIN CONSULTATION MANAGER---------");
            System.out.println("Enter C to add a new doctor to the database\n" +
                    "Enter R to remove a doctor from the database\n" +
                    "Enter P to print the list of doctors in alphabetical order\n" +
                    "Enter S to save data to a file\n" +
                    "Enter G to GUI of westminster skin consultation manager\n" +
                    "Enter E to quit the application");
            System.out.println("\t\t\t-------------------------------------------------------------------");
            System.out.print("Choice : ");
            String choice = input.next();
            //Runs menu items according to user input
            switch (choice) {
                case "E":
                    noExit = false;
                    System.exit(0); //Close the programme forcibly to close gui
                    break;
                case "C":
                    try {
                        System.out.print("Enter the first name : ");
                        String fName = input.next();
                        System.out.print("Enter the last name : ");
                        String lName = input.next();
                        System.out.print("Enter the date of birth : ");
                        String dateOfBirth = input.next();
                        System.out.print("Enter the mobile number: ");
                        String mobile = input.next();
                        System.out.print("Enter the licence number: ");
                        int licenseNumber = Integer.parseInt(input.next());
                        System.out.print("Enter the specialized field: ");
                        String specialization = input.next();
                        skinConsultationManager.addNewDoctor(fName,lName,dateOfBirth,mobile,licenseNumber,specialization);
                    }
                    catch (NumberFormatException e){
                        System.out.println("----Error Occurred! Please Check and TRY AGAIN!----");
                    }
                    break;
                case "R":
                    try {
                        System.out.print("Enter the licence number of the doctor: ");
                        int licenseNumber = input.nextInt();
                        skinConsultationManager.deleteDoctor(licenseNumber);
                    }catch (NumberFormatException e){
                        System.out.println("----Error Occurred! Please Check and TRY AGAIN!----");
                    }
                    break;
                case "P":
                    skinConsultationManager.printDoctorsList();
                    break;
                case "S":
                    skinConsultationManager.saveToFile();
                    break;
                case "G":
                    new Gui(skinConsultationManager);
                    break;
                default:
                    System.out.println("-------------Not recognized. Please try again---------------");
            }
        }
    }
}
