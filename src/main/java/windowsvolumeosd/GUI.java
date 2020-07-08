
package windowsvolumeosd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.GroupLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class GUI {

    static {
	setLookAndFeel();
    }

    private final JFrame F=new JFrame();
    private final String title="Windows Volume OSD";
    private final int startx=600;
    private final int starty=300;

    private VolumeOSD m;
    
    public GUI(){
	
	initialisectrls();
	setframe();
    }

    public void setvolumeosd(VolumeOSD m){
	this.m=m;
    }
    
    private void setframe(){
	F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	F.setTitle("Windows Volume OSD Controls");
	F.setLocation(startx,starty);
	F.setVisible(true);
	F.add(getcontainer());
	F.pack();	
    }
    
    private JPanel getcontainer(){
	JPanel c=new JPanel();
	addcomponents(c);
	return c;
    }

    private void addcomponent(GroupLayout.Group g,JComponent c){
	final int max=Short.MAX_VALUE;
	final int pref=GroupLayout.DEFAULT_SIZE;
	final int min=0;
	g.addComponent(c,min,pref,pref);
	customisecomponent(c);
    }
    private void addcomponents(JPanel p){
	final GroupLayout.Alignment at=GroupLayout.Alignment.TRAILING;
	final GroupLayout.Alignment ab=GroupLayout.Alignment.BASELINE;
	GroupLayout l=new GroupLayout(p);
	p.setLayout(l);

	GroupLayout.Group hg,c0,c2,c4;
	hg=l.createSequentialGroup();
	l.setHorizontalGroup(hg);
	c0=l.createParallelGroup();
	c2=l.createParallelGroup();
	c4=l.createSequentialGroup();
	hg.addGroup(c0).addGroup(c2);
	c0.addGroup(c4);
	addcomponent(c4,l0);
	addcomponent(c4,l1);
	addcomponent(c4,tf0);
	addcomponent(c2,tb0);
	addcomponent(c2,b0);
	addcomponent(c2,b1);
	addcomponent(c2,b2);
	addcomponent(c2,b3);

	GroupLayout.Group vg,c1,c3;
	vg=l.createSequentialGroup();
	l.setVerticalGroup(vg);
	c1=l.createParallelGroup(ab);
	c3=l.createParallelGroup();
	vg.addGroup(c1);
	
	addcomponent(c1,l0);
	addcomponent(c1,l1);
	addcomponent(c1,tf0);
	addcomponent(c1,tb0);
	addcomponent(vg,b0);
	addcomponent(vg,b1);
	addcomponent(vg,b2);
	addcomponent(vg,b3);
	
	
	l.setAutoCreateGaps(true);	     
	l.setAutoCreateContainerGaps(true);	     
    }
    
    private JToggleButton tb0;
    private JLabel l0;
    private JLabel l1;
    private JTextField tf0;
    private JButton b0;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    
    private void initialisectrls(){
	tb0=new JToggleButton("hide");tb0.setEnabled(false);
	tb0.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent e){
		    JToggleButton c=(JToggleButton)e.getSource();
		    if(c.isSelected()){
			m.hide();c.setText("show");
		    }else{
			m.show();c.setText("hide");
		    }
		}
	    });

	l0=new JLabel("OSD window handle");
	l1=new JLabel(":");
	tf0=new JTextField(20);
	tf0.setEditable(false);
	b0=new JButton("Reload Explorer");
	b0.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    m.restartexplorer();

		}
	    });
	b1=new JButton("Find OSD window");
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    m.updatehandleosd();
		}
	    });
	b2=new JButton("Bring forth");
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    m.bringforth();

		}
	    });
	b3=new JButton("Kill");
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    m.kill();

		}
	    });
    }

    private void customisecomponent(JComponent c){
	
	switch(c.getClass().getSimpleName()){
	case "JToggleButton":
	case "JTextField":
	case "JLabel":
	case "JButton":
	default:
	    //c.setFont(new Font("monospaced",Font.PLAIN,20));
	}

    }
    
    private static void setLookAndFeel(){
	try{
	    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	}catch(ClassNotFoundException|
	       InstantiationException|
	       IllegalAccessException|
	       UnsupportedLookAndFeelException e){
	    e.printStackTrace();
	}
    }


    public void onchange(String k,Object v){
	switch(k){
	case "volumeosdinitialised":
	    tb0.setEnabled(true);
	    break;
	case "handleosd":
	    tf0.setText((String)v);
	    break;
	default:

	}

    }
}
