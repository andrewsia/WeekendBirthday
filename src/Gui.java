import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventObject;

public class Gui extends JFrame{
    public Gui(){
        super("Weekend Birthday");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGui();
            }
        });

    }
    public static void createGui()
    {
        Gui frame = new Gui();
        JLabel enterBday = new JLabel("Enter your Birthday (mm/dd/yyyy)");
        enterBday.setBounds(0,50, 500,200);
        enterBday.setFont(new Font("Sans Serif", Font.BOLD,24));
        enterBday.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(enterBday);

        JCheckBox sundayIncBox = new JCheckBox("Include Sundays?");
        sundayIncBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sundayIncBox.setBounds(300,200,140,50);
        sundayIncBox.setHorizontalTextPosition(SwingConstants.CENTER);
        sundayIncBox.setVerticalTextPosition(SwingConstants.NORTH);

        JTextField textField = new JTextField(10);
        textField.setBounds(sundayIncBox.getX()-200,200,200,50);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(new Font("Sans Serif",Font.PLAIN,24));
        final JScrollPane[] pane = {new JScrollPane()};
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(pane[0]);
                String[] columnNames = {"Age", "Bday", "Day of the Week"};
                Object[][] data = parseBday(textField.getText(), sundayIncBox.isSelected());
                JTable table = new JTable(data,columnNames){
                    @Override
                    public boolean editCellAt(int row, int column, EventObject e) {
                        return false;
                    }
                };
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setBounds(0,0,300,600);
                table.setShowVerticalLines(true);
                table.setShowHorizontalLines(true);
                table.setVisible(true);
                pane[0] = new JScrollPane(table);
                pane[0].setBounds(frame.getWidth()-300,0,300,frame.getHeight()-30);
                pane[0].setVisible(true);
                frame.add(pane[0]);
                textField.setText("");
            }
        });

        frame.add(sundayIncBox);
        frame.add(textField);

        JButton info = new JButton("Credits");
        info.setBounds(200,300,100,50);
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Created by Andrew M. using Java and JFrame." +
                        "\nShows weekend birthdays for up to 120 years old.");
            }
        });
        frame.add(info);

        frame.setVisible(true);
    }
    public static Object[][] parseBday(String bday, boolean withSunday) {
        int yearIndex = bday.lastIndexOf("/") + 1;
        int yearCheck = Integer.parseInt(bday.substring(yearIndex));
        if (yearCheck > 9800) //Preventing errors with 5 digit years.
        {
            bday = bday.substring(0, yearIndex) + 9800;
        } else if (yearCheck < 1000) {
            bday = bday.substring(0, yearIndex) + 1000;
        }
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        if (withSunday) {
            for (int i = 0; i <= 120; i++) {
                LocalDate day = LocalDate.parse(bday, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                if (isWeekendInc(day)) {
                    ArrayList<Object> columns = new ArrayList<>(3);
                    columns.add(i);
                    columns.add(bday);
                    columns.add(day.getDayOfWeek().toString());
                    list.add(columns);
                    //System.out.println("Age " + (i) + " (" + bday + "): " + day.getDayOfWeek());
                }
                bday = bday.substring(0, bday.length() - 4) + (Integer.parseInt(bday.substring(bday.length() - 4)) + 1);
            }
        } else {
            for (int i = 0; i <= 120; i++) {
                LocalDate day = LocalDate.parse(bday, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                if (isWeekend(day)) {
                    ArrayList<Object> columns = new ArrayList<>(3);
                    columns.add(i);
                    columns.add(bday);
                    columns.add(day.getDayOfWeek().toString());
                    list.add(columns);
                    //System.out.println("Age " + (i) + " (" + bday + "): " + day.getDayOfWeek());
                }
                bday = bday.substring(0, bday.length() - 4) + (Integer.parseInt(bday.substring(bday.length() - 4)) + 1);
            }
        }
        Object[][] arr = new Object[list.size()][3];
        for(int i = 0; i<list.size(); i++)
        {
            arr[i][0] = list.get(i).get(0);
            arr[i][1] = list.get(i).get(1);
            arr[i][2] = list.get(i).get(2);
        }
        return arr;
    }

        /**
         * Takes in a LocalDate object and finds if the date is on the weekend.
         * @param date The LocalDate object
         * @return True if the date is on a friday or saturday, false otherwise.
         */
        public static boolean isWeekend (LocalDate date)
        {
            return date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
        }

        /**
         * Takes in a LocalDate object and finds if the date is on the weekend including sunday.
         * @param date The LocalDate object
         * @return True if the date is on a friday, saturday, or Sunday, false otherwise.
         */
        public static boolean isWeekendInc (LocalDate date)
        {
            return isWeekend(date) || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        }
}