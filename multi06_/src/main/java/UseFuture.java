import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class UseFuture implements Callable<String>{
    private String para;

    public UseFuture(String para) {
        this.para = para;
    }


    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        String res = this.para + "处理完成";
        return res;
    }
    public static void main(String[] args) throws Exception {
        String query = "query";
        FutureTask<String> futureTask = new FutureTask<String>(new UseFuture(query));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(futureTask);
        System.out.println(" 请求完成");
        Thread.sleep(2000);
        System.out.println(" 数据" + futureTask.get());
        executor.shutdown();
    }

}
