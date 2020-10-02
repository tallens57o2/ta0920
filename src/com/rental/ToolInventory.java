package com.rental;

import com.rental.objects.Tool;
import com.rental.objects.ToolType;
import com.rental.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple container to associate different tool codes to the tools they represent
 */
public class ToolInventory {
    private final Map<String, Tool> toolMap = new HashMap<>();

    /**
     * Default constructor
     */
    public ToolInventory() {
        // nothing to do
    }

    /**
     * Generates new ToolInventory containing tools defined in the spec
     *
     * @return a ToolInventory with defined tools
     */
    public static ToolInventory defaultInventory() {
        ToolInventory inventory = new ToolInventory();
        inventory.addTool(Constants.LADW, new Tool(ToolType.LADDER, Constants.WERNER));
        inventory.addTool(Constants.CHNS, new Tool(ToolType.CHAINSAW, Constants.SITHL));
        inventory.addTool(Constants.JAKR, new Tool(ToolType.JACKHAMMER, Constants.RIDGID));
        inventory.addTool(Constants.JAKD, new Tool(ToolType.JACKHAMMER, Constants.DEWALT));
        return inventory;
    }

    /**
     * Checks if a tool associated wthh a Tool code exists
     *
     * @param toolCode code to look-up
     * @return associated tool, or null if it does not exist
     */
    public Tool getTool(String toolCode) {
        return toolMap.get(toolCode);
    }

    /**
     * Adds a new tool code and associated tool to the inventory
     *
     * @param toolCode tool code to add
     * @param tool tool object to add
     */
    public void addTool(String toolCode, Tool tool) {
        if (getTool(toolCode) != null) {
            System.out.println(Constants.REPEAT_TOOL_CODE);
            throw new RuntimeException(Constants.REPEAT_TOOL_CODE);
        }
        toolMap.put(toolCode, tool);
    }

    /**
     * Removes a tool from the inventory
     *
     * @param toolCode tool code to remove
     * @return the removed tool, or null if there wasn't one with the associated code
     */
    public Tool removeTool(String toolCode) {
        return toolMap.remove(toolCode);
    }
}
