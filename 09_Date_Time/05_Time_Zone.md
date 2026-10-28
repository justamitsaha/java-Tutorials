# TimeZone (`java.util.TimeZone`)

`java.util.TimeZone` represents a time zone offset and calculates Daylight Saving Time (DST) rules in the legacy Date-Time API.

---

## 1. Inspecting Available Time Zones

```java
import java.util.TimeZone;

public class TimeZoneListing {
    public static void main(String[] args) {
        // Default JVM time zone
        TimeZone defaultZone = TimeZone.getDefault();
        System.out.println("Default: " + defaultZone.getID());

        // List all available zone IDs (e.g., "America/New_York", "Asia/Kolkata", "UTC")
        String[] ids = TimeZone.getAvailableIDs();
        System.out.println("Total zones supported: " + ids.length);
    }
}
```

---

## 2. Formatting Date with Time Zones

`java.util.Date` itself holds **no timezone**—it only represents milliseconds since epoch. To display a `Date` in a specific timezone, set the zone on `SimpleDateFormat`:

```java
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneFormat {
    public static void main(String[] args) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

        // Format in New York time
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        System.out.println("New York: " + sdf.format(now));

        // Format in UTC
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("UTC:      " + sdf.format(now));

        // Format in Tokyo time
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        System.out.println("Tokyo:    " + sdf.format(now));
    }
}
```

---

## 3. TimeZone with `Calendar` and a Common Pitfall

When initializing a `GregorianCalendar` with a `TimeZone`, field getters reflect that timezone:

```java
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarTimeZone {
    public static void main(String[] args) {
        TimeZone nyZone = TimeZone.getTimeZone("America/New_York");
        Calendar cal = new GregorianCalendar(nyZone);

        // Fields reflect New York local time
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        System.out.printf("New York Time: %02d:%02d%n", hour, minute);

        // PITFALL: calling cal.getTime() returns a java.util.Date!
        // Printing it directly invokes Date.toString(), which prints in the local JVM timezone,
        // appearing as though the timezone was ignored.
        System.out.println("Raw Date toString: " + cal.getTime()); 
    }
}
```

---

## 4. Modern Replacement: `ZoneId`

In Java 8+, convert legacy `TimeZone` to modern `ZoneId`:

```java
import java.time.ZoneId;
import java.util.TimeZone;

ZoneId zoneId = TimeZone.getTimeZone("America/New_York").toZoneId();
```
