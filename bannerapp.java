import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BannerApp extends JFrame {
    private JLabel bannerLabel;
    private JTextField inputTextField;
    private JButton startButton;
    private boolean isRunning = false;

    public BannerApp() {
        setTitle("Banner App");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        bannerLabel = new JLabel();
        inputTextField = new JTextField(20);
        startButton = new JButton("Start");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    startBannerAnimation();
                } else {
                    stopBannerAnimation();
                }
            }
        });

        add(inputTextField);
        add(startButton);
        add(bannerLabel);
    }

    private void startBannerAnimation() {
        String text = inputTextField.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan teks terlebih dahulu!");
            return;
        }

        isRunning = true;
        startButton.setText("Stop");
        inputTextField.setEnabled(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    text = text.substring(text.length() - 1) + text.substring(0, text.length() - 1);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            bannerLabel.setText(text);
                        }
                    });

                    try {
                        Thread.sleep(200); // Atur kecepatan gerakan di sini (dalam milidetik)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void stopBannerAnimation() {
        isRunning = false;
        startButton.setText("Start");
        inputTextField.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BannerApp bannerApp = new BannerApp();
                bannerApp.setVisible(true);
            }
        });
    }
}
