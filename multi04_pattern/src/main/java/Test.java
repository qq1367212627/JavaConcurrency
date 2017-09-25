/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

public class Test {

    public static void main(String[] args){
        point:for(int i = 0;i<10;i++){
            System.out.println(i);
            if(i==5){
                continue point;
            }
        }

        point:for(int i = 0;i<10;i++){
            System.out.println(i);
            if(i==5){
                break point;
            }
        }

    }
}
