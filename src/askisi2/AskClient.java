package askisi2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Μπαλτατζίδης Χαράλαμπος
 */
public class AskClient {

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 6000;

        Socket socket = null;
        Scanner userInput = new Scanner(System.in);

        try {
            System.out.println("Trying to connect to Server...");
            socket = new Socket(hostname, port);

            //Initiate the streams to read ,and write objects to the socket
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Connection established");

            String command = "";
            String received = "";
            OUTER:
            while (true) {
                System.out.print("Enter your command(new|search|over): ");
                command = userInput.nextLine();
                out.println(command);
                out.flush();
                Student student = new Student();
                switch (command.trim().toLowerCase()) {
                    case "new": {
                        System.out.println("Enter the student details: ");
                        System.out.print("First Name:");
                        String fname = userInput.nextLine();
                        student.setFname(fname);

                        System.out.print("Last Name:");
                        String lname = userInput.nextLine();
                        student.setLname(lname);

                        System.out.print("School:");
                        String school = userInput.nextLine();
                        student.setSchool(school);

                        System.out.print("Semester:");
                        int sem = userInput.nextInt();
                        student.setSemester(sem);

                        System.out.print("Number of Passed Classes:");
                        int passedSubj = userInput.nextInt();
                        student.setPassedSubj(passedSubj);

                        oos.writeObject((Student) student);
                        oos.flush();

                        //clear the input
                        //userInput.nextLine();

                        received = reader.readLine();
                        System.out.println("Server's response: " + received);
                        System.out.println("-------------------------------");
                        break;
                    }
                    case "search": {
                        System.out.println("Enter the student's Last Name: ");
                        String lname;
                        lname = userInput.nextLine();
                        out.println(lname);
                        out.flush();

                        //clear the input
                        userInput.nextLine();

                        received = reader.readLine();
                        System.out.println("Server's response: " + received);
                        System.out.println("-------------------------------");
                        break;
                    }
                    case "over":
                        System.out.println("Closing this connection..");
                        socket.close();
                        System.out.println("Connection closed");
                        break OUTER;
                    default:
                        received = reader.readLine();
                        System.out.println("Server's response: " + received);
                        break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(AskClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
