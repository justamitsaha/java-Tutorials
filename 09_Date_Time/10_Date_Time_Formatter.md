# DateTimeFormatter (`java.time.format.DateTimeFormatter`)

`DateTimeFormatter` is the modern, thread-safe formatting and parsing engine for `java.time` types (`LocalDate`, `LocalTime`, `LocalDateTime`, `ZonedDateTime`).

---

## 1. Key Architectural Advantages

- **Thread-Safe & Immutable**: Instances can be stored safely in `public static final` constants and shared across application threads.
- **Strict Validation**: Rejects invalid dates by default without silent rollovers (configurable via `ResolverStyle`).
- **Domain-Specific Integration**: Maps directly to ISO-8601 specifications and modern temporal types.

---

## 2. Standard Predefined Formatters

Standard ISO formats require no pattern strings:

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2024, 12, 18);
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 18, 14, 30, 45);

        // Predefined ISO formatters
        System.out.println("ISO_LOCAL_DATE:      " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));      // 2024-12-18
        System.out.println("BASIC_ISO_DATE:      " + date.format(DateTimeFormatter.BASIC_ISO_DATE));      // 20241218
        System.out.println("ISO_LOCAL_DATE_TIME: " + dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // 2024-12-18T14:30:45

        // Default parsing for standard ISO strings requires no formatter parameter:
        LocalDate parsedIso = LocalDate.parse("2024-12-18");
        System.out.println("Parsed Default ISO:  " + parsedIso);
    }
}
```

---

## 3. Custom Patterns & The `yyyy` vs `YYYY` Trap

| Symbol | Meaning | Example |
| :--- | :--- | :--- |
| `y` / `yyyy` | Year (Calendar year) | `2024` |
| `Y` / `YYYY` | Week-based year (ISO-8601) | `2025` (for Dec 31, 2024!) |
| `M` / `MM` / `MMMM` | Month number / 2-digit / Full name | `1`, `01`, `December` |
| `d` / `dd` | Day of month | `5`, `05` |
| `H` / `HH` | Hour (0–23) | `9`, `09` |
| `h` / `hh` | Hour (1–12) | `9`, `09` |
| `m` / `mm` | Minute | `30` |
| `s` / `ss` | Second | `45` |
| `a` | AM / PM | `PM` |
| `z` / `VV` | Zone name / Zone ID | `EST` / `America/New_York` |

> [!CAUTION]
> **The `YYYY` (Week-Based Year) Trap**: Never use uppercase `YYYY` for standard calendar dates. A week belongs to the year in which the majority of its days fall. December 31, 2024 belongs to the first week of week-year 2025. Using `YYYY` causes dates in late December to format into the next year.

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        LocalDate newYearsEve = LocalDate.of(2024, 12, 31);

        // Correct: yyyy represents calendar year
        DateTimeFormatter correct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Correct (yyyy): " + newYearsEve.format(correct)); // 2024-12-31

        // ⚠️ BUG: YYYY represents week-based year
        DateTimeFormatter buggy = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        System.out.println("Buggy   (YYYY): " + newYearsEve.format(buggy));   // 2025-12-31 (Wrong year!)
    }
}
```

---

## 4. Parsing and `ResolverStyle` (`STRICT`, `SMART`, `LENIENT`)

When parsing non-existent dates (such as February 31):
- `SMART` (default): Adjusts the day to the last valid day of the month (Feb 29 in leap year).
- `STRICT`: Rejects the invalid date and throws `DateTimeParseException`. (Note: In strict mode, use `uuuu` for year rather than `yyyy`).
- `LENIENT`: Overflows the excess days into subsequent months.

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Test {
    public static void main(String[] args) {
        // 1. SMART resolver (Default)
        DateTimeFormatter smart = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.SMART);
        LocalDate resolvedSmart = LocalDate.parse("2024-02-31", smart);
        System.out.println("SMART resolved 02-31 to: " + resolvedSmart); // 2024-02-29

        // 2. STRICT resolver
        DateTimeFormatter strict = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            // ❌ Throws DateTimeParseException: Invalid date 'February 31'
            LocalDate.parse("2024-02-31", strict);
        } catch (DateTimeParseException e) {
            System.out.println("STRICT rejected invalid date: " + e.getMessage());
        }

        // 3. LENIENT resolver
        DateTimeFormatter lenient = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.LENIENT);
        LocalDate resolvedLenient = LocalDate.parse("2024-02-31", lenient);
        System.out.println("LENIENT resolved 02-31 to: " + resolvedLenient); // 2024-03-02
    }
}
```

---

## 5. Locale-Sensitive Formatting

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Test {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2024, 12, 18);

        // Pattern-based locale formatting
        DateTimeFormatter germanFmt = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.GERMAN);
        DateTimeFormatter frenchFmt = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);

        System.out.println("German: " + date.format(germanFmt)); // 18. Dezember 2024
        System.out.println("French: " + date.format(frenchFmt)); // 18 décembre 2024

        // Standard localized styles (FULL, LONG, MEDIUM, SHORT)
        DateTimeFormatter localizedFull = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(Locale.US);
        System.out.println("US Full: " + date.format(localizedFull)); // Wednesday, December 18, 2024
    }
}
```
