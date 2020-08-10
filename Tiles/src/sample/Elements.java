package sample;

public class Elements {

    private String color;
    private String shape;

    public Elements(String color, String shape){
        this.color = color;
        this.shape = shape;
    }

    /**
     * return color of element
     * @return
     */
    public String getColor(){
        return this.color;
    }

    /**
     * return shape of element
     * @return
     */
    public String getShape(){
        return this.shape;
    }

}
