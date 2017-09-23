package thread;

/**
 * Created by lewis on 2017/8/11.
 */
public class MultiThread {

    private static int num = 0;

    /**
     * 若在方法前使用 static 修饰 则输出
     *          tag a,set num over !
                tag:anum =100
                tag b,set num over !
                tag:bnum =200
        因为此时每个线程对象要获取的是MultiThread类级别的锁，所以每次多个线程都会争用一个对象
     *
        不使用 static 修饰 则输出：
             tag a,set num over !
             tag b,set num over !
             tag:bnum =200
             tag:anum =200
        此时每次要获取的是对象（m1,m2）级别的锁。
     * */
    public  static synchronized  void printNum(String tag) throws InterruptedException {
        if(tag.equals("a")){
            num = 100;
            System.out.println("tag a,set num over !");
            Thread.sleep(1000);
        }else {
            num = 200;
            System.out.println("tag b,set num over !");
        }
        System.out.println("tag:"+tag+"num ="+num);
    }

    public static void main(String [] args){
        final  MultiThread m1 = new MultiThread();
        final  MultiThread m2 = new MultiThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    m1.printNum("a");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    m2.printNum("b");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
