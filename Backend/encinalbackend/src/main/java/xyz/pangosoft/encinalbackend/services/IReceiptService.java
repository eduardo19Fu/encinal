package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Receipt;

import java.util.List;

public interface IReceiptService {

    public List<Receipt> listAll();

    public Receipt singleReceipt(Integer id);

    public Receipt create(Receipt receipt);
}
