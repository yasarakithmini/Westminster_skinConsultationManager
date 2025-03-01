import junit.framework.TestCase;
import org.junit.Test;
import java.security.NoSuchAlgorithmException;

public class WestminsterSkinConsultationManagerTest extends TestCase {
    @Test
    public void testAddDoctorShouldSaveADoctor() throws NoSuchAlgorithmException {
        String fName = "Matt";
        String lName = "Murdock";
        String dOB = "12/12/2012";
        String mobile = "0718010111";
        int licenceNum = 1001;
        String specialization = "Surgery";

        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addNewDoctor(fName,lName,dOB,mobile,licenceNum,specialization);
        assertEquals(manager.getDoctors().get(0).getName(),"Matt");
    }

    @Test
    public void testDeleteDoctorShouldRemoveADoctor() throws NoSuchAlgorithmException {
        String fName = "Matt";
        String lName = "Murdock";
        String dOB = "12/12/2012";
        String mobile = "0718010111";
        int licenceNum = 1001;
        String specialization = "Surgery";

        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addNewDoctor(fName,lName,dOB,mobile,licenceNum,specialization);
        assertEquals(manager.getDoctors().get(0).getName(),"Matt");
        manager.deleteDoctor(1001);
        assertEquals(manager.getDoctors().size(),0);
    }

    @Test
    public void testSaveDataShouldRead() throws NoSuchAlgorithmException {
        String fName = "Matt";
        String lName = "Murdock";
        String dOB = "12/12/2012";
        String mobile = "0718010111";
        int licenceNum = 1001;
        String specialization = "Surgery";

        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addNewDoctor(fName,lName,dOB,mobile,licenceNum,specialization);
        manager.saveToFile();
        manager.deleteDoctor(1001);
        manager.readFromFile();
        assertEquals(manager.getDoctors().get(0).getName(),"Matt");
    }
}