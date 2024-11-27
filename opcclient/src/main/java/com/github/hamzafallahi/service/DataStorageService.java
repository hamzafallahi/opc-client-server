/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.service;

import com.github.hamzafallahi.dto.OPCClientDATADto;
import com.github.hamzafallahi.exception.EtAuthException;


public interface DataStorageService {
    OPCClientDATADto createOPCClientDATA(OPCClientDATADto OPCClientDATADto) throws EtAuthException;

}

