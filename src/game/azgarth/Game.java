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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

// Game should handle the reading of configuration files,
// and assigning them to variables that can be called for
// various purposes by other aspects of the game.
public class Game {

    private static boolean TEMP_DEBUG;
    private static int TEMP_MAX_OBJECTS;
    private static int TEMP_MAX_CREATURES;
    public static String VERSION;

    static {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("data/azgarth-settings.conf");
            prop.load(input);

            // Read game version
            VERSION = prop.getProperty("game.version").toString();

            // Read debug mode setting
            TEMP_DEBUG = Boolean.valueOf(prop.getProperty("game.debug_mode")
                    .toString());
            
            // Read maximum creatures
            TEMP_MAX_OBJECTS = Integer.valueOf(prop.getProperty("game.max_objects").toString());
            TEMP_MAX_CREATURES = Integer.valueOf(prop.getProperty("game.max_creatures").toString());

            input.close();
        } catch (Exception e) {
            game.ui.Display.error("error loading configuration!");
        }
    }

    public static boolean DEBUG_MODE_ENABLE = TEMP_DEBUG;
    public static int MaxGameObjects = TEMP_MAX_OBJECTS;
    public static int MaxGameCreatures = TEMP_MAX_CREATURES;
    public static boolean OVER = false;
}
