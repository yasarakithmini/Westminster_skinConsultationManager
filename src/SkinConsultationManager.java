public interface SkinConsultationManager {
    public void addNewDoctor(String fName, String lName, String dateOfBirth, String mobile, int licenseNumber, String specialization);
    public void deleteDoctor(int licenceNo);
    public void printDoctorsList();
    public void saveToFile();
    public void readFromFile();
}
