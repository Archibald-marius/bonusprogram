package partnership_program.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import partnership_program.models.Transactions;

import java.util.List;

@Repository
public interface TransactionDao extends CrudRepository<Transactions, Long> {

    @Query("SELECT sum (e.amountSpend) FROM Transactions e WHERE e.userId = (:id)")
    Double findAllDependant(@Param("id")Long id);
}
