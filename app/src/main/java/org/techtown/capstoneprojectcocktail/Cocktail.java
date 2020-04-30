package org.techtown.capstoneprojectcocktail;

public class Cocktail {
    String name;
    int id;
    String description;
    String ingredient;
    String abvNum;

    public Cocktail(String name, int id, String description, String ingredient, String abvNum) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.ingredient = ingredient;
        this.abvNum = abvNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getAbvNum() {
        return abvNum;
    }

    public void setAbvNum(String abvNum) {
        this.abvNum = abvNum;
    }
}
