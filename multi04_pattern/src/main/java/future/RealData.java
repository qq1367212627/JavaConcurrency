/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package future;

public class RealData implements Data{

    private String result;

    public RealData(String queryStr) {
       System.out.println("根据"+queryStr+"进行查询，这是一个很耗时的操作...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
        }
        System.out.println("操作完毕，获取结果");
        result = "查询结果";
    }

    public String getRequest() {
        return result;
    }
}
