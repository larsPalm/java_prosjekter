import java.util.concurrent.CyclicBarrier;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Random;
import java.io.*;

class Ping
{
    int threads;
    CyclicBarrier cb;
    public Ping()
    {
        threads = Runtime.getRuntime().availableProcessors();
        cb = new CyclicBarrier(threads+1);
    }
    void start()
    {
        for(int i=0;i<threads;i++)
        {
            new Thread(new PingWorker()).start();
        }
        try{cb.await();}
        catch(Exception e){}
    }
    public void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        String out = "";
        while ((line = reader.readLine()) != null) {
            out+=line+"\n";
        }
        System.out.println(out);
    }
    class PingWorker implements Runnable
    {
        public void run()
        {
            long start = System.nanoTime();
            Random r = new Random();
            for(int i=0;i<100;i++)
            {
                String ip = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
                try{
                    System.out.printf("pinging %s\n",ip);
                    Process p = Runtime.getRuntime().exec(String.format("ping -Q -c 1 %s",ip));
                    //printResults(p);
                }catch(Exception e){}
            }
            long stop = System.nanoTime();
            double time = (double)(stop-start)/1000000;
            System.out.printf("tid: %.2f ms \n",time);
            try{cb.await();}
            catch(Exception e){}
        }
    }
}