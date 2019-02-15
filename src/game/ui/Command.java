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
import game.azgarth.Obj;
import game.azgarth.Player;
import game.azgarth.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

// Command handles everything related to commands! From declaring,
// them to executing them to even checking up on user input!

public class Command {
    // valid_commands
    // The idea here is that when the player inputs a synonym we can look up the
    // 'proper' command name and then call the proper command.
    public static HashMap<String, String> valid_commands = new HashMap<String, String>();

    // directions
    // Here's the list of all the possible the player can go in the game. The
    // idea here is to match the given direction with the proper direction index
    // that should then check whether the said direction can be taken in the
    // current Map.

    public static final HashMap<String, Integer> directions = new HashMap<String, Integer>();
    static {
        // Basic directions
        directions.put("north", 0);
        directions.put("n", 0);
        directions.put("south", 1);
        directions.put("s", 1);
        directions.put("west", 2);
        directions.put("w", 2);
        directions.put("east", 3);
        directions.put("e", 3);
        // Advanced directions
        directions.put("northeast", 4);
        directions.put("ne", 4);
        directions.put("southeast", 5);
        directions.put("se", 5);
        directions.put("northwest", 6);
        directions.put("nw", 6);
        directions.put("southwest", 7);
        directions.put("sw", 7);
        // Miscellaneous directions
        directions.put("up", 8);
        directions.put("u", 8);
        directions.put("down", 9);
        directions.put("d", 9);
        directions.put("in", 10);
        directions.put("on", 10);
        directions.put("enter", 10);
        directions.put("out", 11);
        directions.put("off", 11);
        directions.put("exit", 11);
    }

    // End command
    public static void end() {
        // This command simply ends the game!
        Game.OVER = true;
    }

    // End of end command.

    // Move command
    // The move command basically checks if the current navigation index that
    // the player
    // is requesting to go to is a valid one in the context. When this is
    // confirmed as valid
    // then the correct map ID and phase are loaded from the database.
    public static void move(String[] args) {
        boolean temp_map_found = false; // Turns true when found.
        if (directions.containsKey(args[1])) {
            int temp_direction = Integer.parseInt(directions.get(args[1])
                    .toString());
            for (int i = 0; i < World.m_navi[Map.current][Map.current_phase].length; i++) {
                if ((World.m_navi[Map.current][Map.current_phase][temp_direction] != -1)
                        && (World.m_navi[Map.current][Map.current_phase][i] != -1)) {
                    Map.build(World.m_navi[Map.current][Map.current_phase][temp_direction]);
                    Map.mapChange(World.m_navi[Map.current][Map.current_phase][temp_direction]);
                    temp_map_found = true;
                    break;
                }
            }
            if (!temp_map_found) {
                game.ui.Display.msg("I can't go that way!");
            }
        }
    }

    // Inspect command
    public static void inspect(String[] args) {
        boolean temp_item_found = false;

        // Is the given object a static one?
        if (args.length > 1) {
            for (int i = 0; i < Obj.static_obj.size(); i++) {
                String[] temp_object_def = Obj.static_obj.get(i);
                if (temp_object_def[0].equalsIgnoreCase(args[1])) {
                    if (Player.inventory_def.containsValue(i)
                            || (World.m_static[Map.current][i] == i)) {
                        game.ui.Display.msg(temp_object_def[2]);
                        temp_item_found = true;
                        break;
                    }
                }
            }

            // Is the given object a dynamic one, and is it in the player's
            // inventory?
            for (int i = 0; i < Obj.dynamic_obj.size(); i++) {
                String[] temp_object_def = Obj.dynamic_obj.get(i);
                if (temp_object_def[0].equalsIgnoreCase(args[1])) {
                    game.ui.Display.debug(temp_object_def[i]);
                    if (Player.inventory_def.containsValue(i)
                            || World.checkObj(Map.current, i)) {
                        if (Player.inventory_def.containsValue(i)) {
                            game.ui.Display.msg("I have found the " + args[1]
                                    + " in my bag. \n" + temp_object_def[2]);
                        } else {
                            game.ui.Display.msg(temp_object_def[2]);
                        }
                        temp_item_found = true;
                        break;
                    }
                }
            }

            // Has the item been found?
            if (!temp_item_found) {
                game.ui.Display.msg("\nI can't find any ");
                for (int i = 1; i < args.length; i++) {
                    game.ui.Display.msg(args[i] + " ");
                }
                game.ui.Display.emptyLine();
            }
        } else {
            Map.build(Map.current);
        }

    }

