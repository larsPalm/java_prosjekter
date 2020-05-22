import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SyncInteger
{
    int value;
    Lock lock;
    public SyncInteger(int v)
    {
        lock = new ReentrantLock();
        value = v;
    }
    public SyncInteger()
    {
        lock = new ReentrantLock();
        value = 0;
    }
    void increment()
    {
        lock.lock();
        try{ value++;}
        finally{lock.unlock();}
    }
    void set(int v)
    {
        lock.lock();
        try{ value = v;}
        finally{lock.unlock();}
    }
    void add(int v)
    {
        lock.lock();
        try{ value += v;}
        finally{lock.unlock();}
    }
    int get()
    {
        int ret;
        lock.lock();
        try{ret = value;}
        finally{lock.unlock();}
        return ret;
    }

}