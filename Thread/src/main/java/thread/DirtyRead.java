package thread;

/**
 * Created by lewis on 2017/8/18.
 */
public class DirtyRead {
    private String username = "xdx";
    private String password = "123";

    public synchronized  void  setValue(String username ,String password) throws InterruptedException {
        this.username = username;
        //睡眠2s，再设置值
        Thread.sleep(2000);
        this.password = password;
        System.out.println("username = "+username+" password :" +password);
    }
    /***
     * 若 getValue() 没有使用synchronized修饰，根据程序流程，那么会在设置username之后“抢先”读取，导致脏读
     *username = z3 password :123
      username = z3 password :456

     *
     若 getValue() 使用synchronized修饰
         username = z3 password :456
         username = z3 password :456
     getValue() 与   setValue() 互斥，避免脏读

     * */

    public synchronized void getValue(){
        System.out.println("username = "+username+" password :" +password);
    }

    public static  void  main(String[] args) throws InterruptedException {
        final  DirtyRead dirtyRead = new DirtyRead();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dirtyRead.setValue("z3","456");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
        dirtyRead.getValue();
    }
}
