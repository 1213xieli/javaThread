package test;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchTest
 * time:2019/5/28
 * author:xieli
 * 总结：适用于一个线程等待其他线程的情景。
 */
public class CountDownLatchTest {
        public static void main(String[] args) {
            final CountDownLatch c = new CountDownLatch(3);//总数3
            Thread t1 = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        System.out.println("开始等");
                        c.await();//阻塞，等待countDown，当countDown到0就执行后面的完事了
                        System.out.println("完事");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            Thread t2 = new Thread(new Runnable(){
                @Override
                public void run() {
                    for(int i=3;i>0;i--){
                        c.countDown();//减1
                        System.out.println(i);
                    }
                }

            });
            t1.start();
            t2.start();
        }


}
