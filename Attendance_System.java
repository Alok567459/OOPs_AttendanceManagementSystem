import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Attendance_System{

    static Scanner sc = new Scanner(System.in);

    
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== Attendance Management System (CSV + Roll No) ====");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance by Roll No");
            System.out.println("3. View Attendance");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: addStudent(); break;
                case 2: markAttendance(); break;
                case 3: viewAttendance(); break;
                case 4: return;
                default: System.out.println("Invalid Choice!");
            }
        }
    }

    static void addStudent() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("students.csv", true));

            System.out.print("Enter Roll Number: ");
            String roll = sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

           
            bw.write(roll + "," + name);
            bw.newLine();
            bw.close();

            System.out.println("Student added successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    static void markAttendance() {

        System.out.print("Enter Roll Number: ");
        String roll = sc.nextLine();

        String name = findStudentName(roll);

        if (name == null) {
            System.out.println("❌ Roll Number not found!");
            return;
        }

        System.out.println("Student Found: " + name);
        System.out.print("Present or Absent (P/A): ");
        String status = sc.nextLine().toUpperCase();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("attendance.csv", true));

            String date = LocalDate.now().toString();

            bw.write(roll + "," + name + "," + status + "," + date);
            bw.newLine();
            bw.close();

            System.out.println("Attendance saved successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    
    static String findStudentName(String roll) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("students.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(roll)) {  // Found roll number
                    br.close();
                    return data[1];
                }
            }
            br.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("students.csv not found!");
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return null;
    }

  
    static void viewAttendance() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("attendance.csv"));
            String line;

            System.out.println("\nRoll\tName\tStatus\tDate");

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(data[0] + "\t" + data[1]+"\t" + data[2] + "\t"+ data[3]);
            }

            br.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("attendance.csv not found!");
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
    }
    }
}
