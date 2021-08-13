package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
