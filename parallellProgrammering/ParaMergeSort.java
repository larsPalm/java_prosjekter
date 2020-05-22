import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CyclicBarrier;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

class ParaMergeSort
{
    int threads;
    public ParaMergeSort()
    {
        threads = Runtime.getRuntime().availableProcessors();
    }
    void sortMerge(int[] a, int level)
    {
        int a1[];
        int a2[];
        int l1,l2;
        if(a.length>1)
        {
            l1 = a.length/2;
            if(a.length % 2 != 0)
            {
                l2 = a.length/2+1;
            }
            else
            {
                l2 = a.length/2;
            }
            a1 = new int[l1];
            a2 = new int[l2];
            int teller =0;
            for(int i =0;i<a.length/2;i++)
            {
                a1[i]=a[i];
            }
            for(int j=a.length/2;j<a.length;j++)
            {
                a2[teller]=a[j];
                teller++;
            }
            if((int)Math.pow(2, level)<threads)
            {
                CyclicBarrier cb = new CyclicBarrier(3);
                new Thread(new Worker(a1, level+1,cb)).start();
                new Thread(new Worker(a2, level+1,cb)).start();
                try{cb.await();}
                catch(Exception e){}
            }
            else
            {
                sortMerge(a1, level+1);
                sortMerge(a2, level+1);
            }
            flett(a1, a2, a);
        }
    }
    int[] flett(int[]s1,int[]s2,int[]s3)
    {
        int i=0;
        int j=0;
        while(i<s1.length && j<s2.length)
        {
        if(s1[i] <= s2[j])
        {
            s3[i+j] = s1[i];
            i++;
        }
        else
        {
            s3[i+j]= s2[j];
            j++;
        }
        }
        //hvis det er flere elementer igjen i s1 som ikke er puttet inn ennaa i s3
        while(i<s1.length)
        {
        s3[i+j] = s1[i];
        i++;
        }
        //hvis det er flere elementer igjen i s2 som ikke er puttet inn ennaa i s3
        while(j<s2.length)
        {
        s3[i+j]=s2[j];
        j++;
        }
        //returnerer den ferdigflettede arrayen
        return s3;
    }
    class Worker implements Runnable
    {
        int[] a;
        int level;
        CyclicBarrier minCb;
        public Worker(int[] a,int level, CyclicBarrier barrior)
        {
            this.a = a;
            this.level = level;
            minCb = barrior;
        }
        public void run()
        {
            sortMerge(a, level);
            try{minCb.await();}
            catch(Exception e){}
        }
    }
}