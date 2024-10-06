import java.math.BigDecimal;

public class Ingredient {
    private int ingredientId;
    private String name;
    private NutritionalFacts nutrition;
    private BigDecimal quantity;
    private MeasurementUnit measurementUnit;

    // Constructor
    public Ingredient(int ingredientId, String name, BigDecimal quantity, MeasurementUnit measurementUnit) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.quantity = quantity;
        this.measurementUnit = measurementUnit;
    }

    // Getters and Setters
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public NutritionalFacts getNutrition() {
        return nutrition;
    }

    public void setNutrition(NutritionalFacts nutrition) {
        this.nutrition = nutrition;
    }
}