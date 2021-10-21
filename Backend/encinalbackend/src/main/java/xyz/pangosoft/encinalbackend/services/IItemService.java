package xyz.pangosoft.encinalbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.pangosoft.encinalbackend.models.Item;

import java.util.List;

public interface IItemService {

    public List<Item> listAll();

    public Page<Item> listAllPage(Pageable pageable);

    public Item singleItem(Integer id);

    public Item save(Item item);
}
