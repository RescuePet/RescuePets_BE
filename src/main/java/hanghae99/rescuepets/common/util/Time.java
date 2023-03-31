package hanghae99.rescuepets.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Time {

    public static String chatTime(LocalDateTime time) {
        long between = ChronoUnit.SECONDS.between(LocalDateTime.now(), time);
        String timeStr;
        if (between < 60) {
            timeStr = "방금 전";
        } else if (between < 60 * 60) {
            timeStr = (between / 60) + "분 전";
        } else if (between < 60 * 60 * 24) {
            timeStr = (between / 3600) + "시간 전";
        } else {
            timeStr = time.format(DateTimeFormatter.ofPattern("MM월 dd일"));
        }
        return timeStr;
    }
}
