package edu.ryder_czarnecki.instance.util;

import edu.ryder_czarnecki.instance.DataInstance;

public class LightCopy implements Copy {
    @Override
    public DataInstance copy(DataInstance dataInstance) {
        return new DataInstance(dataInstance.processorsCount(), dataInstance.processList());
    }
}
