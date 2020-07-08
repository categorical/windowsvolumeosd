


package windowsvolumeosd;
import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) throws InterruptedException{

	Parseargs.parse(args);
	
	if(Parseargs.gui){
	    SwingUtilities.invokeLater(new Runnable(){
		    public void run(){
			GUI v=new GUI();
			Thread t=new Thread(new Runnable(){
				public void run(){
				    VolumeOSD m=VolumeOSD.getinstance();
				    m.setgui(v);
				}
			    });t.start();		     
		    }
		});
	}else{
	    VolumeOSD m=VolumeOSD.getinstance();
	    if(Parseargs.hide){
		m.hide();
	    }else if(Parseargs.show){
		m.show();
	    }else if(Parseargs.kill){
		m.kill();
	    }
	}
    }

    private static void printedt(){
	LogUtil.info("iseventdispatchthread: %s",SwingUtilities.isEventDispatchThread());
	try{
	    Thread.sleep(3000);
	}catch(InterruptedException e){
	}
    }

}
