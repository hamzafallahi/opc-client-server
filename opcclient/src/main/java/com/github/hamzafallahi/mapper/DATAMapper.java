/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.mapper;


import com.github.hamzafallahi.dto.OPCClientDATADto;
import com.github.hamzafallahi.entity.OPCClientDATA;

public class DATAMapper {
    public static OPCClientDATADto mapToOPCClientDATADto(OPCClientDATA data){
        return new OPCClientDATADto(
                data.getId(),
                //data.getServerUri(),
                data.getNodeID(),
                data.getValue(),
                data.getDisplayName(),
                data.getVariableType(),
                data.getTimestamp(),
                data.getFullNodePath()


        );

    }
    public static OPCClientDATA mapToOPCClientDATA(OPCClientDATADto dataDto){
        return new OPCClientDATA(
                dataDto.getId(),
                dataDto.getId(),
                //dataDto.getServerUri(),
                dataDto.getNodeID(),
                dataDto.getDisplayName(),
                dataDto.getVariableType(),
                dataDto.getTimestamp(),
                dataDto.getFullNodePath()



        );

    }
}
