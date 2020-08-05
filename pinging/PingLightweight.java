import java.util.Random;

class PingLightweight
{
    public static void main(String[] args) {
        Random r = new Random();
        for(int i=0;i<2000;i++)
        {
            String ip = r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
            try{
                System.out.printf("pinging %s\n",ip);
                Process p = Runtime.getRuntime().exec(String.format("ping -Q -c 1 %s",ip));
                //printResults(p);
            }catch(Exception e){}
        } 
    }
}