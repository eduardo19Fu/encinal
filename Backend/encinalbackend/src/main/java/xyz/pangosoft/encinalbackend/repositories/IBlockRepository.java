package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Block;

public interface IBlockRepository extends JpaRepository<Block, Integer> {
}
