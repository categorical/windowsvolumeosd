
package windowsvolumeosd;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.Win32VK;

public class VolumeOSD{

    private static VolumeOSD instance=null;
    private WinDef.HWND handleosd;
    private static final String osdwindowclassname="NativeHWNDHost";
    private static final String osdchildwindowclassname="DirectUIHWND";
    
    public static VolumeOSD getinstance(){
	if(instance==null)
	    instance=new VolumeOSD();
	return instance;
    }
    
    private VolumeOSD(){
	this.initialise();
    }

    private void initialise(){
	this.ensureexists();
	this.handleosd=gethandleosd();
	if(this.handleosd==null){
	    throw new RuntimeException("OSD Window not found.");
	}	
    }

    private WinDef.HWND gethandleosd(){
	WinDef.HWND osd=null;
	int i=0;
	while(i++<10){
	    if((osd=gethandleosdonce())!=null)
	    	break;
	    try{
		Thread.sleep(200);
	    }catch(InterruptedException e){
		break;
	    }
	}
	return osd;
    }
    
    private WinDef.HWND gethandleosdonce(){
	WinDef.HWND[] ws=WndCtrl.gettopwindowsbyclassname(osdwindowclassname);
	WinDef.HWND c;
	WinDef.HWND osd=null;
	for(int i=0;i<ws.length;i++){
	    c=WndCtrl.getchildwindowbyclassname(ws[i],osdchildwindowclassname);
	    if(c!=null){
		osd=ws[i];
		break;
	    }
	}
	
	return osd;
    }


    private void ensureexists(){
	KeyboardCtrl.sendKeycode(Win32VK.VK_VOLUME_UP);
	KeyboardCtrl.sendKeycode(Win32VK.VK_VOLUME_DOWN);
    }
    
    
    public void printhandle(){
	LogUtil.info(String.format("osd window handle hex: %s",
			     this.handleosd==null?null:WndCtrl.toHex(this.handleosd)));
	LogUtil.info(String.format("osd window name: %s",
			     this.handleosd==null?null:WndCtrl.getName(this.handleosd)));
    }


    public void show(){
	WndCtrl.restorewindow(this.handleosd);
    }
    public void hide(){
	WndCtrl.minimisewindow(this.handleosd);
    }
    
    public void kill(){
	WndCtrl.killwindow(this.handleosd);
    }

}
