import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Class for storing POST lift ride details
 */
public class SkierLift {

    private int skierID;
    private int dayID;
    private int seasonID;
    private int resortID;
    private LocalDateTime time;

    public SkierLift(int skierID, int dayID, int seasonID, int resortID){
        this.skierID = skierID;
        this.dayID = dayID;
        this.seasonID = seasonID;
        this.resortID = resortID;
        this.time = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "SkierLift{" +
                "skierID=" + skierID +
                ", dayID=" + dayID +
                ", seasonID=" + seasonID +
                ", resortID=" + resortID +
                ", time=" + time +
                '}';
    }
}
