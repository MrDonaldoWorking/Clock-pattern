import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface EventsStatic {
    void incEvent(final String name);
    double getEventStaticByName(final String name);
    Map<String, List<Instant>> getAllEventStatic();
    void printStatic();
}
