package demo;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {

  @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
  @Query("FROM Person")
  Stream<Person> all();
}
