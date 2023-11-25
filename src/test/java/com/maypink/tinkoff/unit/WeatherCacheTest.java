package com.maypink.tinkoff.unit;

import com.maypink.tinkoff.cache.BaseCache;
import com.maypink.tinkoff.cache.WeatherCache;
import com.maypink.tinkoff.controllers.resources.WeatherResource;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WeatherCacheTest {

    private static final WeatherResource WEATHER_RESOURCE = new WeatherResource(
            "weather",
            "region",
            "country",
            0,
            0D);

    private static final WeatherResource WEATHER_RESOURCE_SECOND = new WeatherResource(
            "weather2",
            "region",
            "country",
            0,
            0D);

    @Test
    public void addSomeDataToCache_WhenGetData_ThenIsEqualWithCacheElement() {
        WeatherCache weatherCache = new WeatherCache(2);
        weatherCache.put("1", WEATHER_RESOURCE);
        weatherCache.put("2", WEATHER_RESOURCE_SECOND);
        assertEquals(WEATHER_RESOURCE, weatherCache.get("1").get());
        assertEquals(WEATHER_RESOURCE_SECOND, weatherCache.get("2").get());
    }

    @Test
    public void addDataToCacheToTheNumberOfSize_WhenAddOneMoreData_ThenLeastRecentlyDataWillEvict() {
        WeatherCache weatherCache = new WeatherCache(1);
        weatherCache.put("1", WEATHER_RESOURCE);
        weatherCache.put("2", WEATHER_RESOURCE_SECOND);
        assertFalse(weatherCache.get("1").isPresent());
    }

    @Test
    public void runMultiThreadTask_WhenPutDataInConcurrentToCache_ThenNoDataLost() throws Exception {
        final int size = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        BaseCache cache = new WeatherCache(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        try {
            IntStream.range(0, size).<Runnable>mapToObj(key -> () -> {
                cache.put(key, "value" + key);
                countDownLatch.countDown();
            }).forEach(executorService::submit);
            countDownLatch.await();
        } finally {
            executorService.shutdown();
        }
        assertEquals(cache.size(), size);
        IntStream.range(0, size).forEach(i -> assertEquals("value" + i, cache.get(i).get()));
    }
}
