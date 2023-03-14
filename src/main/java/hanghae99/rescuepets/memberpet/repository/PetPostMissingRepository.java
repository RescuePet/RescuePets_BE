package hanghae99.rescuepets.memberpet.repository;
import hanghae99.rescuepets.common.entity.PetPostMissing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetPostMissingRepository extends JpaRepository<PetPostMissing, Long>{
    List<PetPostMissing> findAllByMemberId(Long petPostMissingId);
}