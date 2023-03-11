package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicPetRepository extends JpaRepository<PetInfoByAPI, Long> {
}
