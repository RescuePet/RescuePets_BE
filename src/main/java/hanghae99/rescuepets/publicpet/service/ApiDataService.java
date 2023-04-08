package hanghae99.rescuepets.publicpet.service;

import hanghae99.rescuepets.common.dto.CustomException;
import hanghae99.rescuepets.common.dto.ResponseDto;
import hanghae99.rescuepets.common.entity.Member;
import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.PetStateEnum;
import hanghae99.rescuepets.common.entity.Post;
import hanghae99.rescuepets.memberpet.dto.PostShortResponseDto;
import hanghae99.rescuepets.publicpet.dto.PublicPetResponseDto;
import hanghae99.rescuepets.publicpet.repository.PublicPetInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static hanghae99.rescuepets.common.dto.ExceptionMessage.TEST;
import static hanghae99.rescuepets.common.dto.SuccessMessage.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiDataService {
    private final ApiScheduler apiScheduler;

    @Transactional
    public ResponseEntity<ResponseDto> apiCompareData(String pageNo, PetStateEnum state, String size) throws IOException {
        String apiUrl = apiScheduler.createApiUrl(pageNo, state, size);
        JSONArray itemList = apiScheduler.fetchDataFromApi(apiUrl);
        if (itemList != null) {
            apiScheduler.compareData(itemList, state);
        } else {
            log.error("API로부터 데이터를 받아오지 못했습니다.");
        }
        return ResponseDto.toResponseEntity(PUBLIC_PET_INFO_SAVE_SUCCESS);
    }

}
