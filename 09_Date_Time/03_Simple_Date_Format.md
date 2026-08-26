# Simple Date Format

Normal date objects give this format Wed Dec 18 00:00:00 IST 2024. To get a specific format use can use SimpleDateFormat in java.text package. Minute is mm and month is represented as MM.

Date currentDate = *new* Date();System.out.println(currentDate); ***// Fri Feb 09 14:52:01 IST 2024***SimpleDateFormat dateFormat = *new* SimpleDateFormat("yy-MMMM-dd HH:mm:ss");String formattedDate = dateFormat.format(currentDate);System.out.println(formattedDate);SimpleDateFormat dateFormat1 = *new* SimpleDateFormat("MM/dd/yyyy");String formattedDate1 = dateFormat1.format(currentDate);System.out.println(formattedDate1);

Sometime we receive time in string, in that case we can parse using Simple Date format

String dateString = "2100-09-26 14:30:00";SimpleDateFormat dateFormat2 = *new* SimpleDateFormat("yyyy-MM-dd HH:mm:ss");Date parsedDate = dateFormat2.parse(dateString);System.out.println(parsedDate);
