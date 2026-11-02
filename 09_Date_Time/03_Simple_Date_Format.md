# SimpleDateFormat (`java.text.SimpleDateFormat`)

`SimpleDateFormat` is the legacy class used to format `java.util.Date` instances into strings and parse strings back into `Date` objects.

---

## 1. Common Format Pattern Symbols

| Symbol | Meaning | Example |
| :--- | :--- | :--- |
| `y` / `yyyy` | Astronomical year | `2024` |
| `M` / `MM` / `MMMM` | Month in year | `1`, `01`, `December` |
| `d` / `dd` | Day in month | `9`, `09` |
| `H` / `HH` | Hour in day (0–23) | `14` |
| `h` / `hh` | Hour in am/pm (1–12) | `02` |
| `m` / `mm` | Minute in hour | `30` |
| `s` / `ss` | Second in minute | `45` |
| `S` / `SSS` | Millisecond | `520` |
| `a` | AM / PM marker | `PM` |
| `z` / `Z` | Timezone name / RFC 822 offset | `IST`, `+0530` |

---

## 2. Formatting Date to String

```java
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Date now = new Date();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Standard: " + df1.format(now)); // e.g., 2024-12-18 14:30:00

        SimpleDateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy (E)");
        System.out.println("Custom:   " + df2.format(now)); // e.g., 18/December/2024 (Wed)
    }
}
```

---

## 3. Parsing String to Date

Parsing throws a checked `ParseException` when the input string deviates from the specified pattern:

```java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String input = "2024-12-18 14:30:00";
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date parsedDate = parser.parse(input);
            System.out.println("Parsed: " + parsedDate);
        } catch (ParseException e) {
            System.err.println("Parse failure: " + e.getMessage());
        }

        // Lenient parsing vs unparseable format
        try {
            // ❌ Throws ParseException: Unparseable date: "invalid-date"
            parser.parse("invalid-date");
        } catch (ParseException e) {
            System.out.println("Expected failure caught: " + e.getMessage());
        }
    }
}
```

---

## 4. Timezone Formatting

`Date` stores an instant without timezone context. Setting a `TimeZone` on `SimpleDateFormat` determines the zone offset applied during formatting and parsing:

```java
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

        // Format in New York
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        System.out.println("New York: " + sdf.format(now));

        // Format in UTC
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("UTC:      " + sdf.format(now));

        // Format in Tokyo
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        System.out.println("Tokyo:    " + sdf.format(now));
    }
}
```

---

## 5. Critical Pitfall: Thread Safety

> [!CAUTION]
> `SimpleDateFormat` is **stateful and NOT thread-safe**. Internally, it inherits a protected `Calendar calendar` field from `DateFormat`. Both `format()` and `parse()` modify this shared calendar instance. Sharing a static `SimpleDateFormat` across threads causes corrupted dates, silent data errors, or random `NumberFormatException`s.

### Safe Legacy Pattern: `ThreadLocal`

```java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    // Thread-safe legacy approach: One instance per thread
    private static final ThreadLocal<SimpleDateFormat> FORMATTER =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String formatDate(Date date) {
        return FORMATTER.get().format(date);
    }

    public static Date parseDate(String text) throws ParseException {
        return FORMATTER.get().parse(text);
    }

    public static void main(String[] args) throws ParseException {
        Date now = new Date();
        String formatted = formatDate(now);
        System.out.println("Formatted safely: " + formatted);

        Date parsed = parseDate(formatted);
        System.out.println("Parsed safely:    " + parsed);

        // Always clean up thread-local storage in thread-pool environments
        FORMATTER.remove();
    }
}
```

### Modern Recommended Replacement

In Java 8+, eliminate `SimpleDateFormat` entirely in favor of immutable, thread-safe `DateTimeFormatter`:

```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    // Thread-safe, immutable constant. Safe to share across all threads.
    private static final DateTimeFormatter FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        String formatted = now.format(FORMATTER);
        System.out.println("Formatted: " + formatted);

        LocalDateTime parsed = LocalDateTime.parse(formatted, FORMATTER);
        System.out.println("Parsed:    " + parsed);
    }
}
```
