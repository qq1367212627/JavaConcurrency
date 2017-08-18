package sync;

/**
 * Created by lewis on 2017/8/18.
 */
public class SyncDubbo1 {
    /**
     * 锁的重入问题

     * 关键字 synchronized拥有锁重入的功能，当一个线程得到一个对象的锁后，
     * 再次请求此对象时是可以在此得到该对象的锁
     * */
    public synchronized void method1(){
        System.out.println("method 1 ..");
        method2();
    }
    public synchronized void method2(){

        System.out.println("method 2 ..");
        method3();
    }
    public synchronized void method3(){
        System.out.println("method 3 ..");
    }
    public static void main(String[] args){
        final SyncDubbo1 syncDubbo1 = new SyncDubbo1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncDubbo1.method1();
            }
        }).start();
    }
}
