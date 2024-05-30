package anya.core.pagecore.common;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TestCore {
    PageUtils<Dummy, Long> pageUtils;
    Sort sort;

    public void testCoreInANutShell(){
        Dummy dummy = new Dummy();
        List<Dummy> listOfDummy = new ArrayList<>();
        Page<Dummy> dummyPage = pageUtils.findPaginated(dummy, 1, 15, 50);
        Page<Dummy> dummyPage1 = pageUtils.findPaginated(dummy, 1, 15, 50, Sort.by(Sort.Order.asc("BMI")));
    }
}
