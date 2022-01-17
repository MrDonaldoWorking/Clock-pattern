import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class CustomClock extends Clock {
    private Clock clock;

    public CustomClock() {
        this.clock = fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Clock withZone(final ZoneId zone) {
        return clock.withZone(getZone());
    }

    @Override
    public Instant instant() {
        return clock.instant();
    }

    public void setPlusTo(final Duration duration) {
//        clock = offset(clock, Duration.ofMillis(current.minusMillis(instant().toEpochMilli()).toEpochMilli()));
        clock = offset(clock, duration);
    }
}
