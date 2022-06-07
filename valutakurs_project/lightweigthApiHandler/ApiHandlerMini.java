import java.io.InputStream;
import java.net.*; 
import java.util.*;

class ApiHandlerMini{
    public String getApiData(){
        HttpURLConnection conn;
        URL url;
        String retur = "";
        try{
            url = new URL("http://127.0.0.1:8080/latestValues/");
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            int responsecode = conn.getResponseCode();
            System.out.println(responsecode);
            Scanner sc = new Scanner(url.openStream());
            String inline = "";
            while(sc.hasNext())
            {
            inline+=sc.nextLine();
            }
            sc.close();
            return inline;
        }
        catch(Exception e){return retur;}
    }

    public HashMap<String,Double> makeHashMap(String data){
        String[] ar = data.substring(15).replace('{',' ').replace('}',' ').replace('"',' ').split(",");
        System.out.println(Arrays.toString(ar));
        HashMap<String,Double> hm = new HashMap<>();
        for(String elm: ar){
            String[] info = elm.strip().split(":");
            hm.put(info[0].substring(0,3),Double.parseDouble(info[1]));
            //System.out.println(info[0].substring(0,3)+","+info[1]);
        }
        return hm;
    }

    public String findDate(String data){
        System.out.println(data.substring(2, 12));
        return data.substring(2, 12);
    }
}