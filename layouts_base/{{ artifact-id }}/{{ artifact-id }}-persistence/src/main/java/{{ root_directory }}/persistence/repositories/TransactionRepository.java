package {{root_package}}.persistence.repositories;

import {{root_package}}.persistence.entities.TransactionEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Archetect
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {

}