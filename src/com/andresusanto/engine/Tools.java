/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andresusanto.engine;

import com.andresusanto.option.SpacingOption;

/**
 *
 * @author Andre
 */
public class Tools {
    public static SpacingOption intValToSpacingOption(int value){
        if (value == 0) return SpacingOption.DEFAULT;
        if (value == 1) return SpacingOption.NO_SPACE;
        return SpacingOption.GROUP_5;
    }
    
    public static float calculatePSNR(Picture picture1, Picture picture2){
        return 0;
    }
}
