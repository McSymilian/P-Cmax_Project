package edu.ryder_czarnecki.instance.util;

import edu.ryder_czarnecki.instance.DataInstance;

import java.util.ArrayList;

public class DeepCopy implements Copy {
    @Override
    public DataInstance copy(DataInstance dataInstance) {
        return new DataInstance(dataInstance.processorsCount(), new ArrayList<>(dataInstance.processList()));
    }
}
