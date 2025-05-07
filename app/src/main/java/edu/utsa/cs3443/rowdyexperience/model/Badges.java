package edu.utsa.cs3443.rowdyexperience.model;

/**
 * Represents a badge in the Rowdy Experience application.
 * Each badge has a name and a tier indicating its level.
 *
 */

public class Badges {
    private String name;
    private int tier;

    /**
     * Constructs a new Badge with the specified name and tier.
     *
     * @param name the name of the badge
     * @param tier the tier level of the badge
     */
    public Badges(String name, int tier) {
        this.name = name;
        this.tier = tier;
    }

    /**
     * Sets the name of the badge.
     *
     * @param name the new name of the badge
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the badge.
     *
     * @return the name of the badge
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the tier level of the badge.
     *
     * @param tier the new tier level of the badge
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * Returns the tier level of the badge.
     *
     * @return the tier level of the badge
     */
    public int getTier() {
        return this.tier;
    }
}
