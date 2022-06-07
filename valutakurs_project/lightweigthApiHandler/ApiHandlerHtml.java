import java.io.InputStream;
import java.net.*;
import java.util.*;

class ApiHandlerHtml{
  public String getHtml(){
    HttpURLConnection conn;
    URL url;
    String retur = "";
    try{
        url = new URL("http://127.0.0.1:8080/compareImg/USD/CAD");
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        int responsecode = conn.getResponseCode();
        System.out.println(responsecode);
        Scanner sc = new Scanner(url.openStream());
        String inline = "";
        while(sc.hasNext())
        {
        inline+=sc.nextLine().replace(" ","");
        }
        sc.close();
        return inline;
    }
    catch(Exception e){return retur;}
  }
}
