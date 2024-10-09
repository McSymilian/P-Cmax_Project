package edu.ryder_czarnecki.process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Process {
    private String processName = null;
    private final int maxLength;
}
