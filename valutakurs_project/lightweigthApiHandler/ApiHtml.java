/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;*/
import java.io.InputStream;
import java.net.*;
import java.util.*;
//import org.json.simple.*;

class ApiHtml{
    public static void main(String[] args){
      ApiHandlerHtml ahh = new ApiHandlerHtml();
      String response = ahh.getHtml();
      int len = response.length();
      System.out.println(response);
      System.out.println(len);
    }
}
