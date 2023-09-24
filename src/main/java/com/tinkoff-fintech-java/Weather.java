import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Weather {

    private UUID id;

    private String regionName;

    private int temperature;

    private Date date = new Date();

    // no date in constructor to simplify process of creating new Weather objects
    public Weather(String regionName, int temperature) {
        this.regionName = regionName;
        this.id = UUID.nameUUIDFromBytes(regionName.getBytes());
        this.temperature = temperature;
    }
}
