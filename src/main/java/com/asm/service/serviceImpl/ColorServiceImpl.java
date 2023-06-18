package com.asm.service.serviceImpl;

import com.asm.entity.Color;
import com.asm.repository.ColorRepository;
import com.asm.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    ColorRepository colorRepository;
    @Override
    public Color getColorByProductIdAndColorName(String productId, String colorName) {
        return colorRepository.getColorByProductIdAndColorName(productId,colorName);
    }
}
