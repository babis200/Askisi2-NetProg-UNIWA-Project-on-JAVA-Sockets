package askisi2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Μπαλτατζίδης Χαράλαμπος
 */
public class AskServer {

    public static void main(String[] args) {

        int port = 6000;
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for client...");

            socket = serverSocket.accept();
            System.out.println("Client accepted");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            String line = "";

            while (!line.trim().toLowerCase().equals("over")) {
                line = input.readLine();

                String fname, lname, school;
                int sem, passedSubj;
                if (line.trim().toLowerCase().equals("new")) {

                    Student student;
                    student = (Student) ois.readObject();

                    int status = 0;
                    status = StudentDao.createStudent(student);

                    if (status != 0) {
                        writer.println("Student " + student.getFname() + " registered successfully");
                    } else {
                        writer.println("Student " + student.getFname() + " registered UNsuccessfully");
                    }

                } else if (line.trim().toLowerCase().equals("search")) {
                    lname = input.readLine();

                    Student student = StudentDao.getStudent(lname);

                    if (student.getFname() != null) {
                        writer.println("First Name:" + student.getFname());
                        writer.println("Last Name:" + student.getFname());
                        writer.println("School:" + student.getSchool());
                        writer.println("Semester:" + student.getSemester());
                        writer.println("Number of Passed classes:" + student.getPassedSubj());
                    } else {
                        writer.println("Couldn't find any student with Last Name: " + student.getLname());
                    }

                }

            }

            System.out.println("Closing connection..");
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(AskServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AskServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                serverSocket.close();
                System.out.println("Server terminated");
            } catch (IOException ex) {

            }
        }

    }
}
