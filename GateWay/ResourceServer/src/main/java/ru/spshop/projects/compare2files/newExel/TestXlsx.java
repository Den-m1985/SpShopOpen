package ru.spshop.projects.compare2files.newExel;

import java.io.IOException;

public class TestXlsx {


    public static void main(String[] args) throws IOException {

        //String pathXLS = "c:\\Users\\User\\Downloads\\Voshod\\заказ СОБРАНО.xlsx";
        String pathXLS = "c:\\Users\\User\\Downloads\\Урал Тойс\\Счет ИП Кашлева.xlsx";




        String baseName = pathXLS.substring(0, pathXLS.lastIndexOf('.'));
        String extension = pathXLS.substring(pathXLS.lastIndexOf('.') + 1);
        System.out.println(baseName);
        System.out.println(extension);

    }
}
