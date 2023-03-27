package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.PetStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import static hanghae99.rescuepets.common.dto.SuccessMessage.PUBLIC_PET_INFO_SAVE_SUCCESS;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiDataService {
    private final ApiScheduler apiScheduler;

//    @Transactional
//    public ResponseEntity<ResponseDto> apiDataSave(String pageNo, PetStateEnum state, String size) throws IOException {
//        String apiUrl = apiScheduler.createApiUrl(pageNo, state, size);
//        JSONArray itemList = apiScheduler.fetchDataFromApi(apiUrl);
//        if (itemList != null) {
//            apiScheduler.saveOrUpdateFromApi(itemList, state.getState());
//        } else {
//            log.info("API로부터 데이터를 받아오지 못했습니다.");
//        }
//        return ResponseDto.toResponseEntity(PUBLIC_PET_INFO_SAVE_SUCCESS);
//    }
    @Transactional
    public ResponseEntity<ResponseDto> apiCompareData(String pageNo, PetStateEnum state, String size) throws IOException {
        String apiUrl = apiScheduler.createApiUrl(pageNo, state, size);
        JSONArray itemList = apiScheduler.fetchDataFromApi(apiUrl);
        if (itemList != null) {
            apiScheduler.compareData(itemList, state);
        } else {
            log.info("API로부터 데이터를 받아오지 못했습니다.");
        }
        return ResponseDto.toResponseEntity(PUBLIC_PET_INFO_SAVE_SUCCESS);
    }
}
