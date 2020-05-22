import java.util.Random;
import java.util.*;

class TestSort
{
    public static void main(String[] args)
    {
        int n = 60;
        int sim = 7;
        Random r = new Random(7361);
        int[] list = new int[n];
        for(int i=0;i<n;i++)
        {
            list[i] = r.nextInt(2*n);
        }
        int[] l2 = list.clone();
        int[] l3 = list.clone();
        Bubblesort b = new Bubblesort();
        ParabBubblesort pb3 = new ParabBubblesort();
        MergeSort m = new MergeSort();
        ParaMergeSort pm = new ParaMergeSort();
        m.flettesortering(list);
        //System.out.println(Arrays.toString(list));
        pm.sortMerge(l2,-1);
        //System.out.println(Arrays.toString(l2));
        System.out.println(Arrays.equals(list, l2));
        int[] allN = new int[]{1000,5000,10000,50000};
        System.out.println("bubblesort:");
        for(int an:allN)
        {
            int[] orgL = new int[an];
            for(int i=0;i<an;i++)
            {
                orgL[i] = r.nextInt(2*n);
            }
            System.out.println(an+":");
            double[] tidSek = new double[sim];
            double[] tidPara = new double[sim];
            boolean[] equals = new boolean[sim];
            for(int i =0;i<sim;i++)
            {
                int[] cList1 = orgL.clone(); 
                int[] cList2 = orgL.clone();
                long start = System.nanoTime();
                b.bobblesort(cList1);
                double stop = System.nanoTime();
                tidSek[i] = (double)(stop-start)/1000000;
                start = System.nanoTime();
                pb3.fuckIt(cList2);
                stop = System.nanoTime();
                tidPara[i] = (double)(stop-start)/1000000;
                equals[i] = Arrays.equals(cList1, cList2);
            }
            Arrays.sort(tidSek);
            System.out.printf("tid sekvensielt: %.2f ms \n",tidSek[sim/2]);
            Arrays.sort(tidPara);
            System.out.printf("tid parallellt: %.2f ms \n",tidPara[sim/2]);
            System.out.printf("speed up: %.2f \n",(tidSek[sim/2]/tidPara[sim/2]));
            System.out.println(Arrays.toString(equals));
            System.out.println("end "+an);
        }
        System.out.println("mergesort:");
        int[] allN2 = new int[]{1000,5000,10000,50000,100000,200000,500000,750000,1000000,1200000,1500000,2000000};
        for(int an:allN2)
        {
            int[] orgL = new int[an];
            for(int i=0;i<an;i++)
            {
                orgL[i] = r.nextInt(2*n);
            }
            System.out.println(an+":");
            double[] tidSek = new double[sim];
            double[] tidPara = new double[sim];
            boolean[] equals = new boolean[sim];
            for(int i =0;i<sim;i++)
            {
                int[] cList1 = orgL.clone(); 
                int[] cList2 = orgL.clone();
                long start = System.nanoTime();
                m.flettesortering(cList1);
                double stop = System.nanoTime();
                tidSek[i] = (double)(stop-start)/1000000;
                start = System.nanoTime();
                pm.sortMerge(cList2,0);
                stop = System.nanoTime();
                tidPara[i] = (double)(stop-start)/1000000;
                equals[i] = Arrays.equals(cList1, cList2);
            }
            Arrays.sort(tidSek);
            System.out.printf("tid sekvensielt: %.2f ms \n",tidSek[sim/2]);
            Arrays.sort(tidPara);
            System.out.printf("tid parallellt: %.2f ms \n",tidPara[sim/2]);
            System.out.printf("speed up: %.2f \n",(tidSek[sim/2]/tidPara[sim/2]));
            System.out.println(Arrays.toString(equals));
            System.out.println("end "+an);
        }
    }
}