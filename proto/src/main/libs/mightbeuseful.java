package main.libs;

import java.awt.FlowLayout;   
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class mightbeuseful { 
    public static void main(String s[]) {  
        JFrame frame = new JFrame("sghsfhgf");  
        JPanel panel = new JPanel();  
        panel.setLayout(new FlowLayout());  
        JLabel label = new JLabel("fdgfdshgfd");   
        panel.add(label); 
        frame.add(panel);  
        frame.setSize(100, 100);  
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true);  

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("Key typed: " + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Key released: " + e.getKeyChar());
            }
        });

        // Hacer que el JFrame sea capaz de recibir eventos de teclado
        frame.setFocusable(true);
    }  
}
