
import java.io.Serializable;
import java.util.Date;

public class Consultation implements Serializable {
    //Instance variables
    private  Doctor doctor;
    private Patient patient;
    private Date startingDateTime;
    private Date endingDateTime;
    private int cost;
    private byte[] note;

    //Public Constructor
    public Consultation(Doctor doctorRef, Patient patientRef, Date startingDateTimeRef, Date endingDateTimeRef, int costRef, byte[] note){
        this.doctor = doctorRef;
        this.patient = patientRef;
        this.startingDateTime = startingDateTimeRef;
        this.endingDateTime = endingDateTimeRef;
        this.cost = costRef;
        this.note = note;
    }

    public Date getStartingDateTime() {
        return startingDateTime;
    }

    public Date getEndingDateTime() {
        return endingDateTime;
    }

    public int getCost() {
        return cost;
    }

    public byte[] getNote() {
        return note;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setStartingDateTime(Date startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    public void setEndingDateTime(Date endingDateTime) {
        this.endingDateTime = endingDateTime;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNote(byte[] note) {
        this.note = note;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
