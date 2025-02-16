package org.delaware.tools.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attribute implements Serializable {
    private String statBonus = " ";
    private String statFromBonus;
    private String statCost = " ";
    private float bonus;
    private float cost;
}
