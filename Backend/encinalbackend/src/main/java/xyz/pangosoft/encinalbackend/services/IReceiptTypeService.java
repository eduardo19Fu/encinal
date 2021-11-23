package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.ReceiptType;

import java.util.List;

public interface IReceiptTypeService {

    public List<ReceiptType> listAll();

    public ReceiptType singleType(Integer id);
}
