/*
 *    __  __       _   _                    __  __                 _                  _______      __
 *   |  \/  |     | | | |           ___    |  \/  |               | |                |_   _\ \    / /
 *   | \  / |_   _| |_| |__  ___   ( _ )   | \  / | ___  _ __  ___| |_ ___ _ __ ___    | |  \ \  / / 
 *   | |\/| | | | | __| '_ \/ __|  / _ \/\ | |\/| |/ _ \| '_ \/ __| __/ _ \ '__/ __|   | |   \ \/ /  
 *   | |  | | |_| | |_| | | \__ \ | (_>  < | |  | | (_) | | | \__ \ ||  __/ |  \__ \  _| |_   \  /   
 *   |_|  |_|\__, |\__|_| |_|___/  \___/\/ |_|  |_|\___/|_| |_|___/\__\___|_|  |___/ |_____|   \/    
 *            __/ |                                                                                  
 *           |___/           
 *                                                                        
 *     Copyright (c) 2014-2019 92RED <https://github.com/92RED>
 *  
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *  
 *         http://www.apache.org/licenses/LICENSE-2.0
 *  
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package game.azgarth;

import java.util.HashMap;

// Well, anything related to the player is here.
public class Player {
    // Player.String
    public static String name; // Player name.
    public static String race; // Player race, answers to game.race's data.

    // Player.Boolean
    public static boolean alive; // Is the player alive?
    public static boolean does_object_exist_in_player_inventory; // Does the
                                                                 // object exist
                                                                 // in the
                                                                 // player
                                                                 // inventory?

    // Player.int
    public static HashMap<String, Object> inventory_def = new HashMap<String, Object>();

    // Inventory
    public static class inventory {
        public static boolean checkItem(int temp_item_id) {
            does_object_exist_in_player_inventory = inventory_def
                    .containsValue(1);
            game.ui.Display.debug("Item Check: "
                    + String.valueOf(does_object_exist_in_player_inventory));
            return does_object_exist_in_player_inventory;
        }

        // Use item in inventory.
        public static void useItem(String temp_item_name) {
            int temp_item_id = Integer.parseInt(inventory_def.get(
                    temp_item_name).toString());
            String temp_sender = "player.inventory";
            game.ui.Display.debug("Sending ID: " + temp_item_id
                    + " to script init ...");
            game.scripts.Handler.init(temp_item_id, temp_sender);
        }

        // Add item to inventory.
        public static void addItem(int temp_item_id) {
            String[] temp_item_def = Obj.dynamic_obj.get(temp_item_id);
            inventory_def.put(temp_item_def[0], temp_item_id);
            game.ui.Display.debug("Added: " + "Item ID: " + temp_item_id
                    + " / Item NAME: " + temp_item_def[0]
                    + " to player inventory!");
            game.ui.Display.msg(temp_item_def[0]
                    + " has been added into the bag.");
        }

        // Remove item from inventory.
        public static void removeItem(String temp_item_name) {
            int temp_item_id = Integer.parseInt(inventory_def.get(
                    temp_item_name).toString());
            inventory_def.remove(temp_item_name);
            game.ui.Display.debug("Removed" + "Item ID: " + temp_item_id
                    + " / Item NAME: " + temp_item_name
                    + " from player inventory!");
            game.ui.Display.msg(temp_item_name + " has been thrown.");
        }

    }
}
