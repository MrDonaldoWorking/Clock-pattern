import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EventsStaticImpl implements EventsStatic {
    private static final int SEC_IN_MIN = 60;
    private static final int MIN_IN_HRS = 60;
    private static final int SEC_IN_HRS = SEC_IN_MIN * MIN_IN_HRS;

    private final Map<String, List<Instant>> events = new HashMap<>();
    private final Clock clock;

    public EventsStaticImpl(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(final String name) {
        events.putIfAbsent(name, new ArrayList<>());
        events.get(name).add(clock.instant());
    }

    private List<Instant> filter(final Instant current, final List<Instant> events) {
        final Instant hourAgo = current.minus(1L, ChronoUnit.HOURS);
        final List<Instant> filtered = new ArrayList<>();
        for (final Instant event : events) {
            if (hourAgo.isBefore(event) && event.isBefore(current)) {
                filtered.add(event);
            }
        }
        return filtered;
    }

    private double calcRPM(final Instant current, final List<Instant> events) {
         return (double) filter(current, events).size() / events.size();
    }

    @Override
    public double getEventStaticByName(final String name) {
        final List<Instant> eventsTime = events.get(name);
        if (eventsTime == null) {
            return 0;
        }
        return calcRPM(clock.instant(), eventsTime);
    }

    @Override
    public Map<String, List<Instant>> getAllEventStatic() {
        final Instant current = clock.instant();
        final Map<String, List<Instant>> all = new HashMap<>();
        for (final Map.Entry<String, List<Instant>> entry : events.entrySet()) {
            all.put(entry.getKey(), filter(current, entry.getValue()));
        }
        return all;
    }

    @Override
    public void printStatic() {
        for (final Map.Entry<String, List<Instant>> entry : getAllEventStatic().entrySet()) {
            System.out.printf("Event name: '%s', rpm: %f", entry.getKey(), calcRPM(clock.instant(), entry.getValue()));
        }
    }
}
