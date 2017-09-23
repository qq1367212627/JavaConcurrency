/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package indi.lewis;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        FutureClient fc = new FutureClient();
        Data data =  fc.getRequest("请求参数");
        System.out.println(" 请求发送成功");
        System.out.println("做其他事情。。");
        String result = data.getRequest();
        System.out.println(result);
    }
}
