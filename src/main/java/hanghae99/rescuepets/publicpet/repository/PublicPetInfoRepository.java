package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicPetInfoRepository extends JpaRepository<PetInfoByAPI, Long> {
    Optional<PetInfoByAPI>findByDesertionNo(String desertionNo);
    Optional<PetInfoByAPI>findById(Long Id);
}
