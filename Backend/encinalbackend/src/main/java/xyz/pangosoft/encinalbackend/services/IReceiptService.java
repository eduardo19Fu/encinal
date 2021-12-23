package xyz.pangosoft.encinalbackend.services;

import net.sf.jasperreports.engine.JRException;
import xyz.pangosoft.encinalbackend.models.Receipt;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface IReceiptService {

    public List<Receipt> listAll();

    public Receipt singleReceipt(Integer id);

    public Receipt create(Receipt receipt);

    /******************** PRINT RECEIPT************************/
    public byte[] rptPrintReceipt(Integer idReceipt) throws JRException, FileNotFoundException, SQLException;
}
