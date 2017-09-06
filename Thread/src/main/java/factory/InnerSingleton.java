package factory;

/**
 * Created by lewis on 2017/9/7.
 *
 * 单例模式的线程安全写法1（饿汉模式）：
 *      使用静态内部类及静态工厂
 */
public class InnerSingleton {
   private static class Singleton{
       private static Singleton singleton = new Singleton();
   }
   public static Singleton getInstace(){
       return Singleton.singleton;
   }

}
