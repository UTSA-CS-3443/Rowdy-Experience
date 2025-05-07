package edu.utsa.cs3443.rowdyexperience.model;

public class Badges {
    private String name;
    private int tier;

    public Badges(String name, int tier) {
        this.name = name;
        this.tier = tier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getTier() {
        return this.tier;
    }
}
