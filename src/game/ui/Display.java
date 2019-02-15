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

package game.ui;

import game.azgarth.Game;
import game.azgarth.Map;

// Anything that has to do with displaying something in the game
// actually happens here!
public class Display {
    public static void debug(String args) {
        if (Game.DEBUG_MODE_ENABLE) {
            game.ui.Display.msgnl("DEBUG: " + args);
        }
    }

    public static void error(String args) {
        System.out.println("ERROR: " + args);
    }

    public static void msg(String args) {
        System.out.print(args);
    }

    public static void msg(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i == (args.length - 1)) {
                System.out.print(args[i]);
            }
        }
    }

    public static void msgnl(String args) {
        System.out.println("\n" + args);
    }

    public static void msgnl(String args[]) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i] + " ");
            if (i == (args.length - 1)) {
                System.out.print(args[i]);
            }
        }
        System.out.println();
    }

    public static void emptyLine() {
        System.out.println();
    }

    public static void commandSubmission() {
        System.out.print("\n[" + Map.name + "]: ");
    }
}