    // Take, put in inventory
    @SuppressWarnings("unused")
    public static void take(String[] args) {
        boolean temp_item_found = false; // Turns true when found.

        // If it's "take all" then take all the takeable objects in the map.
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("all")) {
                for (int i = 0; i < World.m_dynamic[Map.current].length; i++) {
                    game.ui.Display.debug("Attempt to 'take all' #" + i);
                    for (int j = 0; World.m_dynamic[Map.current][j] != -1; j++) {
                        game.ui.Display.debug("TAKING ALL OBJECTS...");
                        game.ui.Display
                                .debug("Value of j: "
                                        + String.valueOf(World.m_dynamic[Map.current][j]));
                        String[] temp_object_def = Obj.dynamic_obj
                                .get(World.m_dynamic[Map.current][j]);
                        game.ui.Display.debug("sending object: "
                                + temp_object_def[0] + " ...");
                        Player.inventory
                                .addItem(World.m_dynamic[Map.current][j]);
                        World.removeObj(Map.current,
                                World.m_dynamic[Map.current][j]);
                        game.ui.Display.debug("FINISHED TAKING ALL OBJECTS!");
                    }
                    break;
                }
            } else {
                // Is the object static?
                game.ui.Display.debug("Testing if the object is a static...");
                for (int i = 0; i < Obj.static_obj.size(); i++) {
                    String[] temp_object_def = Obj.static_obj.get(i);
                    if (temp_object_def[0].equalsIgnoreCase(args[1])) {
                        game.ui.Display.msgnl("I can't possibly add a "
                                + temp_object_def[0] + " in my inventory!");
                        temp_item_found = true;
                        break;
                    }
                }
                // Is the object dynamic?
                game.ui.Display.debug("Testing if the object is a dynamic...");
                for (int i = 0; i < Obj.dynamic_obj.size(); i++) {
                    String[] temp_object_def = Obj.dynamic_obj.get(i);
                    World.does_object_exist_in_map = World.checkObj(
                            Map.current, i);
                    if (temp_object_def[0].equalsIgnoreCase(args[1])
                            && World.does_object_exist_in_map) {
                        game.ui.Display.debug("sending object: "
                                + temp_object_def[0] + " ...");
                        Player.inventory.addItem(i);
                        World.removeObj(Map.current, i);
                        temp_item_found = true;
                        break;
                    }
                }

                // Has it been found?
                if (!temp_item_found) {
                    game.ui.Display.debug("The item wasn't found...");
                    game.ui.Display.emptyLine();
                    game.ui.Display.msg("I can't find any ");
                    for (int i = 1; i < args.length; i++) {
                        game.ui.Display.msg(args[i] + " ");
                    }
                    game.ui.Display.msg("around.");
                    game.ui.Display.emptyLine();
                }
            }
        } else {
            game.ui.Display.msgnl("I am not sure what to take...");
        }
    }

    // Drop from inventory
    public static void drop(String[] args) {
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("all")) {
                String[] temp_inventory = Arrays.copyOf(Player.inventory_def
                        .keySet().toArray(), Player.inventory_def.keySet()
                        .toArray().length, String[].class);
                if (temp_inventory.length > 0) {
                    game.ui.Display.debug("Throwing items from inventory!!!!");
                    for (String temp_item_name : temp_inventory) {
                        int temp_item_id = Integer
                                .parseInt(Player.inventory_def.get(
                                        temp_item_name).toString());
                        Player.inventory.removeItem(temp_item_name);
                        World.addObj(Map.current, temp_item_id);
                    }
                    game.ui.Display.debug("Finished throwing items!!!");
                }
            } else if (!args[1].equalsIgnoreCase("all")) {
                String temp_item_name = args[1];
                if (Player.inventory_def.containsKey(temp_item_name)) {
                    int temp_item_id = Integer.parseInt(Player.inventory_def
                            .get(temp_item_name).toString());
                    Player.inventory.removeItem(args[1]);
                    World.addObj(Map.current, temp_item_id);
                } else {
                    game.ui.Display.msgnl("I don't have a " + temp_item_name
                            + " to throw!");
                }
            } else {
                game.ui.Display.msgnl("My bag is already empty.");
            }
        } else {
            game.ui.Display
                    .msgnl("I am not sure what to drop from my inventory...");
        }
    }

    // Use an item in the inventory.
    public static void use(String[] args) {
        if (Player.inventory_def.size() > 0) {
            game.ui.Display.debug("Using the " + args[1]);
            Player.inventory.useItem(args[1]);
        }
    }

    // List inventory contents.
    public static void listInventory() {
        if (Player.inventory_def.size() != 0) {
            game.ui.Display.msg("I have in my inventory: "
                    + Player.inventory_def.keySet().toString());
        } else {
            game.ui.Display.msg("I am not carrying anything in my bag...");
        }
    }

    /* Debug commands */
    // Wrap command, usable only when debug mode is enabled.
    public static void wrap(String[] args) {
        if (Game.DEBUG_MODE_ENABLE) {
            int id = Integer.parseInt(args[1]);
            Map.mapChange(id);
            Map.build(id);
        } else {
            game.ui.Display.msg("I am not sure how to " + args[0]);
        }
    }

    private static final BufferedReader stdin = new BufferedReader(
            new InputStreamReader(System.in));
    public static String temp_user_input;
    public static String temp_user_interpretation;
    public static String[] user_input;

    /* Slice up user input */
    public static void slice(String args) {
        user_input = args.split(" "); // Splits the text by the spaces between
                                      // words.
        game.ui.Display.debug("user_input[0] = " + user_input[0]);
    }

    /* Call the proper command based on input */
    public static void caller(String[] args) {
        // One-word commands ignore anything but the first word.
        if (args[0].matches("end")) {
            game.ui.Display.debug("executing \"end\" command!");
            Command.end();
        }
        if (args[0].matches("go")) {
            game.ui.Display.debug("executing \"go\" command!");
            Command.move(args);
        }
        if (args[0].matches("take")) {
            game.ui.Display.debug("executing \"take\" command!");
            Command.take(args);
        }
        if (args[0].matches("inventory")) {
            game.ui.Display.debug("executing \"inventory\" command!");
            Command.listInventory();
        }
        if (args[0].matches("drop")) {
            Command.drop(args);
        }
        if (args[0].matches("inspect")) {
            Command.inspect(args);
        }
        if (args[0].matches("use")) {
            Command.use(args);
        }
        // Debug commands
        if (args[0].matches("enable_debug")) {
            Game.DEBUG_MODE_ENABLE = true;
            game.ui.Display.debug("Debug mode enabled!");
        }
        if (args[0].matches("disable_debug")) {
            game.ui.Display.debug("Debug mode disabled!");
            Game.DEBUG_MODE_ENABLE = false;
        }
        if (Game.DEBUG_MODE_ENABLE) {
            if (args[0].matches("wrap")) {
                game.ui.Display.debug("executing \"wrap\" command!");
                Command.wrap(args);
            }
            if (args[0].matches("azgrath.show.all")) {
                for (int i = 0; (i < World.m_dynamic[Map.current].length)
                        && (World.m_dynamic[Map.current][i] != -1); i++) {
                    game.ui.Display
                            .debug("I have found: ID: "
                                    + World.m_dynamic[Map.current][i]
                                    + " NAME: "
                                    + Obj.dynamic_text[World.m_dynamic[Map.current][i]][0]);
                }
            }
        }
    }

    // Interpret user input
    public static void interpreter(String[] args) {
        boolean input_interpreted = false;
        // Does the command belong in the list?
        if (!input_interpreted) {
            if (Command.valid_commands.containsKey(args[0])) {
                input_interpreted = true;
                temp_user_interpretation = Command.valid_commands.get(args[0])
                        .toString();
                game.ui.Display.debug("Command called: "
                        + temp_user_interpretation);
                user_input[0] = temp_user_interpretation;
                Command.caller(user_input);
            } else {
                game.ui.Display.msg("I am not sure how to " + args[0]);
            }
        }
    }

    // Parses the user input into a String[], used by ui.Command.input().
    public static String[] parseInput(String args) {
        return args.split(" ");
    }

    // Handles the player input. It should send it to the interpreter as a
    // String[].
    public static void input() throws IOException {
        game.ui.Display.emptyLine();
        if (!Game.OVER) {
            game.ui.Display.commandSubmission();
            temp_user_input = stdin.readLine();
            game.ui.Display.debug("temp_user_input = " + temp_user_input);
            game.ui.Command.slice(temp_user_input);
            game.ui.Command.interpreter(user_input);
        } else {
            game.ui.Display.msgnl("Azgrath > ");
            temp_user_input = stdin.readLine();
            game.ui.Display.debug("temp_user_input = " + temp_user_input);
            game.ui.Command.slice(temp_user_input);
        }
    }
}
