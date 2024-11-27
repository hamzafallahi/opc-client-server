/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.service.impl;

import com.github.hamzafallahi.dto.OPCClientDATADto;
import com.github.hamzafallahi.entity.OPCClientDATA;
import com.github.hamzafallahi.exception.EtAuthException;
import com.github.hamzafallahi.repository.DataStorageRepository;
import com.github.hamzafallahi.service.DataStorageService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.hamzafallahi.mapper.DATAMapper.mapToOPCClientDATA;
import static com.github.hamzafallahi.mapper.DATAMapper.mapToOPCClientDATADto;

@Service
@AllArgsConstructor
@Transactional
public class DataStorageServiceImpl implements DataStorageService {
    private final DataStorageRepository oPCClientDATARepository;

    @Override
    public OPCClientDATADto createOPCClientDATA(OPCClientDATADto OPCClientDATADto) throws EtAuthException {
        OPCClientDATA opcClientDATA = mapToOPCClientDATA(OPCClientDATADto);
        OPCClientDATA savedOPCClientDATA = oPCClientDATARepository.save(opcClientDATA);
        return mapToOPCClientDATADto(savedOPCClientDATA);
    }


}
