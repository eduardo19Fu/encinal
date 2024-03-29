package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.*;
import xyz.pangosoft.encinalbackend.services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5-808d5.web.app", "https://condadoelencinal.com"})
@RestController
@RequestMapping("/api")
public class SaleApiController {

    @Autowired
    private ISaleService saleService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private ITerrainService terrainService;

    @Autowired
    private ISaleTypeService saleTypeService;

    @Autowired
    private ISellerService sellerService;

    @Autowired
    private IBlockService blockService;

    @Autowired
    private IPaymentAgreementService paymentAgreementService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IClientTerrainService clientTerrainService;

    @Autowired
    private IClientService clientService;

    @GetMapping("/sales/daily-sales")
    public Double dailySales(){
        if(this.saleService.reporDailySales() == null){
            return 0.00;
        }
        return this.saleService.reporDailySales();
    }

    @GetMapping("/sales")
    public List<Sale> index(
            @RequestParam(required = false) String initDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer idManzana) throws ParseException {

        Date date1;
        Date date2;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        if((initDate == null || endDate == null) && idManzana != null) {
            return saleService.listSalesByBlock(idManzana);
        } else if(idManzana == null && (initDate != null || endDate != null)){
            date1 = format.parse(initDate);
            date2 = format.parse(endDate);
            return saleService.listSalesByDate(date1, date2);
        } else if(idManzana != null && (initDate != null || endDate != null)){
            date1 = format.parse(initDate);
            date2 = format.parse(endDate);
            return saleService.listSalesByBlockAndDate(idManzana, date1, date2);
        } else{
            return saleService.listSales();
        }
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping("/sales/page/{page}")
    public Page<Sale> indexPaginate(@PathVariable("page") Integer page){
        return saleService.listSales(PageRequest.of(page, 5));
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping("/sales/{id}")
    public ResponseEntity<?> findSale(@PathVariable("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        Sale sale = null;

        try{
            sale = saleService.singleSale(id);
        }catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(sale == null){
            response.put("message", "¡La venta con ID: ".concat(id.toString()).concat(", no se encuentra registrada en la Base de Datos!"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Sale>(sale, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @GetMapping(value = "/sales/client/{id}")
    public ResponseEntity<?> getByClient(@PathVariable Integer id) {

        List<Sale> sales = new ArrayList<>();
        Client client = null;
        Map<String, Object> response = new HashMap<>();

        try {
            client = clientService.singleClient(id);
            sales = saleService.listSalesByClient(client);

        } catch (DataAccessException e) {
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (client == null) {
            response.put("message", "¡El cliente solicitado no se encuentra registrado en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (sales.size() <= 0) {
            response.put("message", "El cliente solicitado no cuenta con ninguna venta registrada a su nombre");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO"})
    @PostMapping("/sales")
    public ResponseEntity<?> create(@RequestBody Sale sale, BindingResult result){

        Map<String, Object> response = new HashMap<>();

        Sale newSale = null;
        Status status = null;
        SaleType saleType = null;
        Terrain terrain = null;
        Seller seller = null;

        ClientTerrain clientTerrain = null;
        ClientTerrain newClientTerrain = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            if(sale.getSaleType().getSaleTypeId() == 1) {
                sale.setStatus(statusService.singleStatus(14));
                terrain = sale.getTerrain();
                terrain.setStatus(statusService.singleStatus(11));
                terrainService.save(terrain);

                newSale = saleService.save(sale);

                /*clientTerrain.setClient(sale.getClient());
                clientTerrain.setTerrain(sale.getTerrain());

                newClientTerrain = clientTerrainService.save(clientTerrain);
                System.out.println(newClientTerrain);*/

            } else {
                sale.setStatus(statusService.singleStatus(17));
                terrain = sale.getTerrain();
                terrain.setStatus(statusService.singleStatus(12));
                terrainService.save(terrain);

                newSale = saleService.save(sale);

                /*System.out.println(sale.getClient());

                clientTerrain.setClient(this.clientService.singleClient(sale.getClient().getClientId()));
                clientTerrain.setTerrain(this.terrainService.singleTerrain(sale.getTerrain().getTerrainId()));

                newClientTerrain = clientTerrainService.save(clientTerrain);
                System.out.println(newClientTerrain);*/
            }


        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Venta realizada con éxito!");
        response.put("sale", newSale);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @Secured(value = {"ROLE_ADMIN", "ROLE_SECRETARIO", "ROLE_SUPERADMIN"})
    @DeleteMapping("/sales/cancel/{id}")
    public ResponseEntity<?> cancel(@PathVariable("id") Integer saleId){

        Sale saleToCancel = null;
        PaymentAgreement paymentAgreementToCancel = null;
        Terrain terrain = null;

        Status statusSale = null;
        Status statusPaymentAgreement = null;
        Status statusPayment = null;
        Status statusTerrain = null;

        Map<String, Object> response = new HashMap<>();

        try{
            saleToCancel = saleService.singleSale(saleId);
            statusSale = statusService.singleStatusName("Anulado", "Sale");
            statusTerrain = statusService.singleStatusName("En Venta", "Terrain");

            if(saleToCancel.getPaymentAgreement() == null){
                saleToCancel.setStatus(statusSale);

                terrain = saleToCancel.getTerrain();
                terrain.setStatus(statusTerrain);

                saleService.save(saleToCancel);
                terrainService.save(terrain);
            } else {
                statusPaymentAgreement = statusService.singleStatusName("Anulado", "Payment Agreement");
                statusPayment = statusService.singleStatusName("Anulado", "Payment");

                saleToCancel.setStatus(statusSale);

                terrain = saleToCancel.getTerrain();
                terrain.setStatus(statusTerrain);

                paymentAgreementToCancel = saleToCancel.getPaymentAgreement();
                paymentAgreementToCancel.setStatus(statusPaymentAgreement);

                saleService.save(saleToCancel);
                terrainService.save(terrain);
                paymentAgreementService.save(paymentAgreementToCancel);

                for(Payment paymentToCancel : paymentAgreementToCancel.getPayments()){
                    paymentToCancel.setStatus(statusPayment);
                    paymentService.save(paymentToCancel);
                }

            }
        } catch(DataAccessException e){
            response.put("message", "¡Ha ocurrido un error en la Base de Datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡La venta ha sido anulada con éxito!");
        response.put("sale", saleToCancel);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/sales/sales-types")
    public List<SaleType> listSaleTypes(){
        return saleTypeService.listSaleTypes();
    }
}
