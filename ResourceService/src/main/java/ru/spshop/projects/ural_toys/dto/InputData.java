package ru.spshop.projects.ural_toys.dto;

import lombok.Getter;

@Getter
public class InputData {
    private final int numberSheet = 0;  // номер страницы в файле.
    private final int cellName = 2;
    private final int cellEXL = 4;  // Cell with articular
    private final int cellCode = 5;  // код товара
    private final int cellPriceXLS = 6;  // price без скидки
    private final int minToOrder = 8; // миним кол-во для заказа
    private final int cellWriteItem = 9;  //номер строки куда мы записываем

}
