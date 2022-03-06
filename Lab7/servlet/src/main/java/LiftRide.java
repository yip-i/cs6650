public class LiftRide {


    private int time = -1;
    private int liftID = -1;
    private int waitTime = -1;
    private int skierID;
    private int seasonID;
    private int resortID;
    private int dayID;

    public void setDayID(int dayID) {
        this.dayID = dayID;
    }

    public LiftRide(int time, int liftID, int waitTime) {
        this.time = time;
        this.liftID = liftID;
        this.waitTime = waitTime;
    }

    public LiftRide(int time, int liftID, int skierID, int seasonID, int resortID, int dayID) {
        this.time = time;
        this.liftID = liftID;
        this.skierID = skierID;
        this.seasonID = seasonID;
        this.resortID = resortID;
        this.dayID = dayID;
    }

    /**
     * Empty class in case variables are missing from JSON.
     */
    public LiftRide(){}

    @Override
    public String toString() {
        return "Lift{" +
                "time=" + time +
                ", liftID=" + liftID +
                ", waitTime=" + waitTime +
                '}';
    }

    public String sendJson() {
        return "{" +
                "time=" + time +
                ", liftID=" + liftID +
                ", waitTime=" + waitTime +
                ", skierID=" + skierID +
                ", seasonID=" + seasonID +
                ", resortID=" + resortID +
                ", dayID=" + dayID +
                '}';
    }

    /**
     * Set Skier's ID
     * @param skierID
     */
    public void setSkierID(int skierID){
        this.skierID = skierID;
    }

    public void setResortID(int resortID) {
        this.resortID = resortID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }


    public int getTime() {
        return time;
    }

    public int getLiftID() {
        return liftID;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getSkierID() {
        return skierID;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public int getResortID() {
        return resortID;
    }

    public int getDayID() {
        return this.dayID;
    }







    public boolean isValidLift(){
        if (this.liftID < 0 || this.time < 0 || this.waitTime < 0) {
            return false;
        } else {
            return true;
        }
    }
}
