
public class LiftRide {

    private int time = -1;
    private int liftID = -1;
    private int waitTime = -1;
    private int skierID;
    private int seasonID;
    private int resortID;
    private int dayID;

    public int getTime() {
        return time;
    }

    public int getLiftID() {
        return liftID;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public int getResortID() {
        return resortID;
    }

    public int getDayID() {
        return dayID;
    }

    public LiftRide(){}

    public int getSkierID() {
        return this.skierID;
    }
    public String toString() {
        return "Lift{" +
                "time=" + time +
                ", liftID=" + liftID +
                ", waitTime=" + waitTime +
                '}';
    }



}
