
public class Doctor extends Person{
    private int licenceNum;
    private String specialization;

    public Doctor(String nameRef, String surnameRef, String dateOfBirthRef, String mobileRef, int licenceNumRef, String specializationRef){
        super(nameRef,surnameRef,dateOfBirthRef,mobileRef); //Calling the constructor of the parent class
        this.licenceNum = licenceNumRef;
        this.specialization = specializationRef;
    }

    public int getLicenceNum() {
        return licenceNum;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setLicenceNum(int licenceNum) {
        this.licenceNum = licenceNum;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

