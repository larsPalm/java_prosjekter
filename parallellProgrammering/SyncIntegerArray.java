import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SyncIntegerArray
{
    Lock lock;
    int len;
    int[] data;
    public SyncIntegerArray(int v)
    {
        lock = new ReentrantLock();
        len = v;
        data = new int[len];
    }
    public SyncIntegerArray(int[] a)
    {
        lock = new ReentrantLock();
        data = a;
        len = data.length;
    }
    int get(int i)
    {
        int ret;
        if(i<0 || i>data.length-1){return -1;}
        lock.lock();
        try{ret = data[i];}
        finally{lock.unlock();}
        return ret;
    }
    void set(int i,int v)
    {
        if(i<0 || i>data.length-1){return;}
        lock.lock();
        try{data[i] = v;}
        finally{lock.unlock();}
    }
}