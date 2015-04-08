//Class to implement Graph
import java.util.ArrayList;
public class Graph
{
    private int level1;
    private boolean adjacentMatrix[][];     //stores the adjacency matrix of the Graph
    private ArrayList<Edge> edges;          //stores the list of edges along with their property
    private int vertexCount;                //number of vertex in the graph
    private int colourMatrix[][];           //keeps track of colours used on vertex
    public Graph(int vertexCount)           //constructor to create an object of Graph with a given vertexCount
    {
        level1=0;
        edges=new ArrayList<Edge>();
        this.vertexCount=vertexCount;
        this.adjacentMatrix=new boolean[vertexCount][vertexCount];
        colourMatrix=new int[vertexCount][vertexCount];
        for(int i=0;i<vertexCount;i++)      //initialising the colourMatrix
        {
            for(int j=0;j<vertexCount;j++)
            {
                colourMatrix[i][j]=0;
            }
        }
    }
    private void addEdge(Edge e)            //function to add edges in the Graph
    {
        this.adjacentMatrix[e.x][e.y]=true;
        this.adjacentMatrix[e.y][e.x]=true;
        (this.edges).add(e);
    }
    public void addEdge(int i, int j)       //overloaded function to add edges to Graph
    {
        if(i>=0 && i<vertexCount && j>=0 && j<vertexCount)
        {
            adjacentMatrix[i][j]=true;
            adjacentMatrix[j][i]=true;
            if(getEdgeIndex(i,j)<0)
            {
                edges.add(new Edge(i,j));
            }
        }
    }
    private boolean isEdge(int i,int j)      //function to check if an edge exists between the givan pair of vertex
    {
        if(i>=0 && i<vertexCount && j>=0 && j<vertexCount)
        {
            return adjacentMatrix[i][j];
        }
        return false;
    }
    public int getVertexCount()             //function to return vertexCount
    {
        return this.vertexCount;
    }
    private int getEdgeColour(int x,int y)  //function to return colour assigned between a pair of vertices given that the edge exists
    {
        int lt=getEdgeIndex(x,y);
        int c=(edges.get(lt)).getColour();
        return c;
    }
    private void setEdgeColour(Edge e,int t)    //function to set edge colour
    {
        colourMatrix[e.x][t]=1;
        colourMatrix[e.y][t]=1;
    }
    private boolean isEdgeColoured(int x,int y) //function to check if the edge is coloured or noy
    {
        boolean k=false;
        if(getEdgeColour(x,y)>=0)
        {
            k=true;
        }
        return k;
    }
    private Integer[] availableColoursOnVertex(int x,Graph p)   //function to return available colours on a given vertex in a given graph
    {
        ArrayList<Integer> avail=new ArrayList<Integer>();
        int h=getCurrentMaxDegree(p);
        for(int i=0;i<=h;i++)
        {
            if(colourMatrix[x][i]==0)
            {
                avail.add(i);
            }
        }
        int e=avail.size();
        Integer[] d=avail.toArray(new Integer[e]);
        return d;
    }
    private int getCurrentMaxDegree(Graph p)                    //function to return the current maximum degree of a Graph
    {
        boolean[][] f=p.getAdjacencyMatrix();
        int s=0;
        for(int i=0;i<f[0].length;i++)
        {
            if(f[0][i]==true)
            {
                s=s+1;
            }
        }
        int u=0;
        for(int i=1;i<f[0].length;i++)
        {
            u=0;
            for(int j=0;j<f[0].length;j++)
            {
                if(f[i][j]==true)
                {
                    u=u+1;
                }
            }
            if(u>s)
            {
                s=u;
            }
        }
        return s;
    }
    private boolean[][] getAdjacencyMatrix()                //function to return adjacencyMatrix of the Graph object
    {
        return this.adjacentMatrix;
    }
    private boolean allLevelAssigned()                      //function to check if all edges have been assigned colours
    {
        for(int i=0;i<edges.size();i++)
        {
            if(!((edges.get(i)).isLevelSet()))
            {
                return false;
            }
        }
        return true;
    }
    private boolean isLevelAssigned(int x, int y)           //function to check whether level is assigned to an edge
    {
        boolean h=false;
        int r=getEdgeIndex(x,y);
        h=(edges.get(r)).isLevelSet();
        return h;
    }
    private int getEdgeIndex(int x,int y)                   //function to return index of an edge fron "edges" as equals function is not defined in Edge
    {
        int n=-1;
        for(int i=0;i<edges.size();i++)
        {
            if((((edges.get(i)).x==x) && ((edges.get(i)).y==y)) || (((edges.get(i)).x==y) && ((edges.get(i)).y==x)))
            {
                n=i;
            }
        }
        return n;
    }
    private void assignLevels(int x)                        //function to assign level to all edges of the Graph called recurrsively, x keeps track of levels
    {
        if(x==0)
        {
            int n=vertexCount;
            int q;
            outerloop:
            for(int i=0;i<n-2;i++)
            {
                for(int j=1;j<n-1;j++)
                {
                    for(int k=j+1;k<n;k++)
                    {
                        if(isEdge(i,j) && isEdge(j,k) && isEdge(k,i))
                        {
                            q=getEdgeIndex(i,j);
                            (edges.get(q)).setLevel(x,-1,-1);
                            q=getEdgeIndex(k,j);
                            (edges.get(q)).setLevel(x,-1,-1);
                            q=getEdgeIndex(i,k);
                            (edges.get(q)).setLevel(x,-1,-1);
                            break outerloop;
                        }
                    }
                }
            }
            if(!(allLevelAssigned()))
            {
                assignLevels(x+1);
            }
        }
        else
        {
            if(!(allLevelAssigned()))
            {
                int m,n,q;
                for(int i=0;i<edges.size();i++)
                {
                    if(((edges.get(i)).getLevel())==(x-1))
                    {
                        n=(edges.get(i)).x;
                        m=(edges.get(i)).y;
                        for(int j=0;j<vertexCount;j++)
                        {
                            if(j!=n && j!=m)
                            {
                                if(isEdge(n,j) && isEdge(m,j))
                                {
                                    if(!(isLevelAssigned(n,j)))
                                    {
                                        q=getEdgeIndex(n,j);
                                        (edges.get(q)).setLevel(x,n,m);
                                    }
                                    if(!(isLevelAssigned(m,j)))
                                    {
                                        q=getEdgeIndex(m,j);
                                        (edges.get(q)).setLevel(x,n,m);
                                    }
                                }
                            }
                        }
                    }
                }
                assignLevels(x+1);
            }
        }
    }
    private boolean checkColouringLevel(int f)              //funtion to check if all the edges at a given level are assigned colours or not
    {
        for(int i=0;i<edges.size();i++)
        {
            if(((edges.get(i)).getLevel())==f)
            {
                if(!((edges.get(i)).isColourSet()))
                {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isGraphColoured()                       //function to check if the whole Graph is coloured
    {
        for(int i=0;i<edges.size();i++)
        {
            if(!((edges.get(i)).isColourSet()))
            {
                return false;
            }
        }
        return true;
    }
    private void assignColours(int r)                       //sunction to assign colours to edges recurrsively, r keeps track of level
    {
        if(r==0)                                            //brute force algorithm to colour level 0
        {
            int k=0;
            for(int i=0;i<edges.size();i++)
            {
                if((edges.get(i)).getLevel()==0)
                {
                    if(k<3)
                    {
                        (edges.get(i)).setColour(k);
                        setEdgeColour(edges.get(i),k);
                        k++;
                    }
                    else
                    {
                        break;
                    }
                }
            }
            r++;
            if(!(isGraphColoured()))
            {
                assignColours(r);
            }
        }
        else if(r==1)                                   //using generalised lemma2 to colour ears of a particular zero base at a time
        {
            int x1=-1;
            int t1=-1;
            int l1=0;
            int x2=-1;
            int t2=-1;
            int l2=0;
            int x3=-1;
            int t3=-1;
            int l3=0;
            ArrayList<Edge> ed=new ArrayList<Edge>();
            ArrayList<Edge> ed1=new ArrayList<Edge>();
            ArrayList<Edge> ed2=new ArrayList<Edge>();
            ArrayList<Edge> ed3=new ArrayList<Edge>();
            for(int i=0;i<edges.size();i++)
            {
                if(((edges.get(i)).isColourSet()))
                {
                    ed.add(edges.get(i));
                }
            }
            for(int i=0;i<edges.size();i++)
            {
                if((edges.get(i)).getLevel()==1)
                {
                    if(!((edges.get(i)).isColourSet()))
                    {
                        if((l1==0) && (l2==0) && (l3==0))
                        {
                            ed1.add(edges.get(i));
                            x1=(edges.get(i)).baseX;
                            t1=(edges.get(i)).baseY;
                            l1++;
                        }
                        else
                        {
                            if((((edges.get(i)).baseX==x1) && ((edges.get(i)).baseY==t1)) || (((edges.get(i)).baseX==t1) && ((edges.get(i)).baseY==x1)))
                            {
                                ed1.add(edges.get(i));
                                l1++;
                            }
                            else
                            {
                                if((l2==0) && (l3==0))
                                {
                                    ed2.add(edges.get(i));
                                    x2=(edges.get(i)).baseX;
                                    t2=(edges.get(i)).baseY;
                                    l2++;
                                }
                                else
                                {
                                    if((((edges.get(i)).baseX==x2) && ((edges.get(i)).baseY==t2)) || (((edges.get(i)).baseX==t2) && ((edges.get(i)).baseY==x2)))
                                    {
                                        ed2.add(edges.get(i));
                                        l2++;
                                    }
                                    else
                                    {
                                        if(l3==0)
                                        {
                                            ed3.add(edges.get(i));
                                            x3=(edges.get(i)).baseX;
                                            t3=(edges.get(i)).baseY;
                                            l3++;
                                        }
                                        else
                                        {
                                            if((((edges.get(i)).baseX==x3) && ((edges.get(i)).baseY==t3)) || (((edges.get(i)).baseX==t3) && ((edges.get(i)).baseY==x3)))
                                            {
                                                ed3.add(edges.get(i));
                                                l3++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int x,t;
            x=-1;
            t=-1;
            int k1=l1/2;
            int k2=l2/2;
            int k3=l3/2;
            int k11=-1;
            if(k1==0)
            {
                if(k2==0)
                {
                    k11=k3;
                }
                else if(k3==0)
                {
                    k11=k2;
                }
                else
                {
                    k11=Math.min(k2,k3);
                }
            }
            else if(k2==0)
            {
                if(k1==0)
                {
                    k11=k3;
                }
                else if(k3==0)
                {
                    k11=k1;
                }
                else
                {
                    k11=Math.min(k1,k3);
                }
            }
            else if(k3==0)
            {
                if(k2==0)
                {
                    k11=k1;
                }
                else if(k1==0)
                {
                    k11=k2;
                }
                else
                {
                    k11=Math.min(k1,k2);
                }
            }
            else
            {
                k11=Math.min(Math.min(k1,k2),k3);
            }
            if(k11==k1)
            {
                for(int i=0;i<ed1.size();i++)
                {
                    ed.add(ed1.get(i));
                }
                x=x1;
                t=t1;
            }
            else if(k11==k2)
            {
                for(int i=0;i<ed2.size();i++)
                {
                    ed.add(ed2.get(i));
                }
                x=x2;
                t=t2;
            }
            else if(k11==k3)
            {
                for(int i=0;i<ed3.size();i++)
                {
                    ed.add(ed3.get(i));
                }
                x=x3;
                t=t3;
            }
            int u=-1;
            for(int i=0;i<edges.size();i++)
            {
                if(((edges.get(i)).getLevel())==0)
                {
                    if(((edges.get(i)).x!=x) && (((edges.get(i)).x)!=t))
                    {
                        u=(edges.get(i)).x;
                        break;
                    }
                    else if(((edges.get(i)).y!=x) && (((edges.get(i)).y)!=t))
                    {
                        u=(edges.get(i)).y;
                        break;
                    }
                }
            }
            Graph g=new Graph(vertexCount);
            for(int i=0;i<ed.size();i++)
            {
                g.addEdge(ed.get(i));
            }
            Integer []c0=availableColoursOnVertex(x,g);
            Integer []c1=availableColoursOnVertex(t,g);
            ArrayList<Integer> c2=new ArrayList<Integer>();
            ArrayList<Integer> c00=new ArrayList<Integer>();
            ArrayList<Integer> c11=new ArrayList<Integer>();
            for(int i=0;i<c0.length;i++)
            {
                for(int xs=0;xs<c1.length;xs++)
                {
                    if(c0[i]==c1[xs])
                    {
                        c2.add(c0[i]);
                    }
                }
            }
            for(int i=0;i<c0.length;i++)
            {
                if((c2.indexOf(c0[i]))<0)
                {
                    c00.add(c0[i]);
                }
            }
            for(int i=0;i<c1.length;i++)
            {
                if((c2.indexOf(c1[i]))<0)
                {
                    c11.add(c1[i]);
                }
            }
            ArrayList<Integer> ne=new ArrayList<Integer>();
            for(int i=0;i<ed.size();i++)
            {
                if(((ed.get(i)).getLevel())==1)
                {
                    if(!((ed.get(i)).isColourSet()))
                    {
                        if((ed.get(i)).x==x)
                        {
                            if((ne.indexOf((ed.get(i)).y))<0)
                            {
                                ne.add((ed.get(i)).y);
                            }
                        }
                        else if((ed.get(i)).y==x)
                        {
                            if((ne.indexOf((ed.get(i)).x))<0)
                            {
                                ne.add((ed.get(i)).x);
                            }
                        }
                        else if((ed.get(i)).x==t)
                        {
                            if((ne.indexOf((ed.get(i)).y))<0)
                            {
                                ne.add((ed.get(i)).y);
                            }
                        }
                        else if(((ed.get(i)).y)==t)
                        {
                            if((ne.indexOf((ed.get(i)).x))<0)
                            {
                                ne.add((ed.get(i)).x);
                            }
                        }
                    }
                }
            }
            if(ne.size()==1)
            {
                if(level1==0)
                {
                    int lt=getEdgeIndex(t,ne.get(0));
                    int rt=getEdgeIndex(x,ne.get(0));
                    (edges.get(lt)).setColour(getEdgeColour(x,u));
                    (edges.get(rt)).setColour(c2.get(0));
                    setEdgeColour((edges.get(lt)),getEdgeColour(x,u));
                    setEdgeColour((edges.get(rt)),c2.get(0));
                    level1++;
                }
                else if(level1==1)
                {
                    int lt=getEdgeIndex(t,ne.get(0));
                    int rt=getEdgeIndex(x,ne.get(0));
                    if(c00.size()>0)
                    {
                        (edges.get(lt)).setColour(c1[0]);
                        (edges.get(rt)).setColour(c00.get(0));
                        setEdgeColour((edges.get(lt)),c1[0]);
                        setEdgeColour((edges.get(rt)),c00.get(0));
                    }
                    else
                    {
                        (edges.get(lt)).setColour(c11.get(0));
                        (edges.get(rt)).setColour(c0[0]);
                        setEdgeColour((edges.get(lt)),c11.get(0));
                        setEdgeColour((edges.get(rt)),c0[0]);
                    }
                    level1++;
                }
                else
                {
                    if(c2.size()>1)
                    {
                        int lt=getEdgeIndex(t,ne.get(0));
                        int rt=getEdgeIndex(x,ne.get(0));
                        (edges.get(lt)).setColour(c2.get(0));
                        (edges.get(rt)).setColour(c2.get(1));
                        setEdgeColour((edges.get(lt)),c2.get(0));
                        setEdgeColour((edges.get(rt)),c2.get(1));
                    }
                    else
                    {
                        int lt=getEdgeIndex(t,ne.get(0));
                        int rt=getEdgeIndex(x,ne.get(0));
                        (edges.get(lt)).setColour(c2.get(0));
                        (edges.get(rt)).setColour(c00.get(0));
                        setEdgeColour((edges.get(lt)),c2.get(0));
                        setEdgeColour((edges.get(rt)),c00.get(0));
                    }
                }
            }
            else if(ne.size()==2)
            {
                int lt=getEdgeIndex(x,ne.get(0));
                int rt=getEdgeIndex(t,ne.get(0));
                if(c2.size()>1)
                {
                    (edges.get(lt)).setColour(c2.get(0));
                    (edges.get(rt)).setColour(c2.get(1));
                    setEdgeColour((edges.get(lt)),c2.get(0));
                    setEdgeColour((edges.get(rt)),c2.get(1));
                    lt=getEdgeIndex(x,ne.get(1));
                    rt=getEdgeIndex(t,ne.get(1));
                    (edges.get(lt)).setColour(c2.get(1));
                    (edges.get(rt)).setColour(c2.get(2));
                    setEdgeColour((edges.get(lt)),c2.get(1));
                    setEdgeColour((edges.get(rt)),c2.get(2));
                }
                else
                {
                    (edges.get(lt)).setColour(c2.get(0));
                    (edges.get(rt)).setColour(c11.get(0));
                    setEdgeColour((edges.get(lt)),c2.get(0));
                    setEdgeColour((edges.get(rt)),c11.get(0));
                    lt=getEdgeIndex(x,ne.get(1));
                    rt=getEdgeIndex(t,ne.get(1));
                    (edges.get(lt)).setColour(c00.get(0));
                    (edges.get(rt)).setColour(c2.get(0));
                    setEdgeColour((edges.get(lt)),c00.get(0));
                    setEdgeColour((edges.get(rt)),c2.get(0));
                }
            }
            else
            {
                int lt;
                int count=0;
                for(int i=0;i<ne.size();i++)
                {
                    if(i<c2.size())
                    {
                        lt=getEdgeIndex(x,ne.get(i));
                        (edges.get(lt)).setColour(c2.get(i));
                        setEdgeColour(edges.get(lt),c2.get(i));
                    }
                    else
                    {
                        lt=getEdgeIndex(x,ne.get(i));
                        (edges.get(lt)).setColour(c00.get(count));
                        setEdgeColour(edges.get(lt),c00.get(count));
                        count++;
                    }
                }
                count=0;
                for(int i=0;i<ne.size();i++)
                {
                    if(i<((c2.size())-1))
                    {
                        lt=getEdgeIndex(t,ne.get(i));
                        (edges.get(lt)).setColour(c2.get(i+1));
                        setEdgeColour(edges.get(lt),c2.get(i+1));
                    }
                    else if(i==(ne.size()-1))
                    {
                        lt=getEdgeIndex(t,ne.get(i));
                        (edges.get(lt)).setColour(c2.get(0));
                        setEdgeColour(edges.get(lt),c2.get(0));
                    }
                    else
                    {
                        lt=getEdgeIndex(t,ne.get(i));
                        (edges.get(lt)).setColour(c11.get(count));
                        setEdgeColour(edges.get(lt),c11.get(count));
                        count++;
                    }
                }
            }
            if(!(isGraphColoured()))
            {
                if(!(checkColouringLevel(r)))
                {
                    assignColours(r);
                }
                else
                {
                    r++;
                    assignColours(r);
                }
            }
        }
        else                                //assign colours to edges with level>=2
        {
            int l=0;
            int l2=0;
            int x=-1;
            int t=-1;
            int u=-1;
            int pbx=-1;
            int pby=-1;
            int lx=-1;
            int ly=-1;
            int ux=-1;
            int uy=-1;
            ArrayList<Edge> ed=new ArrayList<Edge>();
            ArrayList<Edge> k1=new ArrayList<Edge>();
            ArrayList<Edge> k2=new ArrayList<Edge>();
            for(int i=0;i<edges.size();i++)
            {
                if(((edges.get(i)).isColourSet()))
                {
                    ed.add(edges.get(i));
                }
            }
            for(int i=0;i<edges.size();i++)
            {
                if((edges.get(i)).getLevel()==r)
                {
                    if(!((edges.get(i)).isColourSet()))
                    {
                        if(l==0)
                        {
                            ed.add(edges.get(i));
                            k1.add(edges.get(i));
                            x=(edges.get(i)).baseX;
                            t=(edges.get(i)).baseY;
                            int temp=getEdgeIndex(x,t);
                            pbx=(edges.get(temp)).baseX;
                            pby=(edges.get(temp)).baseY;
                            l++;
                        }
                        else
                        {
                            int temp1x=(edges.get(i)).baseX;
                            int temp1y=(edges.get(i)).baseY;
                            int temp2=getEdgeIndex(temp1x,temp1y);
                            int temp2x=(edges.get(temp2)).baseX;
                            int temp2y=(edges.get(temp2)).baseY;
                            if(((temp2x==pbx) && (temp2y==pby)) || (temp2x==pby) && (temp2y==pbx))
                            {
                                if((x==pbx) || (x==pby))
                                {
                                    if((temp1x==t) || (temp1y==t))
                                    {
                                        if(((temp1x==x) && (temp1y==t)) || ((temp1x==t) && (temp1y==x)))
                                        {
                                            l++;
                                            k1.add(edges.get(i));
                                            ed.add(edges.get(i));
                                        }
                                        else
                                        {
                                            if(l2==0)
                                            {
                                                if(temp1x==t)
                                                {
                                                    u=temp1y;
                                                }
                                                else
                                                {
                                                    u=temp1x;
                                                }
                                            }
                                            l2++;
                                            k2.add(edges.get(i));
                                            ed.add(edges.get(i));
                                        }
                                    }
                                }
                                else if((t==pbx) || (t==pby))
                                {
                                    if((temp1x==x) || (temp1y==x))
                                    {
                                        if(((temp1x==x) && (temp1y==t)) || ((temp1x==t) && (temp1y==x)))
                                        {
                                            l++;
                                            k1.add(edges.get(i));
                                            ed.add(edges.get(i));
                                        }
                                        else
                                        {
                                            if(l2==0)
                                            {
                                                if(temp1x==t)
                                                {
                                                    u=temp1y;
                                                }
                                                else
                                                {
                                                    u=temp1x;
                                                }
                                            }
                                            l2++;
                                            k2.add(edges.get(i));
                                            ed.add(edges.get(i));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            l=l/2;
            l2=l2/2;
            Graph g=new Graph(vertexCount);
            for(int i=0;i<ed.size();i++)
            {
                g.addEdge(ed.get(i));
            }
            if(pbx==x)
            {
                ly=pbx;
                uy=pby;
                ux=t;
                lx=t;
            }
            else if(pbx==t)
            {
                ly=t;
                uy=pby;
                ux=x;
                lx=x;
            }
            else if(pby==x)
            {
                ly=pby;
                uy=pbx;
                ux=t;
                lx=t;
            }
            else
            {
                ly=t;
                uy=pbx;
                ux=x;
                lx=x;
            }
            Integer c0[]=availableColoursOnVertex(ly,g);
            int cr=getEdgeColour(ux,uy);
            ArrayList<Integer> col=new ArrayList<Integer>();
            for(int j=0;j<c0.length;j++)
            {
                if(c0[j]!=cr)
                {
                    col.add(c0[j]);
                }
            }
            Integer c1[]=availableColoursOnVertex(uy,g);
            cr=getEdgeColour(lx,ly);
            ArrayList<Integer> col1=new ArrayList<Integer>();
            for(int j=0;j<c1.length;j++)
            {
                if(c1[j]!=cr)
                {
                    col1.add(c1[j]);
                }
            }
            Integer c2[]=availableColoursOnVertex(lx,g);
            cr=getEdgeColour(uy,ly);
            ArrayList<Integer> col2=new ArrayList<Integer>();
            for(int j=0;j<c2.length;j++)
            {
                if(c2[j]!=cr)
                {
                    col2.add(c2[j]);
                }
            }
            if(l==0 || l2==0)
            {
                if(l==1 || l2==1)
                {
                    for(int i=0;i<k1.size();i++)
                    {
                        if(((k1.get(i)).x==lx) || ((k1.get(i)).y==lx))
                        {
                            int lt=getEdgeIndex((k1.get(i)).x,(k1.get(i)).y);
                            (edges.get(lt)).setColour(getEdgeColour(pbx,pby));
                            setEdgeColour(edges.get(lt),getEdgeColour(pbx,pby));
                            if(i==0)
                            {
                                int rt=getEdgeIndex((k1.get(i+1)).x,(k1.get(i+1)).y);
                                (edges.get(rt)).setColour(col.get(0));
                                setEdgeColour(edges.get(rt),col.get(0));
                            }
                            else
                            {
                                int rt=getEdgeIndex((k1.get(i-1)).x,(k1.get(i-1)).y);
                                (edges.get(rt)).setColour(col.get(0));
                                setEdgeColour(edges.get(rt),col.get(0));
                            }
                        }
                    }
                }
                else if(l==2 || l2==2)
                {
                    int sum=0;
                    for(int i=0;i<k1.size();i++)
                    {
                        if(((k1.get(i)).x==lx) || ((k1.get(i)).y==lx))
                        {
                            if(sum==0)
                            {
                                for(int j=0;j<k1.size();j++)
                                {
                                    if(((k1.get(j)).x==ly) || ((k1.get(j)).x==ly))
                                    {
                                        if((((k1.get(j)).x)==((k1.get(i)).x)) || (((k1.get(j)).x)==((k1.get(i)).y)) || (((k1.get(j)).y)==((k1.get(i)).x)) || (((k1.get(j)).y)==((k1.get(i)).y)))
                                        {
                                            int rt=getEdgeIndex((k1.get(i)).x,(k1.get(i)).y);
                                            int lt=getEdgeIndex((k1.get(j)).x,(k1.get(j)).y);
                                            (edges.get(rt)).setColour(getEdgeColour(ly,uy));
                                            setEdgeColour(edges.get(rt),getEdgeColour(ly,uy));
                                            (edges.get(lt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(lt),col.get(0));
                                            break;
                                        }
                                    }
                                }
                                sum++;
                            }
                            else
                            {
                                for(int j=0;j<k1.size();j++)
                                {
                                    if(((k1.get(j)).x==ly) || ((k1.get(j)).x==ly))
                                    {
                                        if((((k1.get(j)).x)==((k1.get(i)).x)) || (((k1.get(j)).x)==((k1.get(i)).y)) || (((k1.get(j)).y)==((k1.get(i)).x)) || (((k1.get(j)).y)==((k1.get(i)).y)))
                                        {
                                            int rt=getEdgeIndex((k1.get(i)).x,(k1.get(i)).y);
                                            int lt=getEdgeIndex((k1.get(j)).x,(k1.get(j)).y);
                                            (edges.get(rt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(rt),col.get(0));
                                            (edges.get(lt)).setColour(col.get(1));
                                            setEdgeColour(edges.get(lt),col.get(1));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    int sum=0;
                    for(int i=0;i<k1.size();i++)
                    {
                        if(((k1.get(i)).x==lx) || ((k1.get(i)).y==lx))
                        {
                            for(int j=0;j<k1.size();j++)
                            {
                                if(((k1.get(j)).x==ly) || ((k1.get(j)).y==ly))
                                {
                                    if((((k1.get(j)).x)==((k1.get(i)).x)) || (((k1.get(j)).x)==((k1.get(i)).y)) || (((k1.get(j)).y)==((k1.get(i)).x)) || (((k1.get(j)).y)==((k1.get(i)).y)))
                                    {
                                        int rt=getEdgeIndex((k1.get(i)).x,(k1.get(i)).x);
                                        int lt=getEdgeIndex((k1.get(j)).x,(k1.get(j)).x);
                                        if(sum==((k1.size()/2)-1))
                                        {
                                            (edges.get(rt)).setColour(col.get(sum));
                                            setEdgeColour(edges.get(rt),col.get(sum));
                                            (edges.get(lt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(lt),col.get(0));
                                        }
                                        else
                                        {
                                            (edges.get(rt)).setColour(col.get(sum));
                                            setEdgeColour(edges.get(rt),col.get(sum));
                                            (edges.get(lt)).setColour(col.get(sum+1));
                                            setEdgeColour(edges.get(lt),col.get(sum+1));
                                        }
                                        sum++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if(l==1 || l2==1)
            {
                if(k1.size()==2)
                {
                    for(int i=0;i<k1.size();i++)
                    {
                        if(((k1.get(i)).x==lx) || ((k1.get(i)).y==lx))
                        {
                            int lt=getEdgeIndex((k1.get(i)).x,(k1.get(i)).y);
                            (edges.get(lt)).setColour(getEdgeColour(pbx,pby));
                            setEdgeColour(edges.get(lt),getEdgeColour(pbx,pby));
                            if(i==0)
                            {
                                int rt=getEdgeIndex((k1.get(i+1)).x,(k1.get(i+1)).y);
                                (edges.get(rt)).setColour(col.get(0));
                                setEdgeColour(edges.get(rt),col.get(0));
                            }
                            else
                            {
                                int rt=getEdgeIndex((k1.get(i-1)).x,(k1.get(i-1)).y);
                                (edges.get(rt)).setColour(col.get(0));
                                setEdgeColour(edges.get(rt),col.get(0));
                            }
                        }
                    }
                    if(l==1 && l2==1)
                    {
                        if(((k2.get(0)).x==lx) || ((k2.get(0)).y==lx))
                        {
                            int lt=getEdgeIndex((k2.get(0)).x,(k2.get(0)).y);
                            (edges.get(lt)).setColour(col2.get(0));
                            setEdgeColour(edges.get(lt),col2.get(0));
                            int rt=getEdgeIndex((k2.get(1)).x,(k2.get(1)).y);
                            (edges.get(rt)).setColour(col1.get(0));
                            setEdgeColour(edges.get(rt),col1.get(0));
                        }
                        else
                        {
                            int lt=getEdgeIndex((k2.get(1)).x,(k2.get(1)).y);
                            (edges.get(lt)).setColour(col2.get(0));
                            setEdgeColour(edges.get(lt),col2.get(0));
                            int rt=getEdgeIndex((k2.get(0)).x,(k2.get(0)).y);
                            (edges.get(rt)).setColour(col1.get(0));
                            setEdgeColour(edges.get(rt),col1.get(0));
                        }
                    }
                    else if(l==2 || l2==2)
                    {
                        ArrayList<Integer> c21=new ArrayList<Integer>();
                        for(int kt=0;kt<col2.size();kt++)
                        {
                            if((col1.indexOf(col2.get(kt)))>=0)
                            {
                                c21.add(col2.get(kt));
                            }
                        }
                        int c=0;
                        for(int kt=0;kt<k2.size();kt++)
                        {
                            if(((k2.get(kt)).x==lx) || ((k2.get(kt)).y==lx))
                            {
                                int lt=getEdgeIndex((k2.get(kt)).x,(k2.get(kt)).y);
                                (edges.get(lt)).setColour(c21.get(c));
                                setEdgeColour(edges.get(lt),c21.get(c));
                                for(int kk=0;kk<k2.size();kk++)
                                {
                                    if((k2.get(kk)).x==uy)
                                    {
                                        if(((k2.get(kk)).y==(k2.get(kt)).x) || ((k2.get(kk)).y==(k2.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k2.get(kk)).x,(k2.get(kk)).y);
                                            (edges.get(rt)).setColour(c21.get(c+1));
                                            setEdgeColour(edges.get(rt),c21.get(c+1));
                                        }
                                    }
                                    else if((k2.get(kk)).y==uy)
                                    {
                                        if(((k2.get(kk)).x==(k2.get(kt)).x) || ((k2.get(kk)).x==(k2.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k2.get(kk)).x,(k2.get(kk)).y);
                                            (edges.get(rt)).setColour(c21.get(c+1));
                                            setEdgeColour(edges.get(rt),c21.get(c+1));
                                        }                                       
                                    }
                                }
                                c++;
                            }
                        }
                    }
                    else
                    {
                        ArrayList<Integer> lvx=new ArrayList<Integer>();
                        ArrayList<Integer> lwa=new ArrayList<Integer>();
                        for(int kt=0;kt<((k2.size())/2);kt++)
                        {
                            lvx.add(col1.get(kt));
                            lwa.add(col2.get(kt));
                        }
                        ArrayList<Integer> ck2=new ArrayList<Integer>();
                        ArrayList<Integer> lvxf=new ArrayList<Integer>();
                        ArrayList<Integer> lwaf=new ArrayList<Integer>();
                        for(int kt=0;kt<lvx.size();kt++)
                        {
                            if((lwa.indexOf(lvx.get(kt)))>=0)
                            {
                                ck2.add(lvx.get(kt));
                            }
                            else
                            {
                                lvxf.add(lvx.get(kt));
                            }
                            if((lvx.indexOf(lwa.get(kt)))<0)
                            {
                                lwaf.add(lwa.get(kt));
                            }
                        }
                        ArrayList<Integer> ne=new ArrayList<Integer>();
                        for(int i=0;i<ed.size();i++)
                        {
                            if(((ed.get(i)).getLevel())==r)
                            {
                                if(!((ed.get(i)).isColourSet()))
                                {
                                    if((ed.get(i)).x==uy)
                                    {
                                        if((ne.indexOf((ed.get(i)).y))<0)
                                        {
                                            ne.add((ed.get(i)).y);
                                        }
                                    }
                                    else if((ed.get(i)).y==uy)
                                    {
                                        if((ne.indexOf((ed.get(i)).x))<0)
                                        {
                                            ne.add((ed.get(i)).x);
                                        }
                                    }
                                }
                            }
                        }
                        int co=0;
                        for(int i=0;i<ne.size();i++)
                        {
                            if(i<ck2.size())
                            {
                                if(i==((ne.size())-1))
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    (edges.get(lt)).setColour(ck2.get(0));
                                    setEdgeColour(edges.get(lt),ck2.get(0));
                                }
                                else
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    if(i==(ck2.size()-1))
                                    {
                                        (edges.get(lt)).setColour(lwaf.get(0));
                                        setEdgeColour(edges.get(lt),lwaf.get(0));
                                    }
                                    else
                                    {
                                        (edges.get(lt)).setColour(ck2.get(i+1));
                                        setEdgeColour(edges.get(lt),ck2.get(i+1));
                                    }
                                }
                            }
                            else
                            {
                                if(i==((ne.size())-1))
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(lvxf.get(co));
                                    setEdgeColour(edges.get(rt),lvxf.get(co));
                                    (edges.get(lt)).setColour(ck2.get(0));
                                    setEdgeColour(edges.get(lt),ck2.get(0));
                                }
                                else
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(lvxf.get(co));
                                    setEdgeColour(edges.get(rt),lvxf.get(co));
                                    (edges.get(lt)).setColour(lwaf.get(co+1));
                                    setEdgeColour(edges.get(lt),lwaf.get(co+1));
                                    co++;
                                }
                            }
                        }
                    }
                }
                else
                {
                    for(int i=0;i<k2.size();i++)
                    {
                        if(((k2.get(i)).x==lx) || ((k2.get(i)).y==lx))
                        {
                            int lt=getEdgeIndex((k2.get(i)).x,(k2.get(i)).y);
                            (edges.get(lt)).setColour(getEdgeColour(pbx,pby));
                            setEdgeColour(edges.get(lt),getEdgeColour(pbx,pby));
                            if(i==0)
                            {
                                int rt=getEdgeIndex((k2.get(i+1)).x,(k2.get(i+1)).y);
                                (edges.get(rt)).setColour(col.get(0));
                                setEdgeColour(edges.get(rt),col.get(0));
                            }
                            else
                            {
                                int rt=getEdgeIndex((k2.get(i-1)).x,(k2.get(i-1)).y);
                                (edges.get(rt)).setColour(col.get(0));
                                setEdgeColour(edges.get(rt),col.get(0));
                            }
                        }
                    }
                    if(l==1 && l2==1)
                    {
                        if(((k1.get(0)).x==lx) || ((k1.get(0)).y==lx))
                        {
                            int lt=getEdgeIndex((k1.get(0)).x,(k1.get(0)).y);
                            (edges.get(lt)).setColour(col2.get(0));
                            setEdgeColour(edges.get(lt),col2.get(0));
                            int rt=getEdgeIndex((k1.get(1)).x,(k1.get(1)).y);
                            (edges.get(rt)).setColour(col1.get(0));
                            setEdgeColour(edges.get(rt),col1.get(0));
                        }
                        else
                        {
                            int lt=getEdgeIndex((k1.get(1)).x,(k1.get(1)).y);
                            (edges.get(lt)).setColour(col2.get(0));
                            setEdgeColour(edges.get(lt),col2.get(0));
                            int rt=getEdgeIndex((k1.get(0)).x,(k1.get(0)).y);
                            (edges.get(rt)).setColour(col1.get(0));
                            setEdgeColour(edges.get(rt),col1.get(0));
                        }
                    }
                    else if(l==2 || l2==2)
                    {
                        ArrayList<Integer> c20=new ArrayList<Integer>();
                        for(int kt=0;kt<col2.size();kt++)
                        {
                            if((col.indexOf(col2.get(kt)))>=0)
                            {
                                c20.add(col2.get(kt));
                            }
                        }
                        int c=0;
                        for(int kt=0;kt<k1.size();kt++)
                        {
                            if(((k1.get(kt)).x==lx) || ((k1.get(kt)).y==lx))
                            {
                                int lt=getEdgeIndex((k1.get(kt)).x,(k1.get(kt)).y);
                                (edges.get(lt)).setColour(c20.get(c));
                                setEdgeColour(edges.get(lt),c20.get(c));
                                for(int kk=0;kk<k1.size();kk++)
                                {
                                    if((k1.get(kk)).x==ly)
                                    {
                                        if(((k1.get(kk)).y==(k1.get(kt)).x) || ((k1.get(kk)).y==(k1.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k1.get(kk)).x,(k1.get(kk)).y);
                                            (edges.get(rt)).setColour(c20.get(c+1));
                                            setEdgeColour(edges.get(rt),c20.get(c+1));
                                        }
                                    }
                                    else if((k1.get(kk)).y==ly)
                                    {
                                        if(((k1.get(kk)).x==(k1.get(kt)).x) || ((k1.get(kk)).x==(k1.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k1.get(kk)).x,(k1.get(kk)).y);
                                            (edges.get(rt)).setColour(c20.get(c+1));
                                            setEdgeColour(edges.get(rt),c20.get(c+1));
                                        }                                       
                                    }
                                }
                                c++;
                            }
                        }
                    }
                    else
                    {
                        ArrayList<Integer> luy=new ArrayList<Integer>();
                        ArrayList<Integer> lwa=new ArrayList<Integer>();
                        for(int kt=0;kt<((k1.size())/2);kt++)
                        {
                            luy.add(col.get(kt));
                            lwa.add(col2.get(kt));
                        }
                        ArrayList<Integer> ck2=new ArrayList<Integer>();
                        ArrayList<Integer> luyf=new ArrayList<Integer>();
                        ArrayList<Integer> lwaf=new ArrayList<Integer>();
                        for(int kt=0;kt<luy.size();kt++)
                        {
                            if((lwa.indexOf(luy.get(kt)))>=0)
                            {
                                ck2.add(luy.get(kt));
                            }
                            else
                            {
                                luyf.add(luy.get(kt));
                            }
                            if((luy.indexOf(lwa.get(kt)))<0)
                            {
                                lwaf.add(lwa.get(kt));
                            }
                        }
                        ArrayList<Integer> ne=new ArrayList<Integer>();
                        for(int i=0;i<ed.size();i++)
                        {
                            if(((ed.get(i)).getLevel())==r)
                            {
                                if(!((ed.get(i)).isColourSet()))
                                {
                                    if((ed.get(i)).x==ly)
                                    {
                                        if((ne.indexOf((ed.get(i)).y))<0)
                                        {
                                            ne.add((ed.get(i)).y);
                                        }
                                    }
                                    else if((ed.get(i)).y==ly)
                                    {
                                        if((ne.indexOf((ed.get(i)).x))<0)
                                        {
                                            ne.add((ed.get(i)).x);
                                        }
                                    }
                                }
                            }
                        }
                        int co=0;
                        for(int i=0;i<ne.size();i++)
                        {
                            if(i<ck2.size())
                            {
                                if(i==((ne.size())-1))
                                {
                                    int rt=getEdgeIndex(ly,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    (edges.get(lt)).setColour(ck2.get(0));
                                    setEdgeColour(edges.get(lt),ck2.get(0));
                                }
                                else
                                {
                                    int rt=getEdgeIndex(ly,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    if(i==(ck2.size()-1))
                                    {
                                        (edges.get(lt)).setColour(lwaf.get(0));
                                        setEdgeColour(edges.get(lt),lwaf.get(0));
                                    }
                                    else
                                    {
                                        (edges.get(lt)).setColour(ck2.get(i+1));
                                        setEdgeColour(edges.get(lt),ck2.get(i+1));
                                    }
                                }
                            }
                            else
                            {
                                if(i==((ne.size())-1))
                                {
                                    int rt=getEdgeIndex(ly,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(luyf.get(co));
                                    setEdgeColour(edges.get(rt),luyf.get(co));
                                    (edges.get(lt)).setColour(ck2.get(0));
                                    setEdgeColour(edges.get(lt),ck2.get(0));
                                }
                                else
                                {
                                    int rt=getEdgeIndex(ly,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(luyf.get(co));
                                    setEdgeColour(edges.get(rt),luyf.get(co));
                                    (edges.get(lt)).setColour(lwaf.get(co+1));
                                    setEdgeColour(edges.get(lt),lwaf.get(co+1));
                                    co++;
                                }
                            }
                        }
                    }
                }
            }
            else if(l==2 || l2==2)
            {
                if(k1.size()==4)
                {
                    if(l==2 && l2==2)
                    {
                        int z=0;
                        for(int kt=0;kt<k1.size();kt++)
                        {
                            if(((k1.get(kt)).x==lx) || ((k1.get(kt)).y==lx))
                            {
                                for(int kk=0;kk<k1.size();kk++)
                                {
                                    if((((k1.get(kt)).x==ly) && ((k1.get(kt)).y==lx)) || (((k1.get(kt)).x==lx) && ((k1.get(kt)).y==ly))) 
                                    {
                                        int lt=getEdgeIndex((k1.get(kt)).x,(k1.get(kt)).y);
                                        int rt=getEdgeIndex((k1.get(kk)).x,(k1.get(kk)).y);
                                        if(z==0)
                                        {
                                            (edges.get(lt)).setColour(getEdgeColour(pbx,pby));
                                            setEdgeColour(edges.get(lt),getEdgeColour(pbx,pby));
                                            (edges.get(rt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(rt),col.get(0));
                                            z++;
                                        }
                                        else
                                        {
                                            (edges.get(lt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(lt),col.get(0));
                                            (edges.get(rt)).setColour(col.get(1));
                                            setEdgeColour(edges.get(rt),col.get(1));
                                        }
                                    }
                                }
                            }
                        }
                        ArrayList<Integer> vwc=new ArrayList<Integer>();
                        for(int kt=0;kt<col1.size();kt++)
                        {
                            if((col2.indexOf(col1.get(kt)))>=0)
                            {
                                vwc.add(col1.get(kt));
                            }
                        }
                        int c=0;
                        for(int kt=0;kt<k2.size();kt++)
                        {
                            if(((k2.get(kt)).x==lx) || ((k2.get(kt)).y==lx))
                            {
                                int lt=getEdgeIndex((k2.get(kt)).x,(k2.get(kt)).y);
                                (edges.get(lt)).setColour(vwc.get(c));
                                setEdgeColour(edges.get(lt),vwc.get(c));
                                for(int kk=0;kk<k2.size();kk++)
                                {
                                    if((k2.get(kk)).x==uy)
                                    {
                                        if(((k2.get(kk)).y==(k2.get(kt)).x) || ((k2.get(kk)).y==(k2.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k2.get(kk)).x,(k2.get(kk)).y);
                                            (edges.get(rt)).setColour(vwc.get(c+1));
                                            setEdgeColour(edges.get(rt),vwc.get(c+1));
                                        }
                                    }
                                    else if((k2.get(kk)).y==uy)
                                    {
                                        if(((k2.get(kk)).x==(k2.get(kt)).x) || ((k2.get(kk)).x==(k2.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k2.get(kk)).x,(k2.get(kk)).y);
                                            (edges.get(rt)).setColour(vwc.get(c+1));
                                            setEdgeColour(edges.get(rt),vwc.get(c+1));
                                        }                                       
                                    }
                                }
                                c++;
                            }
                        }
                    }
                    else
                    {
                        int z=0;
                        for(int kt=0;kt<k1.size();kt++)
                        {
                            if(((k1.get(kt)).x==lx) || ((k1.get(kt)).y==lx))
                            {
                                for(int kk=0;kk<k1.size();kk++)
                                {
                                    if((((k1.get(kt)).x==ly) && ((k1.get(kt)).y==lx)) || (((k1.get(kt)).x==lx) && ((k1.get(kt)).y==ly))) 
                                    {
                                        int lt=getEdgeIndex((k1.get(kt)).x,(k1.get(kt)).y);
                                        int rt=getEdgeIndex((k1.get(kk)).x,(k1.get(kk)).y);
                                        if(z==0)
                                        {
                                            (edges.get(lt)).setColour(getEdgeColour(pbx,pby));
                                            setEdgeColour(edges.get(lt),getEdgeColour(pbx,pby));
                                            (edges.get(rt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(rt),col.get(0));
                                            z++;
                                        }
                                        else
                                        {
                                            (edges.get(lt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(lt),col.get(0));
                                            (edges.get(rt)).setColour(col.get(1));
                                            setEdgeColour(edges.get(rt),col.get(1));
                                        }
                                    }
                                }
                            }
                        }
                        Integer cly[]=availableColoursOnVertex(ly,g);
                        Integer clx[]=availableColoursOnVertex(lx,g);
                        ArrayList<Integer> lvx=new ArrayList<Integer>();
                        ArrayList<Integer> lwa=new ArrayList<Integer>();
                        for(int kt=0;kt<((k2.size())/2);kt++)
                        {
                            lvx.add(cly[kt]);
                            lwa.add(clx[kt]);
                        }
                        ArrayList<Integer> ck2=new ArrayList<Integer>();
                        ArrayList<Integer> lvxf=new ArrayList<Integer>();
                        ArrayList<Integer> lwaf=new ArrayList<Integer>();
                        for(int kt=0;kt<lvx.size();kt++)
                        {
                            if((lwa.indexOf(lvx.get(kt)))>=0)
                            {
                                ck2.add(lvx.get(kt));
                            }
                            else
                            {
                                lvxf.add(lvx.get(kt));
                            }
                            if((lvx.indexOf(lwa.get(kt)))<0)
                            {
                                lwaf.add(lwa.get(kt));
                            }
                        }
                        ArrayList<Integer> ne=new ArrayList<Integer>();
                        for(int i=0;i<ed.size();i++)
                        {
                            if(((ed.get(i)).getLevel())==r)
                            {
                                if(!((ed.get(i)).isColourSet()))
                                {
                                    if((ed.get(i)).x==uy)
                                    {
                                        if((ne.indexOf((ed.get(i)).y))<0)
                                        {
                                            ne.add((ed.get(i)).y);
                                        }
                                    }
                                    else if((ed.get(i)).y==uy)
                                    {
                                        if((ne.indexOf((ed.get(i)).x))<0)
                                        {
                                            ne.add((ed.get(i)).x);
                                        }
                                    }
                                }
                            }
                        }
                        int co=0;
                        for(int i=0;i<ne.size();i++)
                        {
                            if(i<ck2.size())
                            {
                                if(i==((ne.size())-1))
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    (edges.get(lt)).setColour(ck2.get(0));
                                    setEdgeColour(edges.get(lt),ck2.get(0));
                                }
                                else
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    if(i==(ck2.size()-1))
                                    {
                                        (edges.get(lt)).setColour(lwaf.get(0));
                                        setEdgeColour(edges.get(lt),lwaf.get(0));
                                    }
                                    else
                                    {
                                        (edges.get(lt)).setColour(ck2.get(i+1));
                                        setEdgeColour(edges.get(lt),ck2.get(i+1));
                                    }
                                }
                            }
                            else
                            {
                                if(i==((ne.size())-1))
                                {
                                    if(ck2.size()>0)
                                    {
                                        int rt=getEdgeIndex(uy,ne.get(i));
                                        int lt=getEdgeIndex(lx,ne.get(i));
                                        (edges.get(rt)).setColour(lvxf.get(co));
                                        setEdgeColour(edges.get(rt),lvxf.get(co));
                                        (edges.get(lt)).setColour(ck2.get(0));
                                        setEdgeColour(edges.get(lt),ck2.get(0));
                                    }
                                    else
                                    {
                                        int rt=getEdgeIndex(uy,ne.get(i));
                                        int lt=getEdgeIndex(lx,ne.get(i));
                                        (edges.get(rt)).setColour(lvxf.get(co));
                                        setEdgeColour(edges.get(rt),lvxf.get(co));
                                        (edges.get(lt)).setColour(lwaf.get(0));
                                        setEdgeColour(edges.get(lt),lwaf.get(0));
                                    }
                                }
                                else
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(lvxf.get(co));
                                    setEdgeColour(edges.get(rt),lvxf.get(co));
                                    (edges.get(lt)).setColour(lwaf.get(co+1));
                                    setEdgeColour(edges.get(lt),lwaf.get(co+1));
                                    co++;
                                }
                            }
                        }
                    }
                }
                else
                {
                    if(l==2 && l2==2)
                    {
                        int z=0;
                        for(int kt=0;kt<k2.size();kt++)
                        {
                            if(((k2.get(kt)).x==lx) || ((k2.get(kt)).y==lx))
                            {
                                for(int kk=0;kk<k1.size();kk++)
                                {
                                    if((((k2.get(kt)).x==uy) && ((k2.get(kt)).y==lx)) || (((k2.get(kt)).x==lx) && ((k2.get(kt)).y==uy))) 
                                    {
                                        int lt=getEdgeIndex((k2.get(kt)).x,(k2.get(kt)).y);
                                        int rt=getEdgeIndex((k2.get(kk)).x,(k2.get(kk)).y);
                                        if(z==0)
                                        {
                                            (edges.get(lt)).setColour(getEdgeColour(pbx,pby));
                                            setEdgeColour(edges.get(lt),getEdgeColour(pbx,pby));
                                            (edges.get(rt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(rt),col.get(0));
                                            z++;
                                        }
                                        else
                                        {
                                            (edges.get(lt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(lt),col.get(0));
                                            (edges.get(rt)).setColour(col.get(1));
                                            setEdgeColour(edges.get(rt),col.get(1));
                                        }
                                    }
                                }
                            }
                        }
                        ArrayList<Integer> uwc=new ArrayList<Integer>();
                        for(int kt=0;kt<col.size();kt++)
                        {
                            if((col2.indexOf(col.get(kt)))>=0)
                            {
                                uwc.add(col.get(kt));
                            }
                        }
                        int c=0;
                        for(int kt=0;kt<k1.size();kt++)
                        {
                            if(((k1.get(kt)).x==lx) || ((k1.get(kt)).y==lx))
                            {
                                int lt=getEdgeIndex((k1.get(kt)).x,(k1.get(kt)).y);
                                (edges.get(lt)).setColour(uwc.get(c));
                                setEdgeColour(edges.get(lt),uwc.get(c));
                                for(int kk=0;kk<k1.size();kk++)
                                {
                                    if((k1.get(kk)).x==ly)
                                    {
                                        if(((k1.get(kk)).y==(k1.get(kt)).x) || ((k1.get(kk)).y==(k1.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k1.get(kk)).x,(k1.get(kk)).y);
                                            (edges.get(rt)).setColour(uwc.get(c+1));
                                            setEdgeColour(edges.get(rt),uwc.get(c+1));
                                        }
                                    }
                                    else if((k1.get(kk)).y==ly)
                                    {
                                        if(((k1.get(kk)).x==(k1.get(kt)).x) || ((k1.get(kk)).x==(k1.get(kt)).y))
                                        {
                                            int rt=getEdgeIndex((k1.get(kk)).x,(k1.get(kk)).y);
                                            (edges.get(rt)).setColour(uwc.get(c+1));
                                            setEdgeColour(edges.get(rt),uwc.get(c+1));
                                        }                                       
                                    }
                                }
                                c++;
                            }
                        }
                    }
                    else
                    {
                        int z=0;
                        for(int kt=0;kt<k2.size();kt++)
                        {
                            if(((k2.get(kt)).x==lx) || ((k2.get(kt)).y==lx))
                            {
                                for(int kk=0;kk<k2.size();kk++)
                                {
                                    if((((k2.get(kt)).x==uy) && ((k2.get(kt)).y==lx)) || (((k2.get(kt)).x==lx) && ((k2.get(kt)).y==uy))) 
                                    {
                                        int lt=getEdgeIndex((k2.get(kt)).x,(k2.get(kt)).y);
                                        int rt=getEdgeIndex((k2.get(kk)).x,(k2.get(kk)).y);
                                        if(z==0)
                                        {
                                            (edges.get(lt)).setColour(getEdgeColour(pbx,pby));
                                            setEdgeColour(edges.get(lt),getEdgeColour(pbx,pby));
                                            (edges.get(rt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(rt),col.get(0));
                                            z++;
                                        }
                                        else
                                        {
                                            (edges.get(lt)).setColour(col.get(0));
                                            setEdgeColour(edges.get(lt),col.get(0));
                                            (edges.get(rt)).setColour(col.get(1));
                                            setEdgeColour(edges.get(rt),col.get(1));
                                        }
                                    }
                                }
                            }
                        }
                        Integer cly[]=availableColoursOnVertex(uy,g);
                        Integer clx[]=availableColoursOnVertex(lx,g);
                        ArrayList<Integer> lux=new ArrayList<Integer>();
                        ArrayList<Integer> lwa=new ArrayList<Integer>();
                        for(int kt=0;kt<((k1.size())/2);kt++)
                        {
                            lux.add(cly[kt]);
                            lwa.add(clx[kt]);
                        }
                        ArrayList<Integer> ck2=new ArrayList<Integer>();
                        ArrayList<Integer> luxf=new ArrayList<Integer>();
                        ArrayList<Integer> lwaf=new ArrayList<Integer>();
                        for(int kt=0;kt<lux.size();kt++)
                        {
                            if((lwa.indexOf(lux.get(kt)))>=0)
                            {
                                ck2.add(lux.get(kt));
                            }
                            else
                            {
                                luxf.add(lux.get(kt));
                            }
                            if((lux.indexOf(lwa.get(kt)))<0)
                            {
                                lwaf.add(lwa.get(kt));
                            }
                        }
                        ArrayList<Integer> ne=new ArrayList<Integer>();
                        for(int i=0;i<ed.size();i++)
                        {
                            if(((ed.get(i)).getLevel())==r)
                            {
                                if(!((ed.get(i)).isColourSet()))
                                {
                                    if((ed.get(i)).x==ly)
                                    {
                                        if((ne.indexOf((ed.get(i)).y))<0)
                                        {
                                            ne.add((ed.get(i)).y);
                                        }
                                    }
                                    else if((ed.get(i)).y==ly)
                                    {
                                        if((ne.indexOf((ed.get(i)).x))<0)
                                        {
                                            ne.add((ed.get(i)).x);
                                        }
                                    }
                                }
                            }
                        }
                        int co=0;
                        for(int i=0;i<ne.size();i++)
                        {
                            if(i<ck2.size())
                            {
                                if(i==((ne.size())-1))
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    (edges.get(lt)).setColour(ck2.get(0));
                                    setEdgeColour(edges.get(lt),ck2.get(0));
                                }
                                else
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(ck2.get(i));
                                    setEdgeColour(edges.get(rt),ck2.get(i));
                                    if(i==(ck2.size()-1))
                                    {
                                        (edges.get(lt)).setColour(lwaf.get(0));
                                        setEdgeColour(edges.get(lt),lwaf.get(0));
                                    }
                                    else
                                    {
                                        (edges.get(lt)).setColour(ck2.get(i+1));
                                        setEdgeColour(edges.get(lt),ck2.get(i+1));
                                    }
                                }
                            }
                            else
                            {
                                if(i==((ne.size())-1))
                                {
                                    if(ck2.size()>0)
                                    {
                                        int rt=getEdgeIndex(uy,ne.get(i));
                                        int lt=getEdgeIndex(lx,ne.get(i));
                                        (edges.get(rt)).setColour(luxf.get(co));
                                        setEdgeColour(edges.get(rt),luxf.get(co));
                                        (edges.get(lt)).setColour(ck2.get(0));
                                        setEdgeColour(edges.get(lt),ck2.get(0));
                                    }
                                    else
                                    {
                                        int rt=getEdgeIndex(uy,ne.get(i));
                                        int lt=getEdgeIndex(lx,ne.get(i));
                                        (edges.get(rt)).setColour(luxf.get(co));
                                        setEdgeColour(edges.get(rt),luxf.get(co));
                                        (edges.get(lt)).setColour(luxf.get(0));
                                        setEdgeColour(edges.get(lt),luxf.get(0));
                                    }
                                }
                                else
                                {
                                    int rt=getEdgeIndex(uy,ne.get(i));
                                    int lt=getEdgeIndex(lx,ne.get(i));
                                    (edges.get(rt)).setColour(luxf.get(co));
                                    setEdgeColour(edges.get(rt),luxf.get(co));
                                    (edges.get(lt)).setColour(lwaf.get(co+1));
                                    setEdgeColour(edges.get(lt),lwaf.get(co+1));
                                    co++;
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                if(k1.size()<=k2.size())
                {
                    ArrayList<Integer> ck1=new ArrayList<Integer>();
                    for(int i=0;i<((k1.size())/2);i++)
                    {
                        ck1.add(col.get(i));
                    }
                    ArrayList<Integer> ne=new ArrayList<Integer>();
                    for(int i=0;i<ed.size();i++)
                    {
                        if(((ed.get(i)).getLevel())==r)
                        {
                            if(!((ed.get(i)).isColourSet()))
                            {
                                if((ed.get(i)).x==ly)
                                {
                                    if((ne.indexOf((ed.get(i)).y))<0)
                                    {
                                        ne.add((ed.get(i)).y);
                                    }
                                }
                                else if((ed.get(i)).y==ly)
                                {
                                    if((ne.indexOf((ed.get(i)).x))<0)
                                    {
                                        ne.add((ed.get(i)).x);
                                    }
                                }
                            }
                        }
                    }
                    for(int i=0;i<ne.size();i++)
                    {
                        int lt=getEdgeIndex(ne.get(i),lx);
                        int rt=getEdgeIndex(ne.get(i),ly);
                        if(i==((ne.size())-1))
                        {
                            (edges.get(lt)).setColour(ck1.get(i));
                            setEdgeColour(edges.get(lt),ck1.get(i));
                            (edges.get(rt)).setColour(ck1.get(0));
                            setEdgeColour(edges.get(rt),ck1.get(0));
                        }
                        else
                        {
                            (edges.get(lt)).setColour(ck1.get(i));
                            setEdgeColour(edges.get(lt),ck1.get(i));
                            (edges.get(rt)).setColour(ck1.get(i+1));
                            setEdgeColour(edges.get(rt),ck1.get(i+1));
                        }
                    }
                    ArrayList<Integer> ck2=new ArrayList<Integer>();
                    ArrayList<Integer> c21=new ArrayList<Integer>();
                    ArrayList<Integer> c12=new ArrayList<Integer>();
                    for(int i=0;i<col2.size();i++)
                    {
                        if((col1.indexOf(col2.get(i)))>0)
                        {
                            ck2.add(col2.get(i));
                        }
                        else
                        {
                            c21.add(col2.get(i));
                        }
                    }
                    for(int i=0;i<col1.size();i++)
                    {
                        if((col2.indexOf(col1.get(i)))<0)
                        {
                            c12.add(col1.get(i));
                        }
                    }
                    ArrayList<Integer> ne2=new ArrayList<Integer>();
                    for(int i=0;i<ed.size();i++)
                    {
                        if(((ed.get(i)).getLevel())==r)
                        {
                            if(!((ed.get(i)).isColourSet()))
                            {
                                if((ed.get(i)).x==uy)
                                {
                                    if((ne2.indexOf((ed.get(i)).y))<0)
                                    {
                                        ne2.add((ed.get(i)).y);
                                    }
                                }
                                else if((ed.get(i)).y==uy)
                                {
                                    if((ne2.indexOf((ed.get(i)).x))<0)
                                    {
                                        ne2.add((ed.get(i)).x);
                                    }
                                }
                            }
                        }
                    }
                    int c=0;
                    for(int i=0;i<ne2.size();i++)
                    {
                        int lt=getEdgeIndex(uy,ne2.get(i));
                        int rt=getEdgeIndex(ux,ne2.get(i));
                        if(i<((ne.size())-1))
                        {
                            if(i<((ck2.size())-1))
                            {
                                (edges.get(lt)).setColour(ck2.get(i));
                                setEdgeColour(edges.get(lt),ck2.get(i));
                                (edges.get(rt)).setColour(ck2.get(i+1));
                                setEdgeColour(edges.get(rt),ck2.get(i+1));
                            }
                            else if(i==((ck2.size())-1))
                            {
                                (edges.get(lt)).setColour(ck2.get(i));
                                setEdgeColour(edges.get(lt),ck2.get(i));
                                (edges.get(rt)).setColour(c21.get(0));
                                setEdgeColour(edges.get(rt),c21.get(0));
                            }
                            else
                            {
                                (edges.get(lt)).setColour(c12.get(c));
                                setEdgeColour(edges.get(lt),c12.get(c));
                                (edges.get(rt)).setColour(c21.get(c));
                                setEdgeColour(edges.get(rt),c21.get(c));
                                c++;
                            }
                        }
                        else
                        {
                            if(i<ck2.size())
                            {
                                (edges.get(lt)).setColour(ck2.get(i));
                                setEdgeColour(edges.get(lt),ck2.get(i));
                                (edges.get(rt)).setColour(ck2.get(0));
                                setEdgeColour(edges.get(rt),ck2.get(0));
                            }
                            else
                            {
                                (edges.get(lt)).setColour(c12.get(c));
                                setEdgeColour(edges.get(lt),c12.get(c));
                                (edges.get(rt)).setColour(ck2.get(0));
                                setEdgeColour(edges.get(rt),ck2.get(0));
                            }
                        }
                    }
                }
                else
                {
                    ArrayList<Integer> ck1=new ArrayList<Integer>();
                    for(int i=0;i<((k2.size())/2);i++)
                    {
                        ck1.add(col1.get(i));
                    }
                    ArrayList<Integer> ne=new ArrayList<Integer>();
                    for(int i=0;i<ed.size();i++)
                    {
                        if(((ed.get(i)).getLevel())==r)
                        {
                            if(!((ed.get(i)).isColourSet()))
                            {
                                if((ed.get(i)).x==uy)
                                {
                                    if((ne.indexOf((ed.get(i)).y))<0)
                                    {
                                        ne.add((ed.get(i)).y);
                                    }
                                }
                                else if((ed.get(i)).y==uy)
                                {
                                    if((ne.indexOf((ed.get(i)).x))<0)
                                    {
                                        ne.add((ed.get(i)).x);
                                    }
                                }
                            }
                        }
                    }
                    for(int i=0;i<ne.size();i++)
                    {
                        int lt=getEdgeIndex(ne.get(i),lx);
                        int rt=getEdgeIndex(ne.get(i),uy);
                        if(i==((ne.size())-1))
                        {
                            (edges.get(lt)).setColour(ck1.get(i));
                            setEdgeColour(edges.get(lt),ck1.get(i));
                            (edges.get(rt)).setColour(ck1.get(0));
                            setEdgeColour(edges.get(rt),ck1.get(0));
                        }
                        else
                        {
                            (edges.get(lt)).setColour(ck1.get(i));
                            setEdgeColour(edges.get(lt),ck1.get(i));
                            (edges.get(rt)).setColour(ck1.get(i+1));
                            setEdgeColour(edges.get(rt),ck1.get(i+1));
                        }
                    }
                    ArrayList<Integer> ck2=new ArrayList<Integer>();
                    ArrayList<Integer> c20=new ArrayList<Integer>();
                    ArrayList<Integer> c02=new ArrayList<Integer>();
                    for(int i=0;i<col2.size();i++)
                    {
                        if((col.indexOf(col2.get(i)))>0)
                        {
                            ck2.add(col2.get(i));
                        }
                        else
                        {
                            c20.add(col2.get(i));
                        }
                    }
                    for(int i=0;i<col.size();i++)
                    {
                        if((col2.indexOf(col.get(i)))<0)
                        {
                            c02.add(col.get(i));
                        }
                    }
                    ArrayList<Integer> ne2=new ArrayList<Integer>();
                    for(int i=0;i<ed.size();i++)
                    {
                        if(((ed.get(i)).getLevel())==r)
                        {
                            if(!((ed.get(i)).isColourSet()))
                            {
                                if((ed.get(i)).x==ly)
                                {
                                    if((ne2.indexOf((ed.get(i)).y))<0)
                                    {
                                        ne2.add((ed.get(i)).y);
                                    }
                                }
                                else if((ed.get(i)).y==ly)
                                {
                                    if((ne2.indexOf((ed.get(i)).x))<0)
                                    {
                                        ne2.add((ed.get(i)).x);
                                    }
                                }
                            }
                        }
                    }
                    int c=0;
                    for(int i=0;i<ne2.size();i++)
                    {
                        int lt=getEdgeIndex(ly,ne2.get(i));
                        int rt=getEdgeIndex(lx,ne2.get(i));
                        if(i<((ne.size())-1))
                        {
                            if(i<((ck2.size())-1))
                            {
                                (edges.get(lt)).setColour(ck2.get(i));
                                setEdgeColour(edges.get(lt),ck2.get(i));
                                (edges.get(rt)).setColour(ck2.get(i+1));
                                setEdgeColour(edges.get(rt),ck2.get(i+1));
                            }
                            else if(i==((ck2.size())-1))
                            {
                                (edges.get(lt)).setColour(ck2.get(i));
                                setEdgeColour(edges.get(lt),ck2.get(i));
                                (edges.get(rt)).setColour(c20.get(0));
                                setEdgeColour(edges.get(rt),c20.get(0));
                            }
                            else
                            {
                                (edges.get(lt)).setColour(c02.get(c));
                                setEdgeColour(edges.get(lt),c02.get(c));
                                (edges.get(rt)).setColour(c20.get(c));
                                setEdgeColour(edges.get(rt),c20.get(c));
                                c++;
                            }
                        }
                        else
                        {
                            if(i<ck2.size())
                            {
                                (edges.get(lt)).setColour(ck2.get(i));
                                setEdgeColour(edges.get(lt),ck2.get(i));
                                (edges.get(rt)).setColour(ck2.get(0));
                                setEdgeColour(edges.get(rt),ck2.get(0));
                            }
                            else
                            {
                                (edges.get(lt)).setColour(c02.get(c));
                                setEdgeColour(edges.get(lt),c02.get(c));
                                (edges.get(rt)).setColour(ck2.get(0));
                                setEdgeColour(edges.get(rt),ck2.get(0));
                            }
                        }
                    }
                }
            }
            if(!(isGraphColoured()))
            {
                if(!(checkColouringLevel(r)))
                {
                    assignColours(r);
                }
                else
                {
                    r++;
                    assignColours(r);
                }
            }
        }
    }
    private int numberOfColoursUsed()
    {
        int c=0;
        int s=0;
        for(int i=0;i<vertexCount;i++)
        {
            for(int j=0;j<vertexCount;j++)
            {
                if(colourMatrix[i][j]==1)
                {
                    s=j+1;
                }
            }
            if(s>=c)
            {
                c=s;
            }
        }
        return c;
    }
    private int getGraphDegree()
    {
        int s=0;
        for(int i=0;i<adjacentMatrix[0].length;i++)
        {
            if(adjacentMatrix[0][i]==true)
            {
                s=s+1;
            }
        }
        int u=0;
        for(int i=1;i<adjacentMatrix[0].length;i++)
        {
            u=0;
            for(int j=0;j<adjacentMatrix[0].length;j++)
            {
                if(adjacentMatrix[i][j]==true)
                {
                    u=u+1;
                }
            }
            if(u>s)
            {
                s=u;
            }
        }
        return s;
    }
    public void print()
    {
        assignLevels(0);
        assignColours(0);
        for(int i=0;i<edges.size();i++)
        {
            System.out.println("for edge between vertices "+(((edges.get(i)).x)+1)+","+(((edges.get(i)).y)+1)+" level is "+(edges.get(i)).getLevel());
        }
        for(int i=0;i<edges.size();i++)
        {
            System.out.println("for edge between vertices "+(((edges.get(i)).x)+1)+","+(((edges.get(i)).y)+1)+" colour is "+(((edges.get(i)).getColour())+1));
        }
        System.out.println("Number of colours used:"+numberOfColoursUsed());
        System.out.println("Maximum degree of the given Graph:"+getGraphDegree());
    }
}