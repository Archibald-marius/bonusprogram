package partnership_program.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import partnership_program.models.SiteUser;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<SiteUser, Long> {
    SiteUser findSiteUserById(Long id);

//    @Query("SELECT e FROM SiteUser e WHERE e.id = (:id)")
//    List<SiteUser> findAllDependant(@Param("id")Long id);
}
