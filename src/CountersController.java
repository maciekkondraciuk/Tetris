

public class CountersController{

    private static int pointFactor = 6;

    private static int lvl = 1;
    private static int points = 0;
    private static int lines = 0;
    
    private static RightMenuPanel rightMenuPanel;

    public  static void connect(RightMenuPanel rightMenuPanel){

        CountersController.rightMenuPanel = rightMenuPanel;
    }
    public void addLine(){
        lines++;
        calculateLvl();
        calculatePointsForRow();
        rightMenuPanel.setLvlCounter(lvl);
        rightMenuPanel.setLinesCounter(lines);
    }
    public static void restartCounters(){
        lvl = 1;
        points = 0;
        lines = 0;
        rightMenuPanel.setLvlCounter(lvl);
        rightMenuPanel.setPointsCounter(points);
        rightMenuPanel.setLinesCounter(lines);
    }
    public static void calculatePoints(){
        points += (int)(pointFactor * lvl);
        rightMenuPanel.setPointsCounter(points);
    }
    public static void calculatePointsForRow(){
        points += (int)(pointFactor * 5 * lvl);
        rightMenuPanel.setPointsCounter(points);
    }
    public int getLines(){return lines;}
    private void calculateLvl()
    {
        if (((lines - (lines % 10)) / 10) > lvl - 1){
            lvl++;
        }
    }
    public static int getLvl(){return lvl;}
}
