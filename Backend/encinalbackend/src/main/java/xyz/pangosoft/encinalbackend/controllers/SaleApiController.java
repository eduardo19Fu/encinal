package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.pangosoft.encinalbackend.models.Sale;
import xyz.pangosoft.encinalbackend.models.SaleType;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.services.ISaleService;
import xyz.pangosoft.encinalbackend.services.ISaleTypeService;
import xyz.pangosoft.encinalbackend.services.IStatusService;
import xyz.pangosoft.encinalbackend.services.ITerrainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
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

    @GetMapping("/sales")
    public List<Sale> index(){
        return saleService.listSales();
    }

    @GetMapping("/sales/page/{page}")
    public Page<Sale> indexPaginate(@PathVariable("page") Integer page){
        return saleService.listSales(PageRequest.of(page, 5));
    }

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

    @PostMapping("/sales/{type}")
    public ResponseEntity<?> create(@PathVariable("type") Integer type, @RequestBody Sale sale, BindingResult result){

        Map<String, Object> response = new HashMap<>();
        Sale newSale = null;
        Status status = null;
        SaleType saleType = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '".concat(err.getField().concat("' ")).concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            if(type == 1){
                status = statusService.singleStatus(14);
                saleType = saleTypeService.singleSaleType(type);

                sale.setStatus(status);
                sale.setSaleType(saleType);
                newSale = saleService.save(sale);
            } else if(type == 2){

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

    @GetMapping("/sales/sales-types")
    public List<SaleType> listSaleTypes(){
        return saleTypeService.listSaleTypes();
    }
}
