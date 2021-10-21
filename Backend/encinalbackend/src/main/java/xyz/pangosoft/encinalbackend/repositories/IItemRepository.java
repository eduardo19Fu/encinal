package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Item;

public interface IItemRepository extends JpaRepository<Item, Integer> {
}
