# Date & Time Overview

Java provides two distinct date-time API ecosystems:

1. **Legacy API (`java.util`, `java.sql`, `java.text`)**: Shipped in Java 1.0 and 1.1 (`Date`, `Calendar`, `GregorianCalendar`, `SimpleDateFormat`, `TimeZone`).
2. **Modern Date-Time API (`java.time`)**: Introduced in Java 8 (JSR-310) to replace the legacy ecosystem (`LocalDate`, `LocalTime`, `LocalDateTime`, `Instant`, `Duration`, `Period`, `ZonedDateTime`, `DateTimeFormatter`).

---

## 1. Why the Legacy API Was Replaced

The legacy date-time classes suffered from severe architectural flaws:

1. **Mutability & Lack of Thread Safety**: `Date`, `Calendar`, and `SimpleDateFormat` are mutable. Sharing instances across threads without explicit synchronization introduces severe race conditions and state corruption.
2. **Confusing Offsets & Indexing**:
   - Years are offset from `1900` (e.g., year `2024` requires passing `124`).
   - Months are 0-indexed (`0` = January, `11` = December), while days of the month are 1-indexed.
3. **Type Ambiguity**: `java.util.Date` represents a millisecond instant from the Unix epoch, but its name implies date-only semantics. It cannot represent a date without a time or a time without a date.
4. **Millisecond Precision Limit**: Legacy types only support millisecond precision (`long` epoch millis). The `java.time` types support nanosecond precision.
5. **Misleading `toString()`**: `Date` has no timezone information, yet calling `date.toString()` formats the output using the JVM's default timezone, masking its true instant nature.

```java
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        // Legacy API: Year offset (1900 + 124), 0-indexed month (11 = December)
        @SuppressWarnings("deprecation")
        Date legacyDate = new Date(124, 11, 18);
        System.out.println("Legacy Date: " + legacyDate);

        // Modern API: Explicit year, Month enum or 1-indexed int, immutable
        LocalDate modernDate = LocalDate.of(2024, Month.DECEMBER, 18);
        System.out.println("Modern Date: " + modernDate);

        // Immutability: Modifications return new instances
        LocalDate nextWeek = modernDate.plusWeeks(1);
        System.out.println("Original:    " + modernDate); // 2024-12-18 (unchanged)
        System.out.println("Next Week:   " + nextWeek);   // 2024-12-25
    }
}
```

---

## 2. API Ecosystem Comparison

| Dimension | Legacy API (`java.util`, `java.text`) | Modern API (`java.time`) |
| :--- | :--- | :--- |
| **Specification** | Java 1.0 / 1.1 | Java 8 (JSR-310) |
| **Design Paradigm** | Mutable (thread-unsafe) | Immutable value-based objects (thread-safe) |
| **Domain Separation** | `Date` mixes date and time | Distinct types (`LocalDate`, `LocalTime`, `LocalDateTime`, `Instant`) |
| **Precision** | Milliseconds (`long`) | Nanoseconds (`long seconds` + `int nanos`) |
| **Month Representation** | 0 to 11 (`int`) | 1 to 12 (`int`) or `Month` enum |
| **Year Representation** | `year - 1900` | 4-digit astronomical year (`2024`) |
| **Formatting** | `SimpleDateFormat` (stateful, thread-unsafe) | `DateTimeFormatter` (immutable, thread-safe) |
| **Timezone Model** | `TimeZone` (mutable, flawed DST rules) | `ZoneId`, `ZoneOffset`, `ZoneRules` |
| **Amounts / Deltas** | Raw millisecond math | `Duration` (time-based) and `Period` (date-based) |

---

## 3. The Two Timelines Concept

The modern `java.time` package clearly distinguishes between two timelines:

```
[ Machine Timeline (UTC) ]              [ Human Calendar Timeline ]
Instant (epoch nanos)                    LocalDate (2024-12-18)
Duration (seconds/nanos)                 LocalTime (14:30:00)
                                         LocalDateTime (2024-12-18T14:30:00)
                                         Period (years/months/days)
                  │                                   │
                  └───────────────┬───────────────────┘
                                  ▼
                     [ Bridged by Timezone ]
                     ZonedDateTime (Instant + ZoneId)
                     OffsetDateTime (Instant + ZoneOffset)
```

- **Machine Time**: Continuous timeline of nanoseconds measured from the Unix epoch (`1970-01-01T00:00:00Z`). Unaffected by geographic locations, political boundaries, or Daylight Saving Time (DST).
- **Human / Calendar Time**: Culturally dependent representations (clocks and wall calendars) with days, months, leap years, and daylight saving adjustments.

---

## 4. Module Roadmap

- **Legacy Core**:
  - `02_Date.md`: `java.util.Date`, `java.sql.Date`, and bridge conversions.
  - `03_Simple_Date_Format.md`: `SimpleDateFormat` patterns and multi-threading race conditions.
  - `04_Calendar.md`: `Calendar`, `GregorianCalendar`, field manipulation, and leniency.
  - `05_Time_Zone.md`: Legacy `TimeZone` and conversion to `ZoneId`.
- **Modern `java.time` (Java 8+)**:
  - `06_New_Date_Time.md`: `LocalDate`, `LocalTime`, `LocalDateTime`, and `Clock`.
  - `07_Instant_and_Duration.md`: Machine timeline (`Instant`) and time amounts (`Duration`).
  - `08_Period.md`: Date amounts (`Period`) vs `ChronoUnit`.
  - `09_Zone_Offset.md`: `ZoneId`, `ZoneOffset`, `ZonedDateTime`, and DST transitions.
  - `10_Date_Time_Formatter.md`: Formatting, parsing, `ResolverStyle`, and the `YYYY` vs `yyyy` trap.
