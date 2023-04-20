package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;


import static hanghae99.rescuepets.common.entity.PetStateEnum.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiScheduler {
    private final ApiClient apiClient;
    private final ApiCompareData apiCompareData;

    @Scheduled(cron = "50 52 16 * * *")
    private void apiSchedule() throws IOException {
        log.info("apiSchedule 동작");
        long startTime = System.currentTimeMillis();//시작 시간
        PetStateEnum[] state = {NOTICE, PROTECT, END};
        int stateNo = 0;
        int pageNo = 0;
        String size = "1000";
        while (stateNo < 3) {
            log.info("State: " + state[stateNo] + ",  pageNo: " + pageNo + "을 API로부터 데이터 호출 --");
            pageNo++;
            String apiUrl = apiClient.createPublicDataApiUrl(String.valueOf(pageNo), state[stateNo], size);

            JSONArray itemList = apiClient.fetchDataFromApi(apiUrl);

            if (itemList == null || itemList.isEmpty()) {
                stateNo++;
                pageNo = 0;
                continue;
            }
            apiCompareData.compareData(itemList, state[stateNo]);
        }
        long endTime = System.currentTimeMillis(); //종료 시간
        long executionTime = endTime - startTime; //소요 시간 계산
        log.info("-------------------------Total Execution time: " + executionTime + "ms");
    }

}
