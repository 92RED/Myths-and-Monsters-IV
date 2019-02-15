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

package game.scripts;

// The script Handler is the concept where scripts are called from the map or checked
// these might be useful in the future, for now they will not be of much use.
public class Handler {
    public static boolean torch_lighted = false; // Has the torch been lit?

    // Start scripts based on map.
    public static void start(int temp_map_id) {
        // Special object scripts.
        game.ui.Display.debug("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        game.ui.Display.debug("Initializing scripts ...");

        game.ui.Display.debug("All scripts loaded!");
        game.ui.Display.debug("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }

    // Item scripts
    public static void init(int temp_item_id, String temp_sender) {

    }
}
