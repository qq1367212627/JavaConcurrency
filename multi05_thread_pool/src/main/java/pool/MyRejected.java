
package pool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.System.out;

public class MyRejected implements RejectedExecutionHandler{


    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        out.println("自定义处理。。。");
        out.println("当前被拒绝任务为："+r.toString());
    }
}
