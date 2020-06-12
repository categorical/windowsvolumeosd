
package windowsvolumeosd;



public class Main {

    public static void main(String[] args) throws InterruptedException{

	Parseargs.parse(args);
	int i=3;
	//LogUtil.info("kill"+Parseargs.kill);
	//LogUtil.info("hide"+Parseargs.hide);
	Thread t=new Thread(new Runnable(){
		@Override
		public void run(){
		    VolumeOSD v=VolumeOSD.getinstance();
		    v.printhandle();
		    LogUtil.info("show"+Parseargs.show);
		    LogUtil.info("non final inter:"+i);
		}
	    });
	t.start();

   
    }
}
