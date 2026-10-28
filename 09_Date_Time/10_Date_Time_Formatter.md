# DateTimeFormatter (`java.time.format.DateTimeFormatter`)

`DateTimeFormatter` is the modern replacement for `SimpleDateFormat`. It is designed specifically for the `java.time` types (`LocalDate`, `LocalTime`, `LocalDateTime`, `ZonedDateTime`, etc.).

---

## 1. Key Advantages over `SimpleDateFormat`

- **Thread-Safe & Immutable**: Can be safely stored in `public static final` constants and shared across application threads.
- **Strict Parsing**: Rejects impossible dates by default (e.g., February 31) instead of leniently rolling over.
- **Type-Specific**: Integrates cleanly with all Java 8+ temporal types.

---

## 2. Predefined Standard Formatters

Java provides standard ISO formatters out of the box:

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

LocalDate date = LocalDate.of(2024, 12, 18);

System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // 2024-12-18
System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE)); // 20241218
```

---

## 3. Custom Pattern Formatting

Pattern letters are similar to `SimpleDateFormat`:

| Pattern | Meaning | Example |
| :--- | :--- | :--- |
| `yyyy` / `yy` | Year / 2-digit Year | `2024` / `24` |
| `MM` / `MMM` / `MMMM`| Month number / Short / Full name | `12` / `Dec` / `December` |
| `dd` | Day of month | `18` |
| `HH` / `hh` | Hour (0–23 / 1–12) | `14` / `02` |
| `mm` | Minute | `30` |
| `ss` | Second | `45` |
| `a` | AM/PM marker | `PM` |
| `z` / `VV` | Timezone name / Zone ID | `EST` / `America/New_York` |

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

LocalDate date = LocalDate.of(2024, 12, 18);
LocalTime time = LocalTime.of(15, 30, 0);
LocalDateTime dateTime = LocalDateTime.of(date, time);

// Format Date
DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
System.out.println(date.format(dateFormatter)); // 18/12/2024

// Format Time
DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
System.out.println(time.format(timeFormatter)); // 3:30 PM

// Format DateTime
DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
System.out.println(dateTime.format(dtFormatter)); // 2024-12-18 15:30:00
```

---

## 4. Parsing Strings into Date-Time Objects

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

LocalDate parsedDate = LocalDate.parse("2024-12-18", df);
LocalTime parsedTime = LocalTime.parse("14:30:00", tf);
LocalDateTime parsedDateTime = LocalDateTime.parse("18/12/2024 14:30:00", dtf);

System.out.println("Parsed Date:     " + parsedDate);
System.out.println("Parsed Time:     " + parsedTime);
System.out.println("Parsed DateTime: " + parsedDateTime);
```

> [!NOTE]
> When using standard ISO formats (e.g., `"2024-12-18"`), you don't even need to pass a formatter:
> `LocalDate d = LocalDate.parse("2024-12-18");`

---

## 5. Locale-Sensitive Formatting

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

LocalDate date = LocalDate.of(2024, 12, 18);

DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.GERMAN);
System.out.println(date.format(germanFormatter)); // 18. Dezember 2024

DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
System.out.println(date.format(frenchFormatter)); // 18 décembre 2024
```
