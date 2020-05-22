import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CyclicBarrier;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

class ParabBubblesort
{
    CyclicBarrier cb;
    CyclicBarrier cbMellom;
    CyclicBarrier cbMellom2;
    Lock lock;
    int threads;
    public ParabBubblesort()
    {
        lock = new ReentrantLock();
        threads = Runtime.getRuntime().availableProcessors();
        cb = new CyclicBarrier(threads+1);
        cbMellom = new CyclicBarrier(threads);
        cbMellom2 = new CyclicBarrier(threads);
    }
    void fuckIt(int[] a)
    {
        int rounds = a.length-2;
        int from = 0;
        int partSize = (a.length/2)/threads;
        int to = from+partSize;
        cbMellom.reset();
        cbMellom2.reset();
        for(int i =0;i< threads;i++)
        {
            if(i+1==threads){to =(a.length/2);}
            new Thread(new Worker(i+1, from,to,rounds,a)).start();
            from = to;
            to = to+partSize;
        }
        try{cb.await();}
        catch(Exception e){}
    }

    class Worker implements Runnable
    {
        int[] a;
        int from;
        int to;
        int rounds;
        int id;
        public Worker(int id,int f,int t,int r,int[] a)
        {
            this.id = id;
            from = f;
            to = t;
            rounds = r;
            this.a = a;
        }
        public void run()
        {
            //System.out.println(from+","+to+"("+(2*from)+" "+(2*to)+")");
            try{cbMellom.await();}
            catch(Exception e){}
            for(int k=0;k<rounds-2;k++)
            {
                if(id == 1){cbMellom.reset();}
                try{cbMellom2.await();}
                catch(Exception e){}
                if(k%2 == 0)
                {
                    int stop = to;
                    if(id == 4){stop = to;}
                    for(int i = from;i<stop;i++)
                    {
                        int indeks = 2*i;
                        if(a[indeks]>a[indeks+1])
                        {
                            int temp = a[indeks];
                            a[indeks] = a[indeks+1];
                            a[indeks+1] = temp;
                        }
                    }
                    //System.out.println(from+","+(to-1)+"("+(2*from)+" "+(2*(to-1))+")");
                }
                else
                {
                    int stop = to;
                    if(id == 4){stop = to-1;}
                    for(int i = from;i<stop;i++)
                    {
                        int indeks = 2*i;
                        if(a[indeks+1]>a[indeks+2])
                        {
                            int temp = a[indeks+1];
                            a[indeks+1] = a[indeks+2];
                            a[indeks+2] = temp;
                        }
                    }
                    //System.out.println(from+","+(to-2)+"("+(2*from)+" "+(2*(to-2))+")");
                }
                if(id == 1){cbMellom2.reset();}
                //System.out.println("thread nr"+id+" k:"+k);
                try{cbMellom.await();}
                catch(Exception e){}
            }
            //System.out.println("thread nr"+id+" is done");
            try{cb.await();}
            catch(Exception e){}
        }
    }
}