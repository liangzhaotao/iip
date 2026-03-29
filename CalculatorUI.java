import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorUI extends JFrame implements ActionListener {
    private JTextField display;
    private double num1 = 0;
    private String operator = "";
    private boolean isStart = true;

    public CalculatorUI() {
        setTitle("计算器");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 10, 10));
        String[] buttons = {
                "CLR", "DEL", "±", "%",
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 22));
            btn.addActionListener(this);

            if (text.matches("[÷×\\-+%=]")) {
                btn.setForeground(Color.RED);
            } else if (text.matches("[CLRDEL]")) {
                btn.setForeground(Color.BLUE);
            }
            panel.add(btn);
        }
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            if (cmd.matches("[0-9.]")) {
                if (isStart) {
                    display.setText("");
                    isStart = false;
                }
                display.setText(display.getText() + cmd);
            }
            else if (cmd.equals("CLR")) {
                display.setText("0");
                num1 = 0;
                operator = "";
                isStart = true;
            }
            else if (cmd.equals("DEL")) {
                String text = display.getText();
                display.setText(text.length() > 1 ? text.substring(0, text.length() - 1) : "0");
                isStart = text.length() <= 1;
            }
            else if (cmd.equals("±")) {
                double val = Double.parseDouble(display.getText());
                display.setText(String.valueOf(-val));
            }
            else if (cmd.equals("%")) {
                double val = Double.parseDouble(display.getText()) / 100;
                display.setText(String.valueOf(val));
            }
            else if (cmd.matches("[+\\-×÷]")) {
                num1 = Double.parseDouble(display.getText());
                operator = cmd;
                isStart = true;
            }
            else if (cmd.equals("=")) {
                if (!operator.isEmpty()) {
                    double num2 = Double.parseDouble(display.getText());
                    double result = 0;

                    switch (operator) {
                        case "+": result = num1 + num2; break;
                        case "-": result = num1 - num2; break;
                        case "×": result = num1 * num2; break;
                        case "÷":
                            if (num2 == 0) {
                                JOptionPane.showMessageDialog(this, "除数不能为0！");
                                return;
                            }
                            result = num1 / num2;
                            break;
                    }

                    if (result == (long) result) {
                        display.setText(String.valueOf((long) result));
                    } else {
                        display.setText(String.valueOf(result));
                    }
                    operator = "";
                    isStart = true;
                }
            }
        } catch (Exception ex) {
            display.setText("错误");
            isStart = true;
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("错误：当前环境为无图形界面环境，无法运行图形界面计算器！");
            System.err.println("解决方案：1. 在带桌面的系统中运行；2. 服务器环境配置Xvfb虚拟图形服务");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            new CalculatorUI().setVisible(true);
        });
    }
}