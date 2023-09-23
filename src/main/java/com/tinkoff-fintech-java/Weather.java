import java.util.Date;
import java.util.UUID;

public class Weather {

    private UUID id;

    private String regionName;

    private Integer temperature;

    private Date date = new Date();

    // no date in constructor to simplify process of creating new Weather objects
    public Weather(String regionName, int temperature) {
        this.regionName = regionName;
        this.id = UUID.nameUUIDFromBytes(regionName.getBytes());
        this.temperature = temperature;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
