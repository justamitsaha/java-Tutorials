# TimeZone (`java.util.TimeZone`)

`java.util.TimeZone` represents a timezone offset and calculates Daylight Saving Time (DST) transitions in the legacy Date-Time API.

---

## 1. Inspecting Available Time Zones

`TimeZone.getDefault()` retrieves the default timezone from host system settings or the JVM system property `-Duser.timezone`.

```java
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        // Default JVM time zone
        TimeZone defaultZone = TimeZone.getDefault();
        System.out.println("Default Zone ID:   " + defaultZone.getID());
        System.out.println("Display Name:      " + defaultZone.getDisplayName());

        // Count of supported zone IDs (e.g., "America/New_York", "Europe/London", "UTC")
        String[] ids = TimeZone.getAvailableIDs();
        System.out.println("Total zones:       " + ids.length);

        // Fetching specific timezone by ID
        TimeZone nyZone = TimeZone.getTimeZone("America/New_York");
        System.out.println("Raw Offset (NY):   " + (nyZone.getRawOffset() / 3_600_000) + " hours");
        System.out.println("Observes DST (NY): " + nyZone.useDaylightTime());
    }
}
```

> [!WARNING]
> Mutating the global default timezone via `TimeZone.setDefault(...)` alters the behavior of all concurrent threads, `Date.toString()`, and un-zoned `Calendar` instances throughout the entire JVM. In enterprise applications, treat `TimeZone.setDefault()` as an anti-pattern.

---

## 2. The `Date.toString()` Formatting Pitfall

`java.util.Date` stores an absolute millisecond instant from epoch and contains **no timezone information**. Setting a timezone on a `Calendar` alters field calculations, but calling `cal.getTime()` returns a raw `Date`. Calling `toString()` on that `Date` formats it using the **JVM default timezone**, making it appear as if the timezone configuration was ignored:

```java
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        TimeZone tokyoZone = TimeZone.getTimeZone("Asia/Tokyo");
        Calendar cal = new GregorianCalendar(tokyoZone);

        // 1. Getters accurately reflect Tokyo local time
        int hourTokyo = cal.get(Calendar.HOUR_OF_DAY);
        System.out.println("Tokyo Hour via Calendar: " + hourTokyo);

        // 2. PITFALL: cal.getTime() returns a Date; Date.toString() uses local JVM timezone!
        System.out.println("cal.getTime() toString:   " + cal.getTime());

        // 3. CORRECT FIX: Explicitly apply TimeZone to a formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(tokyoZone);
        System.out.println("Formatted with Formatter: " + sdf.format(cal.getTime()));
    }
}
```

---

## 3. Daylight Saving Time (DST) Inspection

`TimeZone` tracks whether a specific date falls within Daylight Saving Time:

```java
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        TimeZone nyZone = TimeZone.getTimeZone("America/New_York");

        // January (Standard Time - EST: UTC-5)
        Date winterDate = new GregorianCalendar(2024, 0, 15).getTime();
        boolean winterDst = nyZone.inDaylightTime(winterDate);
        int winterOffset = nyZone.getOffset(winterDate.getTime()) / 3_600_000;

        // July (Daylight Saving Time - EDT: UTC-4)
        Date summerDate = new GregorianCalendar(2024, 6, 15).getTime();
        boolean summerDst = nyZone.inDaylightTime(summerDate);
        int summerOffset = nyZone.getOffset(summerDate.getTime()) / 3_600_000;

        System.out.printf("Winter (Jan): DST=%b, Offset=%d hrs%n", winterDst, winterOffset);
        System.out.printf("Summer (Jul): DST=%b, Offset=%d hrs%n", summerDst, summerOffset);
    }
}
```

---

## 4. Bridging `TimeZone` to Modern `ZoneId`

In Java 8+, convert legacy `TimeZone` instances directly to modern `java.time.ZoneId`:

```java
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        // Legacy -> Modern
        TimeZone legacyZone = TimeZone.getTimeZone("Europe/London");
        ZoneId modernZoneId = legacyZone.toZoneId();
        System.out.println("Modern ZoneId: " + modernZoneId);

        // Modern -> Legacy
        TimeZone backToLegacy = TimeZone.getTimeZone(modernZoneId);
        System.out.println("Legacy ID:     " + backToLegacy.getID());

        // Using ZoneId with ZonedDateTime
        ZonedDateTime londonNow = ZonedDateTime.now(modernZoneId);
        System.out.println("London Time:   " + londonNow);
    }
}
```
