/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.controller;


import com.github.hamzafallahi.dto.OPCClientDATADto;


import com.github.hamzafallahi.service.DataStorageService;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;



@AllArgsConstructor
@RestController

@RequestMapping(value ="/api/OPCClientDATA", consumes = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Authentication API", description = "API for OPCClientDATA authentication")
public class OPCClientDATAdtoController {
    private DataStorageService oPCClientDATAService;

    @PostMapping("/register")
    @ApiOperation(value = "register ", notes = "register the OPCClientDATA")
    public ResponseEntity<Map<String, String>> createOPCClientDATA(@RequestBody OPCClientDATADto oPCClientDATAdto){
        OPCClientDATADto savedOPCClientDATA = oPCClientDATAService.createOPCClientDATA(oPCClientDATAdto) ;
        Map<String, String> response = new HashMap<>();
        response.put("id",savedOPCClientDATA.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}

