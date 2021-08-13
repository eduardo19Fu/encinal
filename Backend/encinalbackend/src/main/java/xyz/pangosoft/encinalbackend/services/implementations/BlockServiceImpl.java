package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Block;
import xyz.pangosoft.encinalbackend.repositories.IBlockRepository;
import xyz.pangosoft.encinalbackend.services.IBlockService;

import java.util.List;

@Service
public class BlockServiceImpl implements IBlockService {

    @Autowired
    private IBlockRepository blockRepository;

    @Override
    public List<Block> listAll() {
        return blockRepository.findAll(Sort.by(Sort.Direction.DESC, "blockId"));
    }

    @Override
    public Block singleBlock(Integer idBlock) {
        return blockRepository.findById(idBlock).orElse(null);
    }

    @Override
    public Block save(Block block) {
        return blockRepository.save(block);
    }

    @Override
    public void delete(Integer idBlock) {
        blockRepository.deleteById(idBlock);
    }
}
