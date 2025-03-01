public class Patient extends Person{
    private int patientId;

    public int getPatientId() {
        return patientId;
    }

    public Patient(String nameRef, String surnameRef, String dateOfBirthRef, String mobileRef, int patientIdRef){
        super(nameRef,surnameRef,dateOfBirthRef,mobileRef); //Calling the constructor of the parent class
        this.patientId = patientIdRef;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
