import javax.swing.*;
import java.awt.*;
import java.util.List;
import modules.*;

public class App {
    private JDialog dialog = new JDialog();
    private Client host = new Client();

    private javax.swing.JPanel MainArea;
    private JTextField Answer;
    private JButton SubmitButton;
    private JTextArea Console;

    public void actionHandle(Client.ReturnFlags res) {
        if(res == Client.ReturnFlags.ERROR) {
            Console.setText("");
            Console.append("ReEnter the answer!");
        }
        else if(res == Client.ReturnFlags.FAIL) {
            List<Integer> ballCount = host.getState();
            Console.setText("");

            String text = ballCount.get(0)+"strike, "+ballCount.get(1)+"ball, "+ballCount.get(2)+"out.\n";
            Console.append(text);

            host.clearState();
        }
        else {
            Console.setText("");
            Console.append("You win!!");

            host.clearState();
        }
    }

    private void onClick() {
        host.setMyAnswer(Integer.parseInt(Answer.getText()));

        Client.ReturnFlags res = host.playGame();
        actionHandle(res);

        Answer.setText("");
    }

    public void drawApp() {
        dialog.add(MainArea, BorderLayout.CENTER);
        dialog.setSize(800, 900);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        dialog.pack();
        dialog.setLocation(200, 200);
        dialog.setVisible(true);

        // SubmitButton.setSize(300, 100);
        SubmitButton.setText("확인");
        SubmitButton.setBounds(400, 100, 80, 30);

        SubmitButton.addActionListener(event -> onClick());
    }

    public static void main(String[] args) {
        App gameboy = new App();
        gameboy.drawApp();
    }

}
