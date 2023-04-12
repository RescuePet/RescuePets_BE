package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface PublicPetInfoRepository extends JpaRepository<PetInfoByAPI, Long> {
    Optional<PetInfoByAPI> findByDesertionNo(String desertionNo);

    Optional<PetInfoByAPI> findById(Long Id);


    @Query(value = "SELECT * FROM pet_info_byapi WHERE true = true AND ST_DISTANCE_SPHERE(POINT(:Latitude, :Longitude), " +
            "POINT(pet_info_byapi.longitude, pet_info_byapi.latitude)) <= :distance ORDER BY pet_info_byapi.happen_dt, pet_info_byapi.created_at DESC", nativeQuery = true)
    Page<PetInfoByAPI> findApiByDistance( @Param("Latitude") Double Latitude,
                                   @Param("Longitude") Double Longitude, @Param("distance") Double distance, Pageable pageable);


    @Query(value = "SELECT * FROM pet_info_byapi WHERE true = true AND pet_info_byapi.kind_cd LIKE :searchValue ORDER BY pet_info_byapi.happen_dt, pet_info_byapi.created_at DESC", nativeQuery = true)
    Page<PetInfoByAPI> findApiByKindCd(@Param("searchValue") String searchValue, Pageable pageable);

    @Query(value = "SELECT * FROM pet_info_byapi WHERE true = true AND pet_info_byapi.care_nm LIKE :searchValue ORDER BY pet_info_byapi.happen_dt, pet_info_byapi.created_at DESC", nativeQuery = true)
    Page<PetInfoByAPI> findApiBycareNm( @Param("searchValue") String searchValue, Pageable pageable);


    @Query(value = "SELECT * FROM pet_info_byapi WHERE true = true AND pet_info_byapi.kind_cd LIKE :searchValue AND ST_DISTANCE_SPHERE(POINT(:Latitude, :Longitude), " +
            "POINT(pet_info_byapi.longitude, pet_info_byapi.latitude)) <= :distance ORDER BY pet_info_byapi.happen_dt, pet_info_byapi.created_at DESC", nativeQuery = true)
    Page<PetInfoByAPI> findApiByDistanceAndKindCd(@Param("Latitude") Double Latitude, @Param("Longitude") Double Longitude,
                                                  @Param("distance") Double distance, @Param("searchValue") String searchValue, Pageable pageable);

    @Query(value = "SELECT * FROM pet_info_byapi WHERE true = true AND pet_info_byapi.care_nm LIKE :searchValue AND ST_DISTANCE_SPHERE(POINT(:Latitude, :Longitude)," +
            " POINT(pet_info_byapi.longitude, pet_info_byapi.latitude)) <= :distance ORDER BY pet_info_byapi.happen_dt, pet_info_byapi.created_at DESC", nativeQuery = true)
    Page<PetInfoByAPI> findApiByDistanceAndCareNm(@Param("Latitude") Double Latitude, @Param("Longitude") Double Longitude,
                                                  @Param("distance") Double distance, @Param("searchValue") String searchValue, Pageable pageable);
}