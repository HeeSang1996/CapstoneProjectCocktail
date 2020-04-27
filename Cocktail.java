package org.techtown.capstoneprojectcocktail;

public class Cocktail {
    String name;
    String description;
    String abvNum;

    public Cocktail(String name, String description, String abvNum) {
        this.name = name;
        this.description = description;
        this.abvNum = abvNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbvNum() {
        return abvNum;
    }

    public void setAbvNum(String abvNum) {
        this.abvNum = abvNum;
    }
}
