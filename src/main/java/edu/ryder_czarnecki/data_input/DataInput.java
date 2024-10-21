package edu.ryder_czarnecki.data_input;

import edu.ryder_czarnecki.instance.DataInstance;

import java.io.InputStream;

public interface DataInput {
    DataInstance parse(InputStream inputStream) throws InstanceInputException;

}
