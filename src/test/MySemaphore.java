package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * MySemaphore
 * time:2019/5/28
 * author:xieli、
 * 总结：抢位置。
 *
 * availablePermits()：看现在可用的信号量。
 * acquire()：
 * 尝试获取一个位置，如果获取不到则阻塞。
 * release()：释放位置。
 * acquireUninterruptibly(int num)：
 * 尝试获取num个许可，如果没有足够的许可则阻塞，一直阻塞到有足够的许可释放出来。
 * 调用这个方法的线程具有优先获取许可的权利。如果调用线程被interrupted,该线程并不会被打断，
 * 它会继续阻塞等待许可。
 */
public class MySemaphore implements Runnable{
    Semaphore position;
    private int id;
    public MySemaphore(int i,Semaphore s){
        this.id=i;
        this.position=s;
    }

    @Override
    public void run() {
        try{
            if(position.availablePermits()>0){
                System.out.println("顾客["+this.id+"]进入厕所，有空位");
            }
            else{
                System.out.println("顾客["+this.id+"]进入厕所，没空位，排队");
            }
            position.acquire();//只有在acquire之后才能真正的获得了position
            System.out.println("#########顾客["+this.id+"]获得坑位");
            Thread.sleep((int)(Math.random()*100000));
            System.out.println("@@@@@@@@@顾客["+this.id+"]使用完毕");
            position.release();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        ExecutorService list= Executors.newCachedThreadPool();
        Semaphore position=new Semaphore(2);
        for(int i=0;i<10;i++){
            list.submit(new MySemaphore(i+1,position));
        }
        list.shutdown();
        position.acquireUninterruptibly(2);
        System.out.println("使用完毕，需要清扫了");
        position.release(2);
    }


}
