package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicPetRepository extends JpaRepository<PetInfoByAPI, Long> {
    Optional<PetInfoByAPI>findByDesertionNo(String desertionNo);
}
