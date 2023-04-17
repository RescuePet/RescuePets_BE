package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiDataService {
    private final ApiClient apiClient;
    private final ApiCompareData apiCompareData;

    @Transactional
    public ResponseEntity<ResponseDto> apiCompareData(String pageNo, PetStateEnum state, String size, Member member) throws IOException {
        String apiUrl = apiClient.createPublicDataApiUrl(pageNo, state, size);
        JSONArray itemList = apiClient.fetchDataFromApi(apiUrl);
        if (itemList != null) {
            apiCompareData.compareData(itemList, state);
        } else {
            log.error("API로부터 데이터를 받아오지 못했습니다.");
        }
        return ResponseDto.toResponseEntity(PUBLIC_PET_INFO_SAVE_SUCCESS);
    }

}
