import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main{
    public static void main(String[] args) {
        Connection connection = new Connection("145.33.225.170", 7789);
        connection.sendMessage("Login Reinder");
        while (true) {
            System.out.println(connection.receiveMessage());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
