import java.io.StreamTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class MainThread {
	
	public static void main(String[] args) throws InterruptedException{
		
			System.out.println("         ---------> Starting Initial Thread <--------");
			Thread startingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    	System.out.println("Initial Thread Running ...");
                    	//Some Task
                    	Thread.sleep(3000);
                    	System.out.println("Thread Terminated ...");
                    }
                    catch ( Exception e ) {
                        }
                }
            });
            startingThread.start();
            while(startingThread.isAlive()){
          
            }	
		
			System.out.println("         ---------> Starting Thread Pool for Batch Processing  <--------");
			int threadNo = 5;
			Runnable worker = null;
			ExecutorService threadPool = Executors.newCachedThreadPool();
			for (int i = 0; i < 10; i++) {
				worker = new WorkerThreadForPProcess("" + i);
	            threadPool.execute(worker);
			 }
			threadPool.shutdown();
			while(!threadPool.isTerminated()){
				
			}
			System.out.println("         ---------> Final Thread Running <--------");
			// Final Thread Call for Last Layer  
			if(threadPool.isTerminated()){
				new Thread(new Runnable() { 
		            public void run(){        
		            System.out.println("Final Thread Running ...");
		            System.out.println("Thread Terminated ...");
		            }
		        }).start();
			}
			
			
	}
}
