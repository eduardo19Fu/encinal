package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.IdentificationType;

public interface IIdentificationTypeRepository extends JpaRepository<IdentificationType, Integer> {
}
