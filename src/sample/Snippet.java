package sample;

public class Snippet {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	Pipe p1 = new BlockingQueue();
	Pipe p2 = new BlockingQueue();
	Pipe p3 = new BlockingQueue();
		 
		 
	Filter a1 = new FilterA(null,p1);
	Filter b1 = new FilterB(p1,p2);
	Filter c1 = new FilterC(p2,p3);
	Filter d1 = new FilterD(p3,null);
	 
	Thread th1 = new Thread( a1 );
	Thread th2 = new Thread( b1 );
    Thread th3 = new Thread( c1 );
	Thread th4 = new Thread( d1 );
	
	th1.start();
	th2.start();  
	th3.start();
	th4.start();
	
	}
}