package volatileT;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lewis on 2017/8/19.
 *
 *  实例通过 2 种方式演示 ：volatile 多线程下不具有原子性，只是 具有可见性
 *  AtomicInteger 修饰的 count 是具有原子性的 整数对象
 *  而volatile 修饰的count仅有可见性
 */
public class VolatileNoAtomic extends Thread{
   // private static volatile int count = 0;
    private static AtomicInteger count = new AtomicInteger(0);

    private static void addCount(){
        for(int i= 0;i<1000;i++){
            count.incrementAndGet();
            //count++;
        }
        System.out.println(count);
    }
    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        addCount();
    }
    public static void main(String[] args) {
        VolatileNoAtomic[] arr = new VolatileNoAtomic[100];
        for(int i = 0;i<10;i++){
            arr[i] = new VolatileNoAtomic();
        }
        for(int i = 0;i<10;i++){
            arr[i].start();
        }
    }
}
