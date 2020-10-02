package com.rental.objects;

/**
 * Class consisting of a type of tool and the branch of a specific tool
 * (Tool code is associated in the ToolInventory and not here to allow
 * constant time look-ups and avoid duplicating data)
 */
public class Tool {
    private final ToolType type; // Enum value of the type of tool being represented
    private final String brand; // Brand of the tool

    public Tool(ToolType type, String brand) {
        this.type = type;
        this.brand = brand;
    }

    public ToolType getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }
}
