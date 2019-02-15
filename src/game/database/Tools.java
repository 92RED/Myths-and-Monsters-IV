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
 *     
 */

package game.database;

import game.azgarth.World;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// This code is all part of the Database tools, where the data from the database
// is taken and sent into the proper constructors.

public class Tools {
	public static final String world_db_location = "data//db//world.xml";
	public static final String object_db_location = "data//db/objects.xml";
	public static final String item_db_location = "data//db//items.xml";
	public static final String creature_db_location ="data//db//creatures.xml";
	public static final String command_db_location = "data//db//commands.xml";
	
    public static boolean dbFile() {
        String db;
        String select;
        try {
            // Load the world
            File db_file = new File(world_db_location);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document current_db = dBuilder.parse(db_file);
            current_db.getDocumentElement().normalize();
            db = "world";
            select = "map";
            game.ui.Display
                    .debug("db accessing "
                            + current_db.getDocumentElement().getNodeName()
                            + " now...");

            NodeList nl = current_db.getElementsByTagName(select);

            for (int id = 0; id < nl.getLength(); id++) {
                Node node = nl.item(id);
                game.ui.Display.debug("Generating map with ID " + id);
                game.ui.Display.debug("found: " + select);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element temp = (Element) node;

                    // World builder
                    if (db.equalsIgnoreCase("world")
                            && select.equalsIgnoreCase("map")) {
                        int temp_phase_id = Integer.parseInt(temp
                                .getAttribute("phase"));
                        String temp_map_name = String.valueOf(temp
                                .getElementsByTagName("name").item(0)
                                .getTextContent().toString());
                        String temp_map_text = String.valueOf(temp
                                .getElementsByTagName("text").item(0)
                                .getTextContent().toString());

                        // Clean temporary static and dynamic arrays
                        for (int i = 0; i < World.size; i++) {
                            for (int j = 0; j < World.max_objects; j++) {
                                World.m_static[id][j] = -1;
                                World.m_dynamic[id][j] = -1;
                            }
                            
                            for (int k = 0; k < World.max_creatures; k++) {
                                World.m_creature[id][k] = -1;
                            }
                        }

                        // Parse Static Object IDs in the map
                        if (!temp.getElementsByTagName("static-objects")
                                .item(0).getTextContent().isEmpty()) {
                            if (temp.getElementsByTagName("static-objects")
                                    .item(0).getTextContent().contains(",")) {
                                String[] temporary = temp
                                        .getElementsByTagName("static-objects")
                                        .item(0).getTextContent().toString()
                                        .split(",");
                                for (int i = 0; i < World.m_static[id].length; i++) {
                                    for (int j = 0; j < temporary.length; j++) {
                                        World.m_static[id][j] = Integer
                                                .parseInt(temporary[j]);
                                    }
                                }
                            } else {
                                World.m_static[id][0] = Integer.parseInt(temp
                                        .getElementsByTagName("static-objects")
                                        .item(0).getTextContent().toString());
                            }
                        }

                        // Parse Dynamic Object IDs in the map
                        if (!temp.getElementsByTagName("dynamic-objects")
                                .item(0).getTextContent().isEmpty()) {
                            if (temp.getElementsByTagName("dynamic-objects")
                                    .item(0).getTextContent().contains(",")) {
                                String[] temporary = temp
                                        .getElementsByTagName("dynamic-objects")
                                        .item(0).getTextContent().toString()
                                        .split(",");
                                for (int i = 0; i < World.m_dynamic[id].length; i++) {
                                    for (int j = 0; j < temporary.length; j++) {
                                        World.m_dynamic[id][j] = Integer
                                                .parseInt(temporary[j]);
                                    }
                                }
                            } else {
                                World.m_dynamic[id][0] = Integer
                                        .parseInt(temp
                                                .getElementsByTagName(
                                                        "dynamic-objects")
                                                .item(0).getTextContent()
                                                .toString());
                            }
                        }
                        
                        // Parse creatures in the map
                        if (!temp.getElementsByTagName("creatures")
                                .item(0).getTextContent().isEmpty()) {
                            if (temp.getElementsByTagName("creatures").item(0).getTextContent().contains(",")) {
                                String[] temporary = temp.getElementsByTagName("creatures")
                                        .item(0).getTextContent().toString().split(",");
                                for (int i = 0; i < World.m_creature[id].length; i++) {
                                    for (int j = 0; j < temporary.length; j++) {
                                        World.m_creature[id][j] = Integer.parseInt(temporary[j]);
                                    }
                                }
                            } else {
                                World.m_creature[id][0] = Integer.parseInt(temp.getElementsByTagName("creatures")
                                        .item(0).getTextContent().toString());
                            }
                        }

                        // Parse Navigation Definitions in the map
                        if (!temp.getElementsByTagName("navigation").item(0)
                                .getTextContent().isEmpty()) {
                            String[] temporary = temp
                                    .getElementsByTagName("navigation").item(0)
                                    .getTextContent().toString().split(",");
                            for (int i = 0; i < World.m_navi[id][temp_phase_id].length; i++) {
                                for (int j = 0; j < temporary.length; j++) {
                                    World.m_navi[id][temp_phase_id][j] = Integer
                                            .parseInt(temporary[j]);
                                }
                            }
                        }

                        // Load the database to the game
                        World.m_name[id] = temp_map_name;
                        World.m_struct[id][temp_phase_id] = temp_map_text;

                        // Add m_struct to the silent cartorgrapher
                        World.cartographer.put(id, World.m_struct[id]);
                        game.ui.Display
                                .debug("\tLoaded mapID: " + id + " mapNAME: "
                                        + World.m_name[id] + " to game.");
                    }
                }
            }

            // Object builder
            File db_file_obj = new File(object_db_location);
            DocumentBuilderFactory dbFactoryObj = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilderObj = dbFactoryObj.newDocumentBuilder();
            Document current_dbObj = dBuilderObj.parse(db_file_obj);
            current_dbObj.getDocumentElement().normalize();
            select = "object";
            db = "objects";

            game.ui.Display.debug("db accessing "
                    + current_dbObj.getDocumentElement().getNodeName()
                    + " now...");

            NodeList nlObj = current_dbObj.getElementsByTagName("object");

            for (int id = 0; id < nlObj.getLength(); id++) {
                Node nodeObj = nlObj.item(id);
                if (nodeObj.getNodeType() == Node.ELEMENT_NODE) {
                    Element tempObj = (Element) nodeObj;
                    game.ui.Display.debug("\t\tAdding static object...");
                    // Parse static object
                    if (db.equalsIgnoreCase("objects")
                            && select.equalsIgnoreCase("object")) {
                        game.azgarth.Obj.static_text[id][0] = tempObj
                                .getElementsByTagName("name").item(0)
                                .getTextContent().toString();
                        game.azgarth.Obj.static_text[id][1] = tempObj
                                .getElementsByTagName("text").item(0)
                                .getTextContent().toString();
                        game.azgarth.Obj.static_text[id][2] = tempObj
                                .getElementsByTagName("inspect").item(0)
                                .getTextContent().toString();
                        game.azgarth.Obj.static_obj.put(id,
                                game.azgarth.Obj.static_text[Integer
                                        .valueOf(tempObj.getAttribute("id"))]);
                    }
                }
            }

            // Item builder
            File db_file_item = new File(item_db_location);
            DocumentBuilderFactory dbFactoryItem = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilderItem = dbFactoryItem.newDocumentBuilder();
            Document current_dbItem = dBuilderItem.parse(db_file_item);
            current_dbItem.getDocumentElement().normalize();
            select = "item";
            db = "items";

            game.ui.Display.debug("db accessing "
                    + current_dbItem.getDocumentElement().getNodeName()
                    + " now...");

            NodeList nlItem = current_dbItem.getElementsByTagName("item");

            for (int id = 0; id < nlItem.getLength(); id++) {
                Node nodeItem = nlItem.item(id);
                if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {
                    Element tempItem = (Element) nodeItem;
                    game.ui.Display.debug("\t\tAdding dynamic object...");
                    // Parse dynamic object -- also called item.
                    if (db.equalsIgnoreCase("items")
                            && select.equalsIgnoreCase("item")) {
                        game.azgarth.Obj.dynamic_text[id][0] = tempItem
                                .getElementsByTagName("name").item(0)
                                .getTextContent().toString();
                        game.azgarth.Obj.dynamic_text[id][1] = tempItem
                                .getElementsByTagName("text").item(0)
                                .getTextContent().toString();
                        game.azgarth.Obj.dynamic_text[id][2] = tempItem
                                .getElementsByTagName("inspect").item(0)
                                .getTextContent().toString();
                        game.azgarth.Obj.dynamic_obj.put(id,
                                game.azgarth.Obj.dynamic_text[Integer
                                        .valueOf(tempItem.getAttribute("id"))]);
                    }
                }
            }
            
            // Creature builder
            File db_file_creatures = new File(creature_db_location);
            DocumentBuilderFactory dbFactoryCreatures = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilderCreatures = dbFactoryCreatures
                    .newDocumentBuilder();
            Document current_dbCreature = dBuilderCreatures
                    .parse(db_file_creatures);
            
            current_dbCreature.getDocumentElement().normalize();
            
            select = "creature";
            db = "creatures";
            
            game.ui.Display.debug("db accessing "
                    + current_dbCreature.getDocumentElement().getNodeName()
                    + "now... ");
            
            NodeList nlCreature = current_dbCreature.getElementsByTagName(select);
            
            for (int id=0; id < nlCreature.getLength(); id++) {
                Node nodeCreature = nlCreature.item(id);
                if (nodeCreature.getNodeType() == Node.ELEMENT_NODE) {
                    Element tempCreature = (Element) nodeCreature;
                    game.ui.Display.debug("\t\tAdding creature...");
                    
                    // Parse creature
                    String temp_name = tempCreature.getElementsByTagName("name").item(0)
                            .getTextContent().toString();
                    int temp_id = Integer.valueOf(tempCreature.getAttribute("id"));
                    String[] temp_text = new String[10];
                    
                    for (int i = 0; i < temp_text.length; i++) {
                        if (tempCreature.getElementsByTagName("text")
                                .item(i) != null) {
                            temp_text[i] = tempCreature.getElementsByTagName("text").item(i)
                                    .getTextContent().toString();
                        }
                    }
                    
                    // Assign random description of creature
                    
                    int finalCreatureText = 0;
                    while (true) {
                        int temp_rand_number = (int) (Math.random() * 10);
                        if (temp_text[temp_rand_number] != null) {
                            finalCreatureText = temp_rand_number;
                            break;
                        }
                    }
                    
                    game.azgarth.Creature.add(id, temp_name, temp_text[finalCreatureText], "monster");
                    
                    game.azgarth.Creature.creature_def.put(id, 
                            game.azgarth.Creature.creature_text[temp_id]);
                    
                    
                }
            }
            
            
            // Command builder
            File db_file_commands = new File(command_db_location);
            DocumentBuilderFactory dbFactoryCommands = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilderCommands = dbFactoryCommands
                    .newDocumentBuilder();
            Document current_dbCommand = dBuilderCommands
                    .parse(db_file_commands);

            current_dbCommand.getDocumentElement().normalize();

            select = "command";
            db = "commands";

            game.ui.Display.debug("db accessing "
                    + current_dbCommand.getDocumentElement().getNodeName()
                    + " now...");

            NodeList nlCommand = current_dbCommand.getElementsByTagName(select);

            for (int id = 0; id < nlCommand.getLength(); id++) {
                Node nodeCommand = nlCommand.item(id);
                if (nodeCommand.getNodeType() == Node.ELEMENT_NODE) {
                    Element tempCommand = (Element) nodeCommand;
                    game.ui.Display.debug("\t\tAdding command...");
                    // Parse command
                    if (db.equalsIgnoreCase("commands")
                            && select.equalsIgnoreCase("command")) {
                        String temp_index = tempCommand
                                .getElementsByTagName("index").item(0)
                                .getTextContent().toString();

                        for (int i = 0; i < 10; i++) {
                            if (tempCommand.getElementsByTagName("name")
                                    .item(i) != null) {
                                game.ui.Command.valid_commands.put(tempCommand
                                        .getElementsByTagName("name").item(i)
                                        .getTextContent().toString(),
                                        temp_index);
                            }
                        }
                    }
                }
            }

            // Return true when done successfully
            return true;
        } catch (Exception e) {
            game.ui.Display.error("accessing to database: " + e);
        }

        return false;
    }
}
