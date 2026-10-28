# Date & Time Overview

Java provides two distinct API ecosystems for working with date and time:

1. **Legacy API (`java.util`, `java.sql`, `java.text`)**: Introduced in Java 1.0 and 1.1 (`Date`, `Calendar`, `GregorianCalendar`, `SimpleDateFormat`, `TimeZone`).
2. **Modern Date-Time API (`java.time`)**: Introduced in Java 8 (JSR-310) to address major design flaws in the legacy API (`LocalDate`, `LocalTime`, `LocalDateTime`, `Instant`, `Duration`, `Period`, `ZonedDateTime`, `DateTimeFormatter`).

---

## Why the Legacy API Was Replaced

The legacy date-time classes suffered from fundamental design issues:

1. **Mutability & Thread Safety**: Legacy classes (`Date`, `Calendar`, `SimpleDateFormat`) are mutable, making them unsafe to share across threads without synchronization.
2. **Off-by-One and Year Offsets**:
   - Years start from `1900` (e.g., year `2024` requires passing `124`).
   - Months are 0-indexed (`0` = January, `11` = December).
3. **Ambiguity**: `java.util.Date` represents both date and time (a millisecond timestamp from epoch), but cannot represent a date-only or time-only value.
4. **Precision**: Legacy types only support millisecond precision. `java.time` supports nanosecond precision.
5. **Inconsistent Timezone Handling**: `Date` has no timezone, yet its `toString()` method formats using the JVM default timezone, causing frequent confusion.

```java
// Legacy quirk: 18th December 2024
Date legacyDate = new Date(124, 11, 18); // Year offset (1900 + 124), Month (11 = Dec)

// Modern Java 8+: readable, intuitive, immutable
LocalDate modernDate = LocalDate.of(2024, Month.DECEMBER, 18);
```

---

## Comparison: Legacy vs Modern API

| Feature | Legacy API (`java.util`) | Modern API (`java.time`) |
| :--- | :--- | :--- |
| **Introduced** | Java 1.0 / 1.1 | Java 8 (JSR-310) |
| **Mutability** | Mutable (thread-unsafe) | Immutable (thread-safe) |
| **Separation of Concerns** | Mixed (`Date` holds date + time) | Distinct (`LocalDate`, `LocalTime`, `LocalDateTime`) |
| **Precision** | Milliseconds | Nanoseconds |
| **Month Indexing** | 0 to 11 | 1 to 12 (or `Month` enum) |
| **Year Representation** | Year minus 1900 | Actual 4-digit year (e.g., `2024`) |
| **Formatting** | `SimpleDateFormat` (not thread-safe) | `DateTimeFormatter` (thread-safe) |
| **Timezone Support** | `TimeZone` (clunky, mutable) | `ZoneId`, `ZoneOffset`, `ZonedDateTime` |

---

## Module Roadmap

- **Legacy Core**:
  - `02_Date.md`: `java.util.Date` and `java.sql.Date`
  - `03_Simple_Date_Format.md`: `SimpleDateFormat` patterns and thread-safety pitfalls
  - `04_Calendar.md`: `Calendar` and `GregorianCalendar`
  - `05_Time_Zone.md`: Legacy `TimeZone`
- **Modern `java.time` (Java 8+)**:
  - `06_New_Date_Time.md`: `LocalDate`, `LocalTime`, `LocalDateTime`
  - `07_Instant_and_Duration.md`: Machine timeline (`Instant`) and time amounts (`Duration`)
  - `08_Period.md`: Date amounts (`Period`) and `ChronoUnit`
  - `09_Zone_Offset.md`: `ZoneId`, `ZoneOffset`, `ZonedDateTime`, and DST
  - `10_Date_Time_Formatter.md`: Formatting and parsing with `DateTimeFormatter`
