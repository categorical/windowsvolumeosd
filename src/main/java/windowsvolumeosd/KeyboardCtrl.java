
package windowsvolumeosd;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.Win32VK;
import com.sun.jna.platform.win32.BaseTSD;

public class KeyboardCtrl{

    private static final User32 user32=User32.INSTANCE;

    public static void sendKeycode(Win32VK keycode){
	user32.keybd_event((byte)keycode.code,(byte)0,new WinDef.DWORD(),new BaseTSD.ULONG_PTR());
	
    }


}
