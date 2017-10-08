import java.util.concurrent.CountDownLatch;

public class UseCountDownLatch {


    public static void  main(String[] args){
        final CountDownLatch countDownLatch = new CountDownLatch(2);
          new Thread( ()->{
            try {
                System.out.println("进入线程t1"+"等待其他线程处理完成....");
                countDownLatch.await();
                System.out.println("线程t1继续执行。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t1").start();
        new Thread( ()->{
            try {
                System.out.println("进入线程t2"+"等待其他线程处理完成....");
                Thread.sleep(2000);
                System.out.println("线程t2继续执行。。");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t2").start();
        new Thread( ()->{
            try {
                System.out.println("进入线程t3"+"等待其他线程处理完成....");
                Thread.sleep(3000);
                System.out.println("线程t3继续执行。。");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t3").start();

    }
}
