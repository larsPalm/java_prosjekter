/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;*/
import java.io.InputStream;
import java.net.*; 
import java.util.*;
//import org.json.simple.*;

class ApiMini{
    public static void main(String[] args){
        ApiHandlerMini ahm = new ApiHandlerMini();
        String inline = ahm.getApiData();
        HashMap<String,Double> hm = ahm.makeHashMap(inline);
        String dato = ahm.findDate(inline);
        System.out.println(dato);
        hm.forEach((k, v) -> System.out.println(k + " : " + (v)));
        int valg = -1;
        Scanner sc = new Scanner(System.in);
        while(valg != 0){
            Boolean isNumber = false;
            while(!isNumber){
                System.out.println("enter 0 for aa avslutte, 1 for kovertere mellom valutaer");
                try{
                    valg = Integer.parseInt(sc.nextLine());
                    isNumber=true;
                }
                catch(NumberFormatException e){
                    System.out.println("du skrev inn noe som ikke var et tall");
                    valg = -1;
                    isNumber = false;
                }
            }
            if(valg == 1){
                System.out.print("velg blant:");
                hm.forEach((k, v) -> System.out.print(k+" "));
                System.out.print("\n");
                String v1 =sc.nextLine().toUpperCase();
                String v2 =sc.nextLine().toUpperCase();
                double c1 = 0.0;
                double c2 = 0.0;
                System.out.println("skriv inn onkset mengde");
                double ammount = 0.0;
                try{
                    ammount = Double.parseDouble(sc.nextLine());
                }catch(NumberFormatException e){ammount = 1;}
                try{
                    c1 = hm.get(v1);
                    c2 = hm.get(v2);
                    
                    System.out.printf("%.2f %s er %.2f %s \n",ammount,v1,(c1/c2*ammount),v2);
                }catch(Exception e){System.out.println("du skrev inn et/to navn som ikke finnes");}
            }
        }
        sc.close();
    }
}