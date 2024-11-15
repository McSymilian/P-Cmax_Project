package edu.ryder_czarnecki.engine.util;

import java.util.List;

import edu.ryder_czarnecki.process.Process;

public class Compare {
    public double compare(
            List<Process> list1,
            List<Process> list2
    ) {
        int total = list1.size(); //przydałoby się upewnić czy list2 jest równej długości
        int matching = 0;
        for (int i = 0; i < total; i++){
            if (list1.get(i).equals(list2.get(i))){
                matching++;
            }
        }
        return (double) matching / (double) total; //test żeby sprawdzić czy return należy do <0;1>
    }
}
