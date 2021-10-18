package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Item;
import xyz.pangosoft.encinalbackend.repositories.IItemRepository;
import xyz.pangosoft.encinalbackend.services.IItemService;

import java.util.List;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private IItemRepository itemRepository;

    @Override
    public List<Item> listAll() {
        return this.itemRepository.findAll();
    }

    @Override
    public Page<Item> listAllPage(Pageable pageable) {
        return this.itemRepository.findAll(pageable);
    }

    @Override
    public Item singleItem(Integer id) {
        return this.itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item save(Item item) {
        return this.itemRepository.save(item);
    }
}
