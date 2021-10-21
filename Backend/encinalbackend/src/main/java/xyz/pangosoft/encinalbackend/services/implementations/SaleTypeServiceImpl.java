package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.SaleType;
import xyz.pangosoft.encinalbackend.repositories.ISaleTypeRepository;
import xyz.pangosoft.encinalbackend.services.ISaleTypeService;

import java.util.List;

@Service
public class SaleTypeServiceImpl implements ISaleTypeService {

    @Autowired
    private ISaleTypeRepository saleTypeRepository;

    @Override
    public List<SaleType> listSaleTypes() {
        return saleTypeRepository.findAll();
    }

    @Override
    public SaleType singleSaleType(Integer id) {
        return saleTypeRepository.findById(id).orElse(null);
    }

    @Override
    public SaleType save(SaleType saleType) {
        return saleTypeRepository.save(saleType);
    }
}
