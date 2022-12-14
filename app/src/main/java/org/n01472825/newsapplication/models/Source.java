package org.n01472825.newsapplication.models;

import java.io.Serializable;

public class Source implements Serializable {
    String id;
    String name;

    public String getId() {
        return id;
    }

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
