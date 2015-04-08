public class Edge
{
    public int x;
    public int y;
    private boolean levelSet;
    private int level;
    public int baseX;
    public int baseY;
    private int colour;
    private boolean colourSet;
    public Edge(int m,int n)
    {
        this.x=m;
        this.y=n;
        level=-1;
        levelSet=false;
        baseX=-1;
        baseY=-1;
        colourSet=false;
        colour=-1;
    }
    public void setLevel(int n, int x, int y)
    {
        this.levelSet=true;
        level=n;
        baseX=x;
        baseY=y;
    }
    public void setColour(int n)
    {
        colourSet=true;
        colour=n;
    }
    public boolean isColourSet()
    {
        return this.colourSet;
    }
    public int getColour()
    {
        return colour;
    }
    public int getLevel()
    {
        return level;
    }
    public boolean isLevelSet()
    {
        return levelSet;
    }
}