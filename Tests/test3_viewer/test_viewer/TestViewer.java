import java.awt.*;
import javax.swing.*;

public class TestViewer {

public static void main(String[] args) {
EventQueue.invokeLater(new Runnable() {

@Override
public void run() {
JFrame frame = new ViewerFrame();
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.pack();
frame.setLocationRelativeTo(null);
frame.setVisible(true);
}
});
}
}