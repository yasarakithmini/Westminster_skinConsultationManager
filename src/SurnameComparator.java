import java.util.Comparator;

public class SurnameComparator implements Comparator<Doctor> {
    @Override
    public int compare(Doctor o1, Doctor o2) {
        //Compare doctor objects according to their surname
        //And change their order in alphabetical order
        if( o1.getSurname().toLowerCase().compareTo(o2.getSurname().toLowerCase()) == 0 ){
            return 0;
        }else if (o1.getSurname().toLowerCase().compareTo(o2.getSurname().toLowerCase()) > 0){
            return 1;
        }else{
            return -1;
        }
    }
}
