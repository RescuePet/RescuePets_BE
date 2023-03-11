package hanghae99.rescuepets.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class PetPostMissing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date postedDate;
    private String happenPlace;
    private String popfile;
    private String kindCd;
    private String specialMark;
    private String content;
}
