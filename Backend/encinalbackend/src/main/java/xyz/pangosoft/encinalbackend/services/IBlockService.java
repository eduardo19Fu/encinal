package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Block;

import java.util.List;

public interface IBlockService {

    public List<Block> listAll();

    public Block singleBlock(Integer idBlock);

    public Block save(Block block);

    public void delete(Integer idBlock);
}
