import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Main {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("When is your birthday? ('mm/dd/yyyy' format)");
        String bday = input.nextLine();
        if(Integer.parseInt(bday.substring(bday.length()-4)) >= 4999) //Preventing errors with 5 digit years.
        {
            bday = bday.substring(0,bday.length()-4) + 4998;
        }
        System.out.println("Would you like Sundays to be included? Type 'y' or 'n'");
        String sunday = input.next();
        input.close();
        if(sunday.equals("y"))
        {
            for(int i = 0; i < 120; i++)
            {
                bday = bday.substring(0,bday.length()-4) + (Integer.parseInt(bday.substring(bday.length()-4))+1);
                LocalDate day = LocalDate.parse(bday,formatter);
                if(day.getYear() >= LocalDate.now().getYear() && isWeekendInc(day))
                {
                    System.out.println("Age " + (i+1) + " (" + bday + "): " + day.getDayOfWeek());
                }
            }
        }
        else
        {
            for(int i = 0; i < 120; i++)
            {
                bday = bday.substring(0,bday.length()-4) + (Integer.parseInt(bday.substring(bday.length()-4))+1);
                LocalDate day = LocalDate.parse(bday,formatter);
                if(day.getYear() >= LocalDate.now().getYear() && isWeekend(day))
                {
                    System.out.println("Age " + (i+1) + " (" + bday + "): " + day.getDayOfWeek());
                }
            }
        }
    }

    /**
     * Takes in a LocalDate object and finds if the date is on the weekend.
     * @param date The LocalDate object
     * @return True if the date is on a friday or saturday, false otherwise.
     */
    public static boolean isWeekend(LocalDate date)
    {
        return date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    /**
     * Takes in a LocalDate object and finds if the date is on the weekend including sunday.
     * @param date The LocalDate object
     * @return True if the date is on a friday, saturday, or Sunday, false otherwise.
     */
    public static boolean isWeekendInc(LocalDate date)
    {
        return isWeekend(date) || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}