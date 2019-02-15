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
 *     Copyright (c) 2014 Lucky Red <https://github.com/LuckyRed>
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

// Map actually is where the maps are built from the assets loaded from World,
// basically all the functions here are made to prepare the map to be shown to
// the player.
public class Map {
    public static String name;
    public static int current = 0; // Current map
    public static int current_phase = 0; // Current phase

    private static int[] temp_map_static_objects;
    private static int[] temp_map_dynamic_objects;
    private static int[] temp_map_creatures;
    private static String[] temp_map;

    private static boolean map_validation = false; // Turns true when the map
                                                   // has been validated.

    // Display the Map.
    public static void deliverMap(int[] temp_map_static_objects,
            int[] temp_map_dynamic_objects, int[] temp_map_creatures) {
        game.ui.Display.msgnl(temp_map[0]);
        
        // Add static objects
        for (int i = 0; i < temp_map_static_objects.length; i++) {
            if (!(temp_map_static_objects[i] == -1)) {
                String temp_select_static_object = Obj.static_text[temp_map_static_objects[i]][1];
                if (!temp_select_static_object.equals(null)) {
                    game.ui.Display.msg(temp_select_static_object);
                }
            }
        }
        
        // Add items
        for (int i = 0; i < temp_map_dynamic_objects.length; i++) {
            if (!(temp_map_dynamic_objects[i] == -1)) {
                String temp_select_dynamic_object = Obj.dynamic_text[temp_map_dynamic_objects[i]][1];
                if (!temp_select_dynamic_object.equals(null)) {
                    game.ui.Display.msg(temp_select_dynamic_object);
                    game.ui.Display.msg(". ");
                }
            }
        }
        
        // Add creatures
        for (int i = 0; i < temp_map_creatures.length; i++) {
            if (!(temp_map_creatures[i] == -1)) {
                String temp_select_creature = Creature.creature_text[temp_map_creatures[i]][1];
                if (!temp_select_creature.equals(null)) {
                    game.ui.Display.msg(temp_select_creature);
                }
            }
        }
        game.ui.Display.emptyLine();
    }
    
    // Fill maps with objects and creatures.
    public static void addObjects(int temp_map_id) {
        if (map_validation) {
            temp_map_static_objects = World.m_static[temp_map_id];
            temp_map_dynamic_objects = World.m_dynamic[temp_map_id];
            temp_map_creatures = World.m_creature[temp_map_id];
            Map.name = World.m_name[temp_map_id];
            Map.deliverMap(temp_map_static_objects, temp_map_dynamic_objects, temp_map_creatures);
        }
    }

    // Build the maps.
    public static void build(int temp_map_id) {
        game.scripts.Handler.start(temp_map_id);
        if (World.cartographer.containsKey(temp_map_id)) {
            game.ui.Display.debug("Generating Map...");
            temp_map = World.cartographer.get(temp_map_id);
            map_validation = true;
            Map.addObjects(temp_map_id);
        }
    }

    // This actually changes the current map ID.
    public static void mapChange(int temp_map_id) {
        Map.current = temp_map_id;
    }
}
