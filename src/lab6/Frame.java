package lab6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame implements ActionListener{
	JButton button;
	Frame(){
		button = new JButton();
		button.setBounds(200, 200, 100, 50);
		button.setText("Click me");
		button.addActionListener(e -> System.out.println("Helloaaa"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(500, 500);
		this.setVisible(true);
		this.add(button);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==button) {
			 System.out.println("Hello");
		 }
	}
}
