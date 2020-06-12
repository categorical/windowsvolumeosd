
package windowsvolumeosd;


import java.util.logging.Logger;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;
import java.util.logging.StreamHandler;
import java.util.logging.Level;

public class LogUtil{
    private static final Logger l=getlogger("");

    public static void info(String msg){
	l.info(msg);
    }
    public static void warning(String msg){
	l.log(Level.WARNING,msg);
    }
    
    private static Logger getlogger(String name){
	Logger l=Logger.getLogger(name);
	sethandler(l);
	return l;
    }
    
    private static void sethandler(Logger l){
	l.setUseParentHandlers(false);
	Logger rl=Logger.getLogger("");
	Handler[] hs=rl.getHandlers();
	for(int i=0;i<hs.length;i++)
	    rl.removeHandler(hs[i]);

	System.setProperty("java.util.logging.SimpleFormatter.format","%1$ts %4$s: %5$s%n");
	l.addHandler(new StreamHandler(System.out,new SimpleFormatter()));
    }

}
