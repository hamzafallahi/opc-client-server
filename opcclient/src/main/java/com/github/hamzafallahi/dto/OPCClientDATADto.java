/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OPCClientDATADto {
    private String id;
    //private String ServerUri;
    private String NodeID;
    private String Value;
    private String DisplayName;
    private String VariableType;
    private LocalDateTime Timestamp;
    private String FullNodePath;
}
