
package windowsvolumeosd;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionGroup;

public class Parseargs{

    static boolean show;
    static boolean hide;
    static boolean kill;
    static boolean gui;
    static boolean explorer;
    private static Options os=new Options();
    
    public static void parse(String[] args){
	CommandLineParser p=new DefaultParser();
	OptionGroup g=new OptionGroup();
	Option o1=Option.builder()
	    .required(false)
	    .hasArg(false)
	    .longOpt("hide")
	    .build();
	Option o2=Option.builder()
	    .required(false)
	    .hasArg(false)
	    .longOpt("show")
	    .build();
	Option o3=Option.builder()
	    .required(false)
	    .hasArg(false)
	    .longOpt("kill")
	    .build();
	Option o4=Option.builder()
	    .required(false)
	    .hasArg(false)
	    .longOpt("gui")
	    .build();
       
	g.setRequired(true);
	os.addOptionGroup(g.addOption(o1).addOption(o2).addOption(o3).addOption(o4));
	CommandLine parsed=null;

	if(args.length==0)args=new String[]{"--gui"};
	try{
	    parsed=p.parse(os,args);
	}catch(ParseException e){
	    usage();
	    System.exit(1);
	}

	if(parsed.hasOption("show"))show=true;
	if(parsed.hasOption("hide"))hide=true;
	if(parsed.hasOption("kill"))kill=true;
	if(parsed.hasOption("gui"))gui=true;	    
	    
	
    }
    private static void usage(){
	HelpFormatter usage=new HelpFormatter();
	usage.printHelp("test",os,true);
    }

    

    




}
