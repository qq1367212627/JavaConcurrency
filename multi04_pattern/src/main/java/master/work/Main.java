

package master.work;

import java.util.Random;

public class Main {

    public static void main(String[] args){
        Master master = new Master(new Worker(),20);
        Random r = new Random();
         for(int i = 1;i<=100;i++){
             Task t =new Task();
             t.setId(i);
             t.setPrice(r.nextInt(1000));
             master.submit(t);
         }
         master.excute();
        long start = System.currentTimeMillis();
        while(true){
            if(master.isDone()){
                long end = System.currentTimeMillis();
                int priceResult = master.getResult();
                System.out.printf("最终结果 ：%d 耗时：%d",priceResult,end-start);
                break;
            }
        }
    }
}
