
package pool;

public class MyTask implements  Runnable{

    private int id;
    private String name;

    public MyTask(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void run() {
        System.out.println("run taskId = "+this.id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
