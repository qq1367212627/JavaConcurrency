package thread;

/**
 * Created by lewis on 2017/9/7.
 *
 * ThreadLocal 不同线程获得各自 线程私有对象
 *  ThreadLocal 不提供锁 ：以空间换时间的思路，为每个线程提供变量的独立副本
 *  线程之间 变量不共享，以 保障线程安全
 */
public class ConnThreadLocal {
    private static ThreadLocal<String>th = new ThreadLocal<>();

    public void setTh(String value){
      th.set(value);
    }
    public void getTh(){
        System.out.println(Thread.currentThread().getName()+":"+this.th.get());
    }
    public static void main(String []args){
        final ConnThreadLocal ct = new ConnThreadLocal();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ct.setTh("张三");
                ct.getTh();
            }
        },"t1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ct.setTh("李四");
                ct.getTh();
            }
        },"t2").start();
    }
}
