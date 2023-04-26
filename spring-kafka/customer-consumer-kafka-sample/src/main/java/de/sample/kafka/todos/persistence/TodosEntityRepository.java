package de.sample.kafka.todos.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosEntityRepository extends JpaRepository<TodoEntity, Long> {
}
