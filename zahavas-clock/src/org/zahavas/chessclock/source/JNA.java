package org.zahavas.chessclock.source;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.win32.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;
import com.sun.jna.platform.win32.WinBase.SYSTEM_INFO;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;




public class JNA {

	public interface Paspi extends StdCallLibrary {
		Paspi INSTANCE = (Paspi)Native.loadLibrary("Paspi", Paspi.class);
		public int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
		
	}
	
	
	public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class);

        /**
         * Retrieves the number of milliseconds that have elapsed since the system was started.
         * @see http://msdn2.microsoft.com/en-us/library/ms724408.aspx
         * @return number of milliseconds that have elapsed since the system was started.
         */
        public int GetTickCount();
        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;
        public int GetLastError();
        public Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
        
    };
    
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32)Native.loadLibrary("user32", User32.class);

        /**
         * Contains the time of the last input.
         * @see http://msdn.microsoft.com/library/default.asp?url=/library/en-us/winui/winui/windowsuserinterface/userinput/keyboardinput/keyboardinputreference/keyboardinputstructures/lastinputinfo.asp
         */
        public static class LASTINPUTINFO extends Structure {
            public int cbSize = 8;

            /// Tick count of when the last input event was received.
            public int dwTime;

			@Override
			protected List getFieldOrder() {
				// TODO Auto-generated method stub
				 
		            return Arrays.asList(new String[] { 
		                    "cbSize", "dwTime", 
		                });
			}
        }
        public int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
        
        public boolean GetLastInputInfo(LASTINPUTINFO result);
        public HWND GetForegroundWindow();
        public int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
    
    }	
   
    /**
     * Get the amount of milliseconds that have elapsed since the last input event
     * (mouse or keyboard)
     * @return idle time in milliseconds
     */
    public static int getIdleTimeMillisWin32() {
        User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
         User32.INSTANCE.GetLastInputInfo(lastInputInfo);
        return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
    }

    enum State {
        UNKNOWN, ONLINE, IDLE, AWAY
    };

    public static class SYSTEMTIME extends Structure {
        public short wYear;
        public short wMonth;
        public short wDayOfWeek;
        public short wDay;
        public short wHour;
        public short wMinute;
        public short wSecond;
        public short wMilliseconds;
        protected List getFieldOrder() { 
            return Arrays.asList(new String[] { 
                "wYear", "wMonth", "wDayOfWeek", "wDay", "wHour", "wMinute", "wSecond", "wMilliseconds",
            });
        }
    }

}
  
	

