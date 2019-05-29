package test;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier
 * time:2019/5/28
 * author:xieli
 *
 * 总结：想想一下百米跑，所有运动员都就位之后才会发令起跑，线程调用await意味着说，我准备好了。
 * await()：阻塞，直到阻塞的线程数量达到num个。
 */
public class MainMission {

        private CyclicBarrier barrier;
        private final static int threadCounts = 5;
        public void runMission() {
            ExecutorService exec= Executors.newFixedThreadPool(threadCounts);
            //new 的时候要传入数字，我发现，这个类似semaphore，如果位置不足，线程会抢位置。数字要是threadCounts+1为主线程留一个位子，但实际测试中发现，只要等于threadCount就可以
            barrier=new CyclicBarrier(threadCounts+1);
            for(int i=0;i<5;i++){
                exec.execute(new Mission(barrier));
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("所有任务都执行完了");
            exec.shutdown();//如果不关闭，程序一直处于运行状态
        }
        public static void main(String[] args) {
            MainMission m = new MainMission();
            m.runMission();
        }
    }

    class Mission implements Runnable{
        private CyclicBarrier barrier;
        public Mission(CyclicBarrier barrier){
            this.barrier = barrier;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"开始执行任务");
            try {
                int sleepSecond = new Random().nextInt(10)*1000;
                System.out.println(Thread.currentThread().getName()+"要执行"+sleepSecond+"秒任务");
                Thread.sleep(sleepSecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"执行完毕");
        }

}
