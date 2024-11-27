/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.repository;

import com.github.hamzafallahi.entity.OPCClientDATA;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataStorageRepository extends MongoRepository<OPCClientDATA, String>  {

}
