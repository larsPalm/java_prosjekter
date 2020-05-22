class Bubblesort
{
  public int[] bobblesort(int[] ar)
  {
    for(int i=0;i<ar.length;i++)
    {
      for(int j =ar.length-1;j>0;j--)
      {
        if(ar[j-1]>ar[j])
        {
          int mid = ar[j-1];
          ar[j-1] = ar[j];
          ar[j]=mid;
        }
      }
    }
    return ar;
  }
  public int[] bobblesort2(int[] ar)
  {
    boolean ferdig = false;
    while(!ferdig)
    {
      ferdig = true;
      for(int i=1;i<ar.length;i++)
      {
        if(ar[i-1]<ar[i])
        {
          int mid = ar[i-1];
          ar[i-1] = ar[i];
          ar[i] = mid;
          ferdig = false;
        }
      }
    }
    return ar;
  }
  public int[] bobblesort3(int[] ar)
  {
    boolean ferdig = false;
    while(!ferdig)
    {
      ferdig = true;
      for(int i=ar.length-1;i>0;i--)
      {
        if(ar[i-1]>ar[i])
        {
          int mid = ar[i-1];
          ar[i-1] = ar[i];
          ar[i]=mid;
          ferdig = false;
        }
      }
    }
    return ar;
  }

}