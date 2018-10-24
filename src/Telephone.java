import java.io.Serializable;
import java.util.Date;
import java.text.*;
public class Telephone implements Serializable{

    private String name;
    private String surname;
    private String patronymic;
    private String number;
    private int rate;
    private String date;
    private int discount;
    private String talkingStartTime;
    private String talkingEndTime;

    public Telephone(String name, String patronymic,String surname, String number, int rate, int discount){
        this.name=name;
        this.surname=surname;
        this.patronymic=patronymic;
        this.number=number;
        this.rate=rate;
        this.date=DateFormat.getDateInstance().format(new Date());
        this.discount=discount;
        this.talkingStartTime =DateFormat.getTimeInstance().format(new Date());
        this.talkingEndTime =DateFormat.getTimeInstance().format(new Date());
    }


    public String toString() {
    return (this.name +" "+ this.patronymic +" " + this.surname+ ", telephone number : " + this.number + ", rate : " + this.rate + ", " + this.date);
    }

}

