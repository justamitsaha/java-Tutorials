# SimpleDateFormat (`java.text.SimpleDateFormat`)

`SimpleDateFormat` is the legacy class used to format `java.util.Date` instances into strings and parse strings back into `Date` objects.

---

## 1. Common Format Pattern Symbols

| Symbol | Meaning | Example |
| :--- | :--- | :--- |
| `y` / `yyyy` | Year | `2024` |
| `M` / `MM` / `MMMM` | Month in year | `1`, `01`, `December` |
| `d` / `dd` | Day in month | `9`, `09` |
| `H` / `HH` | Hour in day (0–23) | `14` |
| `h` / `hh` | Hour in am/pm (1–12) | `02` |
| `m` / `mm` | Minute in hour | `30` |
| `s` / `ss` | Second in minute | `45` |
| `a` | AM / PM marker | `PM` |
| `z` / `Z` | Timezone name / RFC 822 offset | `IST`, `+0530` |

---

## 2. Formatting Date to String

```java
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatExample {
    public static void main(String[] args) {
        Date now = new Date();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df1.format(now)); // 2024-12-18 14:30:00

        SimpleDateFormat df2 = new SimpleDateFormat("dd/MMMM/yyyy (E)");
        System.out.println(df2.format(now)); // 18/December/2024 (Wed)
    }
}
```

---

## 3. Parsing String to Date

Parsing can throw a checked `ParseException` if the input string does not match the pattern:

```java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseExample {
    public static void main(String[] args) {
        String input = "2024-12-18 14:30:00";
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date parsedDate = parser.parse(input);
            System.out.println("Parsed: " + parsedDate);
        } catch (ParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
        }
    }
}
```

---

## 4. Timezone Formatting

You can configure a specific timezone on `SimpleDateFormat`:

```java
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
System.out.println(sdf.format(new Date())); 
// e.g., 2024-12-18 04:00:00 EST
```

---

## 5. Critical Pitfall: Thread Safety

> [!WARNING]
> `SimpleDateFormat` is **NOT thread-safe**. Internally, it maintains mutable state in a shared `Calendar` field. Sharing a single `static SimpleDateFormat` instance across multiple threads leads to race conditions, garbled output, or random `NumberFormatException`s.

### Safe Legacy Workarounds:
1. Create a new instance per invocation (overhead).
2. Store inside a `ThreadLocal<SimpleDateFormat>`.

### Modern Recommended Fix:
Use **`DateTimeFormatter`** from `java.time.format` (introduced in Java 8), which is immutable, thread-safe, and faster.
