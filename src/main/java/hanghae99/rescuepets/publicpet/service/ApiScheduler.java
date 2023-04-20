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
            log.info("State: " + state[stateNo] + ",  pageNo: " + pageNo + "을 API로부터 데이터 호출--------------------");
            pageNo++;
            long urlStartTime = System.currentTimeMillis();//시작 시간
            String apiUrl = apiClient.createPublicDataApiUrl(String.valueOf(pageNo), state[stateNo], size);
            long urlEndTime = System.currentTimeMillis(); //종료 시간
            long urlExecutionTime = urlEndTime - urlStartTime; //소요 시간 계산
            log.info("-------------------------urlExecutionTime time: " + urlExecutionTime + "ms");

            long fetchDataFromApiStartTime = System.currentTimeMillis();//시작 시간
            JSONArray itemList = apiClient.fetchDataFromApi(apiUrl);
            long fetchDataFromApiEndTime = System.currentTimeMillis(); //종료 시간
            long fetchDataFromApiExecutionTime = fetchDataFromApiEndTime - fetchDataFromApiStartTime; //소요 시간 계산
            log.info("-------------------------fetchDataFromApiExecutionTime time: " + fetchDataFromApiExecutionTime + "ms");

            if (itemList == null || itemList.isEmpty()) {
                log.info("State: " + state[stateNo] + ",  pageNo: " + pageNo + "을 API로부터 데이터를 받아오지 못했습니다.-------------------------------------------------------------------------");
                stateNo++;
                pageNo = 0;
                continue;
            }
            long compareDataStartTime = System.currentTimeMillis();//시작 시간
            apiCompareData.compareData(itemList, state[stateNo]);
            long compareDataApiEndTime = System.currentTimeMillis(); //종료 시간
            long compareDataApiExecutionTime = compareDataApiEndTime - compareDataStartTime; //소요 시간 계산
            log.info("-------------------------compareDataApiExecutionTime time: " + compareDataApiExecutionTime + "ms");
        }
        long endTime = System.currentTimeMillis(); //종료 시간
        long executionTime = endTime - startTime; //소요 시간 계산
        log.info("-------------------------Total Execution time: " + executionTime + "ms");
    }

}
