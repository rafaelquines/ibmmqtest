package com.rafaelquines.mqtest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cidadao")
public class CidadaoController {

    @GetMapping()
    public ResponseEntity get() throws IllegalAccessException {
        throw new IllegalAccessException("erro teste");
        //return new ResponseEntity(HttpStatus.OK);
    }
}
