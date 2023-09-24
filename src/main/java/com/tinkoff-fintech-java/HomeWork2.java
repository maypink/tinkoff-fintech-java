import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeWork2 {
    public static void main(String[] args) {

        // create regions
        Weather moscowWeather = new Weather("Moscow", 10);
        Weather surgutWeather = new Weather("Surgut", -10);
        Weather surgutWeather2 = new Weather("Surgut", -20);
        Weather saintPeterburgWeather = new Weather("SP", 15);
        Weather berlinWeather = new Weather("Berlin", 20);
        Weather darmstadtWeather = new Weather("Darmstadt", 17);
        Weather munichWeather = new Weather("Munich", 15);

        List<Weather> regions = new ArrayList<Weather>();
        regions.add(moscowWeather);
        regions.add(surgutWeather);
        regions.add(surgutWeather2);
        regions.add(saintPeterburgWeather);
        regions.add(berlinWeather);
        regions.add(darmstadtWeather);
        regions.add(munichWeather);

        // subtask 1
        double averageTemperature = getAverageTemperature(regions);
        System.out.printf("The average temperature is %f%n", averageTemperature);

        // subtask 2
        List<String> filteredRegions = getRegionsWithTemperatureHigher(regions, 10);
        System.out.println(Arrays.toString(filteredRegions.toArray()));

        // subtask 3
        Map<UUID, List<Integer>> mapWithUUIDAndTemperature = convertToMapUUIDAndTemperatureList(regions);
        mapWithUUIDAndTemperature.forEach((key, value) -> System.out.println(key + ": " + value));

        // subtask 4
        Map<Integer, List<Weather>> mapWithTemperatureAndWeatherList = convertToMapTemperatureAndWeatherCollection(regions);
        mapWithTemperatureAndWeatherList.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    /**
     * Calculates an average temperature for specified regions.
     *
     * @param regions A list of regions to consider.
     * @return An average temperature.
     */
    public static double getAverageTemperature(List<Weather> regions){
        Stream<Weather> regionsStream = regions.stream();
        return regionsStream
                .mapToDouble(Weather::getTemperature)
                .average()
                .orElse(Double.NaN);
    }

    /**
     * Returns regions which temperature is higher than the specified one.
     *
     * @param regions A list of regions to consider.
     * @param temperature Temperature to filter regions by.
     * @return Regions filtered by temperature.
     */
    public static List<String> getRegionsWithTemperatureHigher(List<Weather> regions, int temperature){
        return regions.stream()
                .filter(r -> r.getTemperature() > temperature)
                .map(Weather::getRegionName)
                .toList();
    }

    /**
     * Returns map with UUID as keys and List of temperature value as value.
     *
     * @param regions A list of regions to consider.
     * @return Described map.
     */
    public static Map<UUID, List<Integer>> convertToMapUUIDAndTemperatureList(List<Weather> regions){
        return regions.stream()
                .collect(Collectors.groupingBy(
                        Weather::getId,
                        Collectors.mapping(
                                Weather::getTemperature,
                                Collectors.toList())
                ));

    }

    /**
     * Returns map with temperature as keys and List of Weather objects with this temperature as values.
     *
     * @param regions A list of regions to consider.
     * @return Described map.
     */
    public static Map<Integer, List<Weather>> convertToMapTemperatureAndWeatherCollection(List<Weather> regions){
        return regions.stream().collect(Collectors.groupingBy(Weather::getTemperature));
    }

}
