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

// World basically handles the big world-related stuff. For example, removing a specific
// object from a map that isn't specific, and could be anywhere in the world, the same
// goes for adding and other things that can happen *anywhere* or in the whole world.
public class World {
    public static final int size = 4; // World size, aka: amount of maps.
    public static final int phases = 5; // Maximum phases per World.
    public static final int max_objects = Game.MaxGameObjects; // Max objects per map. (200)
    public static final int max_creatures = Game.MaxGameCreatures; // Max creatures per map. (20)
    public static boolean does_object_exist_in_map = false; // Does object exist
                                                            // in map?

    public static String[][] m_struct = new String[World.size][World.phases];
    public static String[] m_name = new String[World.size];
    public static int[][] m_static = new int[World.size][World.max_objects];
    public static int[][] m_dynamic = new int[World.size][World.max_objects];
    public static int[][] m_creature = new int[World.size][World.max_creatures];
    public static int[][][] m_navi = new int[World.size][World.phases][12];

    public static HashMap<Object, String[]> cartographer = new HashMap<Object, String[]>();

    // Remove *dynamic* object from specific map in the World.
    public static void removeObj(int temp_map_id, int temp_object_id) {
        String[] temp_object_name;

        for (int i = 0; i < World.size; i++) {
            for (int j = 0; j < m_dynamic[temp_map_id].length; j++) {
                if (World.m_dynamic[temp_map_id][j] == temp_object_id) {
                    temp_object_name = Obj.dynamic_obj.get(temp_object_id);
                    game.ui.Display.debug("Removing Object ID: "
                            + temp_object_id + "  Object NAME: "
                            + temp_object_name[0] + " from map ID: "
                            + Map.current + " ...");
                    World.m_dynamic[temp_map_id][j] = -1;
                    game.ui.Display.debug("Object removed successfully!");
                    break;
                }
            }
        }
    }

    // Add *dynamic* object from inventory to map in the World.
    public static void addObj(int temp_map_id, int temp_object_id) {
        String[] temp_object_def = Obj.dynamic_obj.get(temp_object_id);
        if (m_dynamic[temp_map_id].length != 0) {
            for (int i = 0; i < m_dynamic[temp_map_id].length; i++) {
                if (m_dynamic[temp_map_id][i] == -1) {
                    m_dynamic[temp_map_id][i] = temp_object_id;
                    game.ui.Display.debug("Object ID: " + temp_object_id
                            + " NAME: " + temp_object_def[0]
                            + " has been added to map ID: " + Map.current
                            + " successfully!");
                    break;
                }
            }
        }
    }

    // Detect if a *dynamic* object exists in the map.
    public static boolean checkObj(int temp_map_id, int temp_object_id) {
        does_object_exist_in_map = false;
        if (m_dynamic[temp_map_id].length != 0) {
            for (int i = 0; i < m_dynamic[temp_map_id].length; i++) {
                if (m_dynamic[temp_map_id][i] == temp_object_id) {
                    does_object_exist_in_map = true;
                }
            }
        }
        return does_object_exist_in_map;
    }
}
