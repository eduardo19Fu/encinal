package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.SaleType;

import java.util.List;

public interface ISaleTypeService {

    public List<SaleType> listSaleTypes();

    public SaleType singleSaleType(Integer id);

    public SaleType save(SaleType saleType);
}
