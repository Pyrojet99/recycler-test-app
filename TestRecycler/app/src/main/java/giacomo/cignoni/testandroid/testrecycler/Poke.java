package giacomo.cignoni.testandroid.testrecycler;

public class Poke {
    private String name;
    private String ingredients;
    private int photoId;
    Poke(String name, String ingredients, int photoId) {
        this.name = name;
        this.ingredients = ingredients;
        this.photoId = photoId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }
}
