package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Transfer;

import java.util.List;

public interface ITransferService {

    public List<Transfer> listAllTransfers();

    public Transfer singleTransfer(Integer idtransfer);

    public Transfer save(Transfer transfer);
}
