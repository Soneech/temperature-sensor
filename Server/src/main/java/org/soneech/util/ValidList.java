package org.soneech.util;

import com.google.common.collect.ForwardingList;
import jakarta.validation.Valid;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidList<T> extends ForwardingList<T> {
    private List<@Valid T> list;

    public ValidList() {
        this(new ArrayList<>());
    }

    public ValidList(List<@Valid T> list) {
        this.list = list;
    }

    @Override
    protected List<T> delegate() {
        return list;
    }
}
