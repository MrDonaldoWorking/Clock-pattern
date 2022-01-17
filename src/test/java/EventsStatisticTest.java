import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventsStatisticTest {
    private static final double eps = 1e-5;

    private CustomClock clock;
    private EventsStatic eventsStatic;

    @Before
    public void prepare() {
        clock = new CustomClock();
        eventsStatic = new EventsStaticImpl(clock);
    }

    @Test
    public void empty() {
        assertTrue(eventsStatic.getAllEventStatic().isEmpty());
        assertEquals(0, eventsStatic.getEventStaticByName("Some"), eps);
        eventsStatic.printStatic();
    }

    @Test
    public void one() {
        final String event = "event";
        eventsStatic.incEvent(event);
        clock.setPlusTo(Duration.ofMillis(1));
        assertEquals(1. / 60, eventsStatic.getEventStaticByName(event), eps);

        clock.setPlusTo(Duration.ofMinutes(59));
        assertEquals(1. / 60, eventsStatic.getEventStaticByName(event), eps);

        clock.setPlusTo(Duration.ofMinutes(1));
        assertEquals(0, eventsStatic.getEventStaticByName(event), eps);

        assertEquals(0, eventsStatic.getAllEventStatic().get(event).size());
    }

    @Test
    public void multiple() {
        final int N = 10;
        for (int i = 1; i <= N; ++i) {
            final String str = Integer.toString(i);
            for (int j = 0; j < i; ++j) {
                eventsStatic.incEvent(str);
            }
        }

        clock.setPlusTo(Duration.ofMillis(1));
        final Map<String, List<Instant>> all = eventsStatic.getAllEventStatic();
        for (int i = 1; i <= N; ++i) {
            final String str = Integer.toString(i);
            assertEquals(i / 60.0, EventsStaticImpl.calcRPM(clock.instant(), all.get(str)), eps);
        }
    }
}
