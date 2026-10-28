# Period (`java.time.Period`) & `ChronoUnit`

While `Duration` measures time-based amounts (hours, minutes, seconds), `Period` measures **date-based amounts** (years, months, days) on the human calendar.

![image55.png](../images/image55.png)

---

## 1. Quick Contrast: `Period` vs `Duration`

| Feature | `Period` | `Duration` |
| :--- | :--- | :--- |
| **Units** | Years, Months, Days | Seconds, Nanoseconds (Days, Hours, Minutes) |
| **Used with** | `LocalDate` | `Instant`, `LocalTime`, `LocalDateTime` |
| **ISO-8601 Format** | `P...` (e.g., `P2Y3M15D`) | `PT...` (e.g., `PT2H30M15S`) |
| **Daylight Saving Time** | Preserves calendar date | Preserves exact elapsed elapsed seconds |

---

## 2. Creating and Measuring with `Period`

```java
import java.time.LocalDate;
import java.time.Period;

// 1. Factory methods
Period twoYearsThreeMonths = Period.of(2, 3, 0); // P2Y3M
Period tenDays             = Period.ofDays(10);  // P10D

// 2. Measuring elapsed calendar difference between dates
LocalDate startDate = LocalDate.of(2022, 1, 1);
LocalDate endDate   = LocalDate.of(2024, 3, 18);

Period diff = Period.between(startDate, endDate);
System.out.println(diff); // P2Y2M17D (2 years, 2 months, 17 days)

System.out.println("Years:  " + diff.getYears());  // 2
System.out.println("Months: " + diff.getMonths()); // 2
System.out.println("Days:   " + diff.getDays());   // 17
```

> [!WARNING]
> `diff.getDays()` only returns the day component of the period (here: `17`), **not** the total days between the two dates!

---

## 3. Date Arithmetic & Scaling

```java
import java.time.LocalDate;
import java.time.Period;

LocalDate today = LocalDate.of(2024, 1, 1);
Period period = Period.ofMonths(2).plusDays(10); // P2M10D

// Adding / subtracting to LocalDate
LocalDate futureDate = today.plus(period);   // 2024-03-11
LocalDate pastDate   = today.minus(period);  // 2023-10-22

// Scaling periods
Period tripled = period.multipliedBy(3);    // P6M30D
Period negated = period.negated();         // P-2M-10D
```

---

## 4. Total Difference with `ChronoUnit`

When you need the **total elapsed difference in a single unit** (e.g., total days or total months between two dates) rather than a broken-down `Years/Months/Days` tuple, use `ChronoUnit.between()`:

```java
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

LocalDate start = LocalDate.of(2022, 1, 1);
LocalDate end   = LocalDate.of(2024, 3, 18);

long totalDays   = ChronoUnit.DAYS.between(start, end);   // 807 days
long totalMonths = ChronoUnit.MONTHS.between(start, end); // 26 months
long totalWeeks  = ChronoUnit.WEEKS.between(start, end);  // 115 weeks

System.out.println("Total Days:   " + totalDays);
System.out.println("Total Months: " + totalMonths);
```
