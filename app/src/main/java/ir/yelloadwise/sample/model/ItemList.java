package ir.yelloadwise.sample.model;

import java.io.Serializable;

import ir.yelloadwise.sample.enums.ListItemType;

public class ItemList implements Serializable {
    public ListItemType listItemType;
    public String id;
    public String title;
}
