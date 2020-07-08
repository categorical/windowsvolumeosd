
package windowsvolumeosd;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.Win32VK;

import java.io.IOException;

public class VolumeOSD{

    private static VolumeOSD instance=null;
    private WinDef.HWND handleosd;
    private static final String osdwindowclassname="NativeHWNDHost";
    private static final String osdchildwindowclassname="DirectUIHWND";
    private GUI v;
    private String texthandlenotfound="OSD Window not found.";
    
    public static VolumeOSD getinstance(){
	if(instance==null)
	    instance=new VolumeOSD();
	return instance;
    }
    
    private VolumeOSD(){
	this.initialise();
    }

    private void initialise(){
	updatehandleosd();
    }
    
    public void setgui(GUI v){
	v.setvolumeosd(this);
	this.v=v;
	this.guichange("volumeosdinitialised",null);
	guiupdatehandleosd();
    }
    private void guiupdatehandleosd(){
	this.guichange("handleosd",stringhandleosd());
    }
    
    public void updatehandleosd(){
	ensureexists();
	handleosd=gethandleosd();
	guiupdatehandleosd();
    }

    private void guichange(String k,Object v){
	if(this.v!=null)
	    this.v.onchange(k,v);
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

    public void bringforth(){
	show();
	ensureexists();
    }
    private void ensureexists(){
	KeyboardCtrl.sendKeycode(Win32VK.VK_VOLUME_UP);
	KeyboardCtrl.sendKeycode(Win32VK.VK_VOLUME_DOWN);
    }
    

    private String stringhandleosd(){
	return this.handleosd==null
	    ?texthandlenotfound
	    :String.format("0x%s",WndCtrl.toHex(this.handleosd));
    }
    public void printhandle(){
	LogUtil.info("osd window handle hex: %s",stringhandleosd());
    }
    

    public void show(){
	if(this.handleosd!=null)WndCtrl.restorewindow(this.handleosd);
    }
    public void hide(){
	if(this.handleosd!=null)WndCtrl.minimisewindow(this.handleosd);
    }
    
    public void kill(){
	if(this.handleosd!=null)WndCtrl.killwindow(this.handleosd);
    }

    public void restartexplorer(){
	Runtime r=Runtime.getRuntime();
	int rv;
	try{
	    rv=r.exec("taskkill /f /im explorer.exe").waitFor();
	    if(rv==0)
		rv=r.exec(new String[]{"cmd.exe","/c","start \"\" explorer.exe"}).waitFor();
	}catch(IOException|InterruptedException e){
	    LogUtil.warning(e.toString());
	}
    }

}
