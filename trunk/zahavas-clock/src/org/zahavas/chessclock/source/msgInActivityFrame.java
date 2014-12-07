package org.zahavas.chessclock.source;

import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.zahavas.chessclock.source.JNA.State;

public class msgInActivityFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JLabel lblMsg;
	public msgInActivityFrame mMsg;
	
	
	int idleSec = 0;
	State state = State.UNKNOWN;
	boolean stateChanged = false;
	State newState = State.UNKNOWN;
	
	{
		Timer displayTimer = new Timer(20000,this);
		 displayTimer.start(); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		 idleSec = JNA.getIdleTimeMillisWin32() / 1000;
         //System.out.println(idleSec);
         newState =
            idleSec < 30 ? State.ONLINE :
            idleSec > 3 * 60? State.AWAY : State.IDLE;

        if (newState != state) {
            state = newState;
            stateChanged = true;
             
            //System.out.println(dateFormat.format(new Date()) + " # " + state);	
        }
        else stateChanged = false;
        
         if (state == state.ONLINE)
         {this.dispose();}
         
         if (state == state.AWAY & stateChanged == true)
         {
      	   Toolkit.getDefaultToolkit().beep();
      	   
      	    GregorianCalendar t = new GregorianCalendar();
      	    t.get(Calendar.SECOND);
      	   int tt = t.get(Calendar.SECOND);
      	   this.setLocation(tt,tt);
      	  
      	   this.setVisible(true);
      	   this.toFront();
      	   this.repaint();
      	   this.requestFocus();
         }
         
		
		
		
		
	}
	public msgInActivityFrame()
	{
		  GridBagLayout layout = new GridBagLayout();
	      setLayout(layout);
		  lblMsg	= new JLabel("WorkStation is inactive");
	      add(lblMsg,			  new GBC(2,7,3,1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(100, 0).setInsets(1));
	       
	      pack();
		
	}
	
}
