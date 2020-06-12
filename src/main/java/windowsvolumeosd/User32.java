

package windowsvolumeosd;

import com.sun.jna.Library;
import com.sun.jna.Native;

import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.BaseTSD;


public interface User32 extends Library {
    User32 INSTANCE=(User32) Native.loadLibrary("user32",User32.class,W32APIOptions.ASCII_OPTIONS);
    boolean GetWindowRect(WinDef.HWND hW,WinDef.RECT rect);
    boolean EnumWindows(WinUser.WNDENUMPROC callback,WinDef.LPARAM l);
    boolean EnumChildWindows(WinDef.HWND p,WinUser.WNDENUMPROC callback,WinDef.LPARAM l);
    WinDef.HWND FindWindow(String className,String name);
    WinDef.HWND FindWindowEx(WinDef.HWND p,WinDef.HWND childafter,String classname,String name);
    int GetClassName(WinDef.HWND hW,byte[] value,int count);
    int GetWindowText(WinDef.HWND hW,byte[] value,int count);
    WinDef.HWND GetDesktopWindow();
    WinDef.HWND GetAncestor(WinDef.HWND hW,int n);
    boolean ShowWindow(WinDef.HWND hW,int n);
    WinUser.LRESULT SendMessage(WinDef.HWND h,WinDef.UINT msg,WinDef.WPARAM w,WinDef.LPARAM l);
    
    void keybd_event(byte keycode,byte scan,WinDef.DWORD flags,BaseTSD.ULONG_PTR e);
    
}

