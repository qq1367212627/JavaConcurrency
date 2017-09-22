package sync;

/**
 * Created by lewis on 2017/8/18.
 */
public class SyncDubbo2 {
    /***
     * 由于异常的释放锁现象
     */

    private int i = 0;
    public synchronized void operation() throws InterruptedException {
        while(true){
            try {
                i++;
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName()+", i = "+i);
                if(i==10){
                    i = 1/0;
                }
            }catch (Exception e){
                e.printStackTrace();
                //throw  new RuntimeException();//抛出异常，结束任务
                continue;//跳过此次异常，继续任务
            }
        }
    }
    public static void main(String[]args){
        final  SyncDubbo2 syncDubbo2 = new SyncDubbo2();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncDubbo2.operation();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
