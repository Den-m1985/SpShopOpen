package ru.spshop.projects.projects_services.csvFilter.csv;

import java.util.ArrayList;
import java.util.List;

public class DuplicateGoods {


    public List<StructureCSV> duplicateGoods(List<StructureCSV> duplicateNames) {

        List<StructureCSV> Names = new ArrayList<>();
        for (StructureCSV row : duplicateNames) {
            String name = row.getName();
            String size = row.getArtucul();
            int price = row.getPrice();
            int item = row.getItem();
            boolean duplicated = false;
            for (StructureCSV existing : Names) {
                String str1 = existing.getName();
                String str2 = existing.getArtucul();
                int existingItem = existing.getItem();
                if (str1.equals(name)) {
                    if (str2.equals(size)) {
                        existing.setItem(existingItem + item);
                        duplicated = true;
                        break;
                    }
                }
            }
            if (!duplicated) {
                Names.add(new StructureCSV(name, size, price, item));
            }
        }
        return Names;
    }

}
