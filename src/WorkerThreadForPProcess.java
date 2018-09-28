
public class WorkerThreadForPProcess implements Runnable {
	
	private String output;
	
	public WorkerThreadForPProcess(String s){
        this.output=s;
    }
	
	@Override
    public void run() {
        System.out.println("Thread Name ->" + Thread.currentThread().getName()+"    Started...       ");
        processCommand();
        System.out.println(Thread.currentThread().getName()+"      Ended...      Output => "+output);
    }
	
	private void processCommand() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
