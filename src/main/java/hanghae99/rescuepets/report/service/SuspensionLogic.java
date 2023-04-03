package hanghae99.rescuepets.report.service;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.LocalTime;
public class SuspensionLogic {

    public static boolean shouldSuspend(LocalDateTime lastActivityDate) {
        // 현재 날짜를 가져온다.
        LocalDateTime currentDate = LocalDateTime.now();
        // 사용자의 마지막 활동일과 현재 날짜 사이의 차이를 계산한다.
        long daysSinceLastActivity = ChronoUnit.DAYS.between(lastActivityDate, currentDate);
        // 차이가 3일 이상인 경우 true를 반환하고, 그렇지 않은 경우 false를 반환한다.
        return daysSinceLastActivity >= 3;
    }

    public static Duration getTimeDifference(LocalDateTime lastActivityDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Duration duration = Duration.between(lastActivityDateTime, currentDateTime);
        Duration difference = Duration.ofHours(72).minus(duration);
        return difference;
    }

    public static String toKoreanDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("일 ");
        }
        if (hours > 0) {
            sb.append(hours).append("시간 ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("분 ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("초");
        }
        return sb.toString();
    }
}



