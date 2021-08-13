package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
}
