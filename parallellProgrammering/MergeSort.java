class MergeSort
{
  public int[] flettesortering(int[]ar)
  {
    int ar1[];
    int ar2[];
    int lengde1,lengde2;
    if(ar.length>1)
    {
      //sjekker om lengden er partall eller oddetall
      lengde1 = ar.length/2;
      if(ar.length % 2 != 0)
      {
        lengde2 = ar.length/2+1;
      }
      else
      {
        lengde2 = ar.length/2;
      }
      ar1 = new int[lengde1];
      ar2 = new int[lengde2];
      //legger elementene i de to arrayen ettersom om de er i forste eller andre
      //halvdel av arrayen
      int teller =0;
      for(int i =0;i<ar.length/2;i++)
      {
        ar1[i]=ar[i];
      }
      for(int j=ar.length/2;j<ar.length;j++)
      {
        ar2[teller]=ar[j];
        teller++;
      }
      //kaller saa paa flettesortering() for begge de nye arrayene
      flettesortering(ar1);
      flettesortering(ar2);
      //fletter saa ar1 og ar2 til aa bli den sorterte ar
      flett(ar1,ar2,ar);
    }
    return ar;
  }


  //flettingen
  private int[] flett(int[]s1,int[]s2,int[]s3)
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
    //skrivGraf(s3);
    return s3;
  }
  public void skrivGraf(int[] ar)
  {
    for(int i:ar)
    {
      System.out.printf(i+" ");
    }
    System.out.println("");
  }
}
