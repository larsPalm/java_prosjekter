import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        Tree tre = new Tree();
        /*tre.add(50);
        tre.add(30);
        tre.add(20);
        tre.add(40);
        tre.add(70);
        tre.add(60);
        tre.add(80);*/
        tre.addAll(new int[]{50,30,20,40,70,60,80,50,80});
        System.out.println(tre.size());
        /*System.out.println(tre.root.value);
        System.out.println(tre.root.left.value);
        System.out.println(tre.root.left.left.value);
        System.out.println(tre.root.left.right.value);
        System.out.println(tre.root.right.value);
        System.out.println(tre.root.right.left.value);
        System.out.println(tre.root.right.right.value);*/
        /*System.out.println(tre.existsInTree(50));
        System.out.println(tre.existsInTree(30));
        System.out.println(tre.existsInTree(20));
        System.out.println(tre.existsInTree(40));
        System.out.println(tre.existsInTree(70));
        System.out.println(tre.existsInTree(60));
        System.out.println(tre.existsInTree(80));*/
        System.out.println(Arrays.toString(tre.sortedArray()));
        System.out.println(Arrays.toString(tre.findInRange(30,70)));
        tre.remove(50);
        System.out.println(tre.root.value);
        tre.remove(70);
        System.out.println(tre.root.right.value);
    }
}