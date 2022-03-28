
public class SkierVertical {

    private int resort;
    private int season;
    private int skierID;

    private int dayID = -1;

    public void setResort(int resort) {
        this.resort = resort;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public SkierVertical(String resort, String season) {
        try{
            this.resort = Integer.parseInt(resort);
            this.season = Integer.parseInt(season);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public SkierVertical(String resort) {
        try{
            this.resort = Integer.parseInt(resort);
            this.season = -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setSkierID(int skierID) {
        this.skierID = skierID;
    }

    public void setDayID(int dayID) {
        this.dayID = dayID;
    }

    public int getSeason(){
        return this.season;
    }
    public String SQLRequest() {
        String request = "SELECT COUNT(*) AS verticalCount FROM LiftRides" +
                " WHERE skierID = " + skierID +
                " AND resortID = " + resort + "";

        if (season > 0) {
            request = request + " AND seasonID = " + season + ";";
            return request;
        } else {
            if (dayID > 0) {
                request = request + " AND dayID = " + dayID + ";";
                return request;
            } else {
                request = request + ";";
                return request;

            }
        }

    }
}
