package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Seller;

import java.util.List;

public interface ISellerService {

    public List<Seller> listSellers();

    public Seller singleSeller(Integer idSeller);

    public Seller save(Seller seller);

    public void delete(Integer idSeller);
}
