
public class SkierLift {

    private int time = 0;
    private int liftID = -1;
    private int waitTime = -1;
    private int skierID;

    public SkierLift(){}

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
