import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("When is your birthday? ('mm/dd/yyyy' format)");
        String bday = input.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        int yearIndex = bday.lastIndexOf("/") + 1;
        int yearCheck = Integer.parseInt(bday.substring(yearIndex));
        if(yearCheck > 9800) //Preventing errors with 5 digit years.
        {
            bday = bday.substring(0,yearIndex) + 9800;
        }
        else if(yearCheck < 1000)
        {
            bday = bday.substring(0,yearIndex) + 1000;
        }

        System.out.println("Would you like Sundays to be included? Type 'y' or 'n'");
        String sunday = input.next();
        input.close();
        if(sunday.equals("y"))
        {
            for(int i = 0; i <= 120; i++)
            {
                LocalDate day = LocalDate.parse(bday,formatter);
                if(isWeekendInc(day))
                {
                    if(day.getYear() < LocalDate.now().getYear())
                    {
                        System.out.print("P | ");
                    }
                    else if(day.isEqual(LocalDate.now()))
                    {
                        System.out.print("Happy Birthday! | ");
                    }
                    else{
                        System.out.print("F | ");
                    }
                    System.out.println("Age " + (i) + " (" + bday + "): " + day.getDayOfWeek());
                }
                bday = bday.substring(0,bday.length()-4) + (Integer.parseInt(bday.substring(bday.length()-4))+1);
            }
        }
        else
        {
            for(int i = 0; i <= 120; i++)
            {
                LocalDate day = LocalDate.parse(bday,formatter);
                if(isWeekend(day))
                {
                    if(day.getYear() < LocalDate.now().getYear())
                    {
                        System.out.print("P | ");
                    }
                    else{
                        System.out.print("F | ");
                    }
                    System.out.println("Age " + (i) + " (" + bday + "): " + day.getDayOfWeek());
                }
                bday = bday.substring(0,bday.length()-4) + (Integer.parseInt(bday.substring(bday.length()-4))+1);
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