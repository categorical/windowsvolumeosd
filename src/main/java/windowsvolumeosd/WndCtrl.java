
package windowsvolumeosd;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import java.util.ArrayList;

public class WndCtrl{
    private static final User32 user32=User32.INSTANCE;

    private static final int NAME_MAX_LENGTH=100;
    private static final byte[] NAME=new byte[NAME_MAX_LENGTH];

    public static void restorewindow(WinDef.HWND h){
	user32.ShowWindow(h,WinUser.SW_RESTORE);
    }
    public static void minimisewindow(WinDef.HWND h){
	user32.ShowWindow(h,WinUser.SW_MINIMIZE);
    }

    public static WinDef.HWND desktopwindow(){
	return user32.GetDesktopWindow();
    }
    
    public static boolean killwindow(WinDef.HWND h){
	WinUser.LRESULT r=user32.SendMessage(h,new WinDef.UINT(WinUser.WM_CLOSE),null,null);
	
	return r.longValue()==0;
    }
    
    public static WinDef.HWND gettopwindowbyclassname(String classname){
	WinDef.HWND h=user32.FindWindow(classname,null);
	return h;
    }
    
    public static WinDef.HWND getchildwindowbyclassname(WinDef.HWND p,String classname){
	WinDef.HWND h=user32.FindWindowEx(p,null,classname,null);
	return h;
    }


    public static WinDef.HWND[] gettopwindowsbyclassname(String classname){
	return getchildwindowsbyclassname(null,classname);
    }

    public static WinDef.HWND[] getchildwindowsbyclassname(WinDef.HWND p,String classname){
	ArrayList<WinDef.HWND> wl=new ArrayList<>();
	WinDef.HWND h=null;
	while((h=user32.FindWindowEx(p,h,classname,null))!=null)
	    wl.add(h);

	WinDef.HWND[] ws=new WinDef.HWND[wl.size()];
	for(int i=0;i<wl.size();i++)
	    ws[i]=wl.get(i);
	
	return ws;
    }
    
    /*
     * "For Windows 8 and later, EnumWindows enumerates only top-level windows of desktop apps."
     * That means, ugly windows like the one Volume OSD uses are ignored. 
     */
    public static WinDef.HWND[] gettopwindowsbyclassname_(final String classname){
	final byte[] windowclassname=new byte[NAME_MAX_LENGTH];
	ArrayList<WinDef.HWND> wl=new ArrayList<>();
	WinUser.WNDENUMPROC callback=new WinUser.WNDENUMPROC(){
		@Override
		public boolean callback(WinDef.HWND h,Pointer value){
		    user32.GetClassName(h,windowclassname,NAME_MAX_LENGTH);
		    if(Native.toString(windowclassname).equals(classname))
			wl.add(h);
		    return true;
		}
		    
	};
	user32.EnumWindows(callback,null);
	WinDef.HWND[] ws=new WinDef.HWND[wl.size()];
	for(int i=0;i<wl.size();i++)
	    ws[i]=wl.get(i);
	
	return ws;
    }
    
    public static WinDef.HWND getwindownamecontains(final String key){
	final WinDef.HWND h=new WinDef.HWND();
	user32.EnumWindows(new WinUser.WNDENUMPROC(){
		@Override
		public boolean callback(WinDef.HWND hW1,Pointer value){
		    user32.GetWindowText(hW1,NAME,NAME_MAX_LENGTH);
		    if (Native.toString(NAME).contains(key)){
			h.setPointer(hW1.getPointer());
			return false;
		    }
		    return true;
		}
	    },null);
	return h;

    }

    public static String getName(WinDef.HWND hW){
	user32.GetWindowText(hW,NAME,NAME_MAX_LENGTH);
	return Native.toString(NAME);

    }

    public static String toHex(WinDef.HWND hW){
	return Long.toHexString(Pointer.nativeValue(hW.getPointer()));
    }
}
