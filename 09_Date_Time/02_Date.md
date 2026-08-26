# Date

Default implementation of date API returns current date and time. Default implementation calls System class currentTimeMillis method which is a native method which returns the systems time in milliseconds from January 1 1970 epoch time (A fixed date and time used as a reference from which a computer measures system time.)

Default constructor definition

```java
public Date() {    this(System.currentTimeMillis());}
```

@IntrinsicCandidatepublic static native long currentTimeMillis();

Default implementation giving current date and time

Date date = *new* Date();System.out.println(date);                       ***//Wed Dec 18 12:27:45 IST 2024***System.out.println(System.currentTimeMillis()); ***//1734505065137***

For specific dates we need to use a different constructor to which we have to pass Date, month , year time. Legacy Date API starts with 1900. If I want to print 18th December 2024, we must write like below. We must pass 124 for year 1900+ 124 for 2014. 11 for December as index starts from 0 Also midnight is returned by default This constructor is deprecated.

Date date = *new* Date(124,11,18);System.out.println(date);   ***//Wed Dec 18 00:00:00 IST 2024***

We can use getTime method to get the time in milliseconds.

Date date = *new* Date(124,11,18);System.out.println(date);                           ***//Wed Dec 18 00:00:00 IST 2024***System.out.println(date.getTime());                 ***//1734460200000***

If we pass milliseconds to Date constructer we will get the time. Below we are passing current time + 8400000 (1 day ) to get current date + 1day

Date date1 = *new* Date(date.getTime() + 86400000);   ***//Date + 1 days***System.out.println(date1);                          ***//Thu Dec 19 00:00:00 IST 2024***

Can use after method to compare 2 date to check if its greater or not

```java
System.out.println(date.after(date1));              //falseSystem.out.println(date.before(date1));             //before

```

There is also set method setDate. setMonth etc to set time but these are deprecated we should instead use calender

SQL date

With SQL date we can represent date without time. This was created to support DB columns which support date and not time. If we send date and time both it can cause exception.

Date date = *new* Date(System.currentTimeMillis());
