import static java.lang.System.out;

public class Test1 {

    public static void main(String[] args){


        // -Xms5m 初始堆大小
        // -Xmx20m 最大堆大小
        // -XX:+PrintGCDetails 查看详细信息，包括各个区的情况
        // -XX:+UseSerialGC  配置串行回收器
        // -XX:+PrintCommandLineFlags 可以将隐式或显示传给虚拟机的参数输出。

        //查看GC信息
        out.println("max memory:"+Runtime.getRuntime().maxMemory());
        out.println("free memory:"+Runtime.getRuntime().freeMemory());
        out.println("total memory:"+Runtime.getRuntime().totalMemory());

        byte arr1[] = new byte[1<<20];
        out.println("分配了1M");
        out.println("max memory:"+Runtime.getRuntime().maxMemory());
        out.println("free memory:"+Runtime.getRuntime().freeMemory());
        out.println("total memory:"+Runtime.getRuntime().totalMemory());

        byte arr2[] = new byte[1<<22];
        out.println("分配了4M");
        out.println("max memory:"+Runtime.getRuntime().maxMemory());
        out.println("free memory:"+Runtime.getRuntime().freeMemory());
        out.println("total memory:"+Runtime.getRuntime().totalMemory());

    }
}
