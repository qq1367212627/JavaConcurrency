import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class UseSemaphore {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(5);//控制最多 5 个线程同时运行
        for(int i = 0;i<20;i++){
            final int no  = i;
            Runnable run = ()->{
                try {
                    semaphore.acquire();
                    System.out.println("Accessing: "+ no);
                    Thread.sleep((long)(Math.random()*10000));
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executorService.execute(run);
        }

        executorService.shutdown();

    }
}