# Calendar (`java.util.Calendar` & `GregorianCalendar`)

`java.util.Calendar` was introduced in Java 1.1 to separate date calculations, field extractions (year, month, day), and internationalization from `java.util.Date`.

`Calendar` is an abstract class. In standard Java runtimes, `Calendar.getInstance()` returns a concrete `java.util.GregorianCalendar`.

---

## 1. Creating and Inspecting `Calendar`

```java
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) {
        // Factory method using default locale and JVM timezone
        Calendar cal = Calendar.getInstance();

        // Direct GregorianCalendar instantiation
        // NOTE: Months are 0-indexed (Calendar.DECEMBER == 11)
        Calendar specific = new GregorianCalendar(2024, Calendar.DECEMBER, 18, 14, 30, 0);

        System.out.println("Calendar Class: " + cal.getClass().getName());
        System.out.println("Specific Date:  " + specific.getTime());
    }
}
```

---

## 2. Field Access: `get()`, `set()`, and Zero-Based Months

`Calendar` uses integer constants for field indexing:

```java
import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();

        // Reading fields
        int year   = cal.get(Calendar.YEAR);
        int month  = cal.get(Calendar.MONTH);        // 0-indexed: 0 = Jan, 11 = Dec
        int day    = cal.get(Calendar.DAY_OF_MONTH);
        int hour24 = cal.get(Calendar.HOUR_OF_DAY);   // 24-hour clock (0-23)
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        System.out.printf("Current: %04d-%02d-%02d %02d:%02d:%02d%n",
                year, month + 1, day, hour24, minute, second);

        // Setting fields (months require Calendar constants or 0-indexed int)
        cal.set(Calendar.YEAR, 2025);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        System.out.println("Modified: " + cal.getTime());
    }
}
```

---

## 3. Date Arithmetic: `add()` vs `roll()`

- **`add(field, amount)`**: Adjusts the field and cascades changes to larger calendar units (e.g., adding 30 days advances the month and possibly year).
- **`roll(field, amount)`**: Adjusts the field **without** cascading changes to larger calendar units (e.g., rolling month from December to January does **not** change the year).

```java
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) {
        // 1. add() cascades up
        Calendar calAdd = new GregorianCalendar(2024, Calendar.DECEMBER, 15);
        calAdd.add(Calendar.DAY_OF_MONTH, 20); // 15 + 20 = 35 -> rolls to January 2025
        System.out.println("After add:  " + calAdd.getTime()); // Jan 04, 2025

        // 2. roll() does NOT cascade up
        Calendar calRoll = new GregorianCalendar(2024, Calendar.DECEMBER, 15);
        calRoll.roll(Calendar.MONTH, 1); // December (11) + 1 wraps to January (0), year stays 2024!
        System.out.println("After roll: " + calRoll.getTime()); // Jan 15, 2024
    }
}
```

---

## 4. Leniency Control: `setLenient(false)`

By default, `Calendar` is **lenient**: passing invalid dates (e.g. February 30th) silently overflows into the next month. To enforce strict validation, set leniency to `false`:

```java
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) {
        Calendar cal = new GregorianCalendar();

        // Default: Lenient mode rolls invalid days forward
        cal.set(2024, Calendar.FEBRUARY, 32);
        System.out.println("Lenient: " + cal.getTime()); // Silent rollover to March 3, 2024

        // Strict mode throws IllegalArgumentException upon computing time
        cal.setLenient(false);
        cal.set(2024, Calendar.FEBRUARY, 32);

        try {
            // Recalculates fields; throws exception
            cal.getTime(); 
        } catch (IllegalArgumentException e) {
            System.out.println("Strict validation caught error: " + e.getMessage());
        }
    }
}
```

---

## 5. Bridging `Calendar` with Modern `java.time`

Convert legacy `Calendar` to `Instant` or `ZonedDateTime` at application boundaries:

```java
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();

        // 1. Calendar -> Instant
        Instant instant = cal.toInstant();
        System.out.println("Instant:       " + instant);

        // 2. Calendar -> ZonedDateTime (preserves calendar timezone)
        ZoneId zoneId = cal.getTimeZone().toZoneId();
        ZonedDateTime zdt = instant.atZone(zoneId);
        System.out.println("ZonedDateTime: " + zdt);

        // 3. ZonedDateTime -> GregorianCalendar
        Calendar backToCal = GregorianCalendar.from(zdt);
        System.out.println("Back to Cal:   " + backToCal.getTime());
    }
}
```
