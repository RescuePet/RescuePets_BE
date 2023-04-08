package hanghae99.rescuepets.publicpet.repository;

import hanghae99.rescuepets.common.entity.PetInfoByAPI;
import hanghae99.rescuepets.common.entity.PetInfoCompare;
import hanghae99.rescuepets.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetInfoCompareRepository extends JpaRepository<PetInfoCompare, Long> {
}
