import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.EventHandler;

public class Test {
    static boolean color = true;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Мой заголовок");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(400,400,400,300);

        frame.addWindowListener(new WindowAdapter() {      //добавляем класс WindowAdapter для работы с окном
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //здесь можно написать код, который будет выполняться перед закрытием окна
            }
        });
    }
}