package PyT;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * This program calculates the radians per second from a timed 
 * number of revolutions per second
 * 
 * @author James Madden
 * @since November 14, 2013
 * @version 1.0.2
 *
 */
public class PhysicsTimerGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -7696498389552466557L;
	
	private long startTime;
	private long endTime;
	
	private JMenuBar menuBar;
	private JMenu mode, about;
	private JMenuItem aboutVersion;
	private JRadioButtonMenuItem modeContinuous, modeStartStop;
	
	private ButtonGroup modes;
	
	private JButton button;
	private JPanel content;
	private JLabel title, description, units, revLabel;
	private JTextField output;
	private JSpinner revolutions;
	
	public PhysicsTimerGUI(){
		//Create Menu
		menuBar = new JMenuBar();
		
		mode = new JMenu("Mode");
		
		modes = new ButtonGroup();
		
		modeStartStop = new JRadioButtonMenuItem("Start/Stop");
		modeStartStop.addActionListener(this);
		modeStartStop.setSelected(true);
		modes.add(modeStartStop);
		mode.add(modeStartStop);
		
		modeContinuous = new JRadioButtonMenuItem("Continuous");
		modeContinuous.addActionListener(this);
		modes.add(modeContinuous);
		mode.add(modeContinuous);
		
		about = new JMenu("About");
		
		aboutVersion = new JMenuItem("Version: 1.0.2");
		about.add(aboutVersion);
		
		menuBar.add(mode);
		menuBar.add(about);
		
		//Create Content
		content = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		String s_description = "This program calculates the "
				             + "[rad/s] from a timed "
				             + "number of revolutions";
		
		title = new JLabel("Physics Timer");
		title.setFont(title.getFont().deriveFont(40.0f));
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 5;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20,5,5,5);
		c.gridwidth = 4;
		content.add(title, c);
		
		description = new JLabel(s_description);
		description.setFont(title.getFont().deriveFont(20.0f));
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5,5,0,5);
		content.add(description, c);
		
		revLabel = new JLabel("Revolutions:");
		revLabel.setFont(title.getFont().deriveFont(20.0f));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = .6;
		c.ipady = 60;
		c.insets = new Insets(5,5,20,5);
		c.anchor = GridBagConstraints.LINE_END;
		content.add(revLabel, c);
		
		revolutions = new JSpinner(new SpinnerNumberModel(1,1,20,1));
		revolutions.setFont(title.getFont().deriveFont(20.0f));
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = 13;
		c.weightx = .4;
		c.anchor = GridBagConstraints.LINE_START;
		content.add(revolutions, c); 
		
		output = new JTextField("--");
		output.setFont(title.getFont().deriveFont(30.0f));
		output.setHorizontalAlignment(JTextField.CENTER);
		output.setMinimumSize(output.getPreferredSize());
		c.gridx = 2;
		c.gridy = 2;
		c.ipady = 0;
		c.ipadx = 8;
		c.anchor = GridBagConstraints.LINE_END;
		content.add(output, c);
		
		units = new JLabel("[rad/s]");
		units.setFont(title.getFont().deriveFont(20.0f));
		c.gridx = 3;
		c.gridy = 2;
		c.ipady = 0;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = .6;
		content.add(units, c);
		
		button = new JButton("Start Timer");
		button.setName("start");
		button.setFont(button.getFont().deriveFont(30.0f));
		button.addActionListener(this);
		button.setOpaque(true);
		button.setBackground(new Color(0x98CC98));
		c.gridx = 0;
		c.gridy = 3;
		c.ipady = 350;
		c.ipadx = 600;
		c.gridwidth = 4;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		content.add(button, c);
		
		setTitle("Physics Timer - By James Madden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setContentPane(content);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String args[]){
		new PhysicsTimerGUI();
	}
	
	public void doCalcuations(){
		float changeInTimeSeconds = (endTime - startTime)/1000.0f;
		int revs = Integer.parseInt(revolutions.getModel().getValue().toString());
		double answer = (revs / changeInTimeSeconds) * ( 2 * Math.PI);
		output.setText(String.valueOf(answer));
		revalidate();
		repaint();
		output.setMinimumSize(output.getPreferredSize());
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(button)){
			if(button.getName().equals("start")){
				button.setText("Stop Timer");
				button.setBackground(new Color(0xFA4A4A));
				button.setName("stop");
				startTime = System.currentTimeMillis();
			} else if(button.getName().equals("stop")) {
				button.setText("Start Timer");
				button.setBackground(new Color(0x98CC98));
				button.setName("start");
				endTime = System.currentTimeMillis();
				doCalcuations();
			} else if(button.getName().equals("continuous")){
				startTime = endTime;
				endTime = System.currentTimeMillis();
				doCalcuations();
			}
		}
		
		if(event.getSource().equals(modeContinuous)){
			button.setText("Time");
			button.setBackground(new Color(0x63A5EC));
			button.setName("continuous");
			output.setText("--");
			endTime = System.currentTimeMillis();
			output.setMinimumSize(output.getPreferredSize());
		}
		
		if(event.getSource().equals(modeStartStop)){
			button.setText("Start Timer");
			button.setBackground(new Color(0x98CC98));
			button.setName("start");
			output.setText("--");
			output.setMinimumSize(output.getPreferredSize());
		}
		
	}
}
