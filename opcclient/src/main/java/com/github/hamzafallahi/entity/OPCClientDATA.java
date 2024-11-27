/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "OPCClientDATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OPCClientDATA {
    @Id
    private String id;
    //private String ServerUri;
    private String NodeID;
    private String Value;
    private String DisplayName;
    private String VariableType;
    private LocalDateTime Timestamp;
    private String FullNodePath;

}
