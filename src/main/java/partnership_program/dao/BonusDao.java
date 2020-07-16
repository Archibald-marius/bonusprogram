package partnership_program.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import partnership_program.models.BonusProgram;


@Repository
public interface BonusDao extends CrudRepository<BonusProgram, Long> {

    @Query("SELECT e FROM BonusProgram e WHERE e.user = (:user)")
    BonusProgram findByUser(@Param("user")Long user);
}
