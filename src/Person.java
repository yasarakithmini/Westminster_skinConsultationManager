import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String surname;
    private String dateOfBirth;
    private String mobile;

    //Constructor for Person class
    public Person(String nameRef, String surnameRef, String dateOfBirthRef, String mobileRef){
        this.name = nameRef;
        this.surname = surnameRef;
        this.dateOfBirth = dateOfBirthRef;
        this.mobile = mobileRef;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMobile() {
        return mobile;
    }
}
