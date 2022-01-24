package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Range;

public interface IRangeRepository extends JpaRepository<Range, Integer> {
}
