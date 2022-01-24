package xyz.pangosoft.encinalbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pangosoft.encinalbackend.services.IRangeService;

@CrossOrigin(origins = {"http://localhost:4200", "https://encinal5-808d5.web.app", "https://condadoelencinal.com"})
@RestController
@RequestMapping("/api")
public class RangeApiController {

    @Autowired
    private IRangeService rangeService;


}
