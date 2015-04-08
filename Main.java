import java.util.Scanner;
class Main
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int n=0;
        while(n<3)
        {
            System.out.println("Enter the number of verices in the Graph:");
            n=Integer.parseInt(sc.nextLine());
            if(n<3)
            {
                System.out.println("Please enter some valid data.");
            }
        }
        Graph g1=new Graph(n);
        System.out.println("Use the format of two vertices with a space between to enter the Edges.");
        System.out.println("To stop entering the edges enter 'end'.");
        System.out.println("Assume Edge numbering to start from 1.");
        System.out.println("Enter the vertices between which edge exists:");
        String s="";
        String temp="";
        int a=0;
        int b=0;
        while(true)
        {
            s=sc.nextLine();
            char c[]=s.toCharArray();
            if(s.equalsIgnoreCase("END"))
            {
                break;
            }
            else
            {
                for(int i=0;i<c.length;i++)
                {
                    if(Character.isDigit(c[i]))
                    {
                        temp=temp+c[i];
                    }
                    else
                    {
                        a=Integer.parseInt(temp);
                        temp="";
                    }
                }
                b=Integer.parseInt(temp);
                temp="";
                if((a==b) || (a>n) || (b>n) || (a<1) || (b<1))
                {
                    System.out.println("Edge not possible");
                }
                else
                {
                    g1.addEdge((a-1),(b-1));
                }
            }
        }
        g1.print();
    }
}