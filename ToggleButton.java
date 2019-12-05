import java.awt.event.*;
import java.awt.*;
import javax.swing.JButton;


public class ToggleButton extends JButton implements ActionListener{
	private boolean actionPerformed;
	
	public ToggleButton(String text){
		super(text);
		addActionListener(this);
		actionPerformed = false;
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		actionPerformed = true;
		//System.out.println("button pressed");
	}
	
	public boolean getNewAction(){
		//System.out.println("actionPerformed");
		return actionPerformed;
	}
	
	public void resetActionPerformed(){
		actionPerformed = false;
	}
}