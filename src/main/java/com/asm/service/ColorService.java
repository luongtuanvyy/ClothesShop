package com.asm.service;

import com.asm.entity.Color;

public interface ColorService {

    Color getColorByProductIdAndColorName(String productId, String colorName);

}
