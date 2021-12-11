package edu.ufl.cise.plpfa21.assignment5;

public class Runtime {

	public static boolean not(boolean arg) {
		return !arg;
	}
	public static int neg(int arg) {
		return -arg;
	}
	public static boolean boolor(boolean arg,boolean args){ return arg || args; }
	public static boolean booland(boolean arg,boolean args){ return arg && args; }
	public static int intdiv(int arg,int args){	return arg/args; }
	// Lesser than
	public static boolean boolle(boolean arg,boolean args){
		if(arg==false && args==true)
			return true;
		return false;
	}
	public static boolean intle(int arg,int args){
		if(arg<args)
			return true;
		return false;
	}
	public static boolean strle(String arg,String args){
		if(args.startsWith(arg))
			return true;
		return false;
	}
	// Greater than
	public static boolean boolge(boolean arg,boolean args){
		if(arg==true && args==false)
			return true;
		return false;
	}
	public static boolean intge(int arg,int args){
		if(arg>args)
			return true;
		return false;
	}
	public static boolean strge(String arg,String args){
		if(arg.startsWith(args))
			return true;
		return false;
	}
	// Equals
	public static boolean booleq(boolean arg,boolean args){
		if(arg==args)
			return true;
		return false;
	}
	public static boolean inteq(int arg,int args){
		if(arg==args)
			return true;
		return false;
	}
	public static boolean streq(String arg,String args){
		if(arg.equals(args))
			return true;
		return false;
	}
	// Not Equals
	public static boolean boolneq(boolean arg,boolean args){
		if(arg!=args)
			return true;
		return false;
	}
	public static boolean intneq(int arg,int args){
		if(arg!=args)
			return true;
		return false;
	}
	public static boolean strneq(String arg,String args){
		if(arg.equals(args))
			return false;
		return true;
	}
	public static int intminus(int arg,int args){
		return arg-args;
	}
	public static int intadd(int arg,int args){
		return arg+args;
	}
	public static int inttimes(int arg,int args){
		return arg*args;
	}





}
