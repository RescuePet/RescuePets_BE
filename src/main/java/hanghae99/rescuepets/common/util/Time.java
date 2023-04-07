package hanghae99.rescuepets.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Time {

    public static String chatTime(LocalDateTime time) {
        String timeStr = "";
        LocalDate day = time.toLocalDate();
        if (day.compareTo(LocalDate.now()) == 0) {
            timeStr = time.format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko")));
        } else if (day.equals(LocalDate.now().minusDays(1))) { // 데이터 형태 변경
            timeStr = "어제";
        } else {
            timeStr = time.format(DateTimeFormatter.ofPattern("M월 d일"));
        }
        return timeStr;
    }
}
