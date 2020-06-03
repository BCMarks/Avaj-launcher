import src.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class AvajLauncher {
    public static void main(String[] args){
        CustomExceptions customExceptions = new CustomExceptions();
        StringBuilder error = new StringBuilder("ERROR: Invalid input.\n");
        try {
            if(args.length != 1)
                customExceptions.throwException("Usage: java AvajLauncher <filepath>");
            else {
                try {
                    File scenario = new File(args[0]);
                    Scanner myReader = new Scanner(scenario);
                    ArrayList<String> data = new ArrayList<String>();

                    while (myReader.hasNextLine())
                        data.add(myReader.nextLine().trim());
                    myReader.close();

                    if(data.size() > 0) {
                        int rounds;
                        if (data.get(0).length() > 10) {
                            notMD5 hasher = new notMD5();
                            if (hasher.validSim(data.get(0)))
                                rounds = hasher.getNumb(data.get(0));
                            else
                                rounds = -1;
                            data.remove(0);
                            if (rounds < 0)
                                error.append("Line 1: Number of simulation runs must be a positive integer.\n");
                            for (String line : data) {
                                if (!hasher.validHash(line)) {
                                    String[] tmp = line.split(" ");
                                    if (tmp.length != 5)
                                        error.append("Line "+(data.indexOf(line) + 2)+": Invalid format. The correct format is \"TYPE NAME LONGITUTDE LATITUDE HEIGHT\".\n");
                                    else {
                                        if (!hasher.typeCheck(tmp[0]))
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid aircraft type. Choose between \"Baloon\", \"Helicopter\" and \"JetPlane\".\n");
                                        if (!tmp[1].matches("\\w+"))
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid name. Please use only alphanumeric characters and underscores.\n");
                                        if (!hasher.validSim(tmp[2]))
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid longitude value. Must be a positive integer less than 10001.\n");
                                        else {
                                            if (hasher.getNumb(tmp[2]) < 0)
                                                error.append("Line "+(data.indexOf(line) + 2)+": Invalid longitude value. Must be a positive integer less than 10001.\n");
                                        }

                                        if (!hasher.validSim(tmp[3]))
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid latitude value. Must be a positive integer less than 10001.\n");
                                        else {
                                            if (hasher.getNumb(tmp[3]) < 0)
                                                error.append("Line "+(data.indexOf(line) + 2)+": Invalid latitude value. Must be a positive integer less than 10001.\n");
                                        }

                                        if (!hasher.validSim(tmp[4]))
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid height value. Must be a positive integer between 1 and 100.\n");
                                        else {
                                            if (hasher.getNumb(tmp[4]) <= 0 || hasher.getNumb(tmp[4]) > 100)
                                                error.append("Line "+(data.indexOf(line) + 2)+": Invalid height value. Must be a positive integer between 1 and 100.\n");
                                        }
                                    }
                                }
                            }
                            if (error.length() < 23) {
                                try{
                                    Writer.newFile();
                                    ArrayList<Flyable> fleet = new ArrayList<Flyable>();
                                    for (String line : data) {
                                        String[] tmp = line.split(" ");
                                        fleet.add(AircraftFactory.newAircraft(hasher.getType(tmp[0]), tmp[1], hasher.getNumb(tmp[2]), hasher.getNumb(tmp[3]), hasher.getNumb(tmp[4])));
                                    }
                                    while(rounds-- > 0) {
                                        for (Flyable aircraft : fleet) {
                                        aircraft.updateConditions();
                                        }
                                        if (fleet.isEmpty())
                                            Writer.newLine("The skies are empty...");
                                    }
                                }
                                catch (Exception e){
                                    customExceptions.throwException("ERROR: Error while running the simulation.");
                                }
                            }
                            else
                                customExceptions.throwException(error.deleteCharAt(error.length() - 1).toString());
                        }
                        else {
                            try {
                                rounds = Integer.parseInt(data.get(0));
                            }
                            catch (Exception e){
                                error.append("Line 1: Number of simulation runs must be a positive integer.\n");
                                rounds = 0;
                            }
                            data.remove(0);
                            if (rounds < 0)
                                error.append("Line 1: Number of simulation runs must be a positive integer.\n");
                            for (String line : data) {
                                String[] tmp = line.split(" ");
                                if (tmp.length != 5)
                                    error.append("Line "+(data.indexOf(line) + 2)+": Invalid format. The correct format is \"TYPE NAME LONGITUTDE LATITUDE HEIGHT\".\n");
                                else {
                                    if (!tmp[0].matches("(Helicopter|JetPlane|Baloon)"))
                                        error.append("Line "+(data.indexOf(line) + 2)+": Invalid aircraft type. Choose between \"Baloon\", \"Helicopter\" and \"JetPlane\".\n");
                                    if (!tmp[1].matches("\\w+"))
                                        error.append("Line "+(data.indexOf(line) + 2)+": Invalid name. Please use only alphanumeric characters and underscores.\n");
                                    if (!tmp[2].matches("([0-9])+"))
                                        error.append("Line "+(data.indexOf(line) + 2)+": Invalid longitude value. Must be a positive integer.\n");
                                    else {
                                        try {
                                            Integer.parseInt(tmp[2]);
                                            if (Integer.parseInt(tmp[2]) < 0)
                                                error.append("Line "+(data.indexOf(line) + 2)+": Invalid latitude value. Must be a positive integer.\n");
                                        }
                                        catch (Exception e) {
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid latitude value. Must be a positive integer.\n");
                                        }
                                    }

                                    if (!tmp[3].matches("([0-9])+"))
                                        error.append("Line "+(data.indexOf(line) + 2)+": Invalid latitude value. Must be a positive integer.\n");
                                    else {
                                        try {
                                            Integer.parseInt(tmp[3]);
                                            if (Integer.parseInt(tmp[3]) < 0)
                                                error.append("Line "+(data.indexOf(line) + 2)+": Invalid latitude value. Must be a positive integer.\n");
                                        }
                                        catch (Exception e) {
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid latitude value. Must be a positive integer.\n");
                                        }
                                    }

                                    if (!tmp[4].matches("([0-9])+"))
                                        error.append("Line "+(data.indexOf(line) + 2)+": Invalid height value. Must be a positive integer between 1 and 100.\n");
                                    else {
                                        try {
                                            Integer.parseInt(tmp[4]);
                                            if (Integer.parseInt(tmp[4]) <= 0 || Integer.parseInt(tmp[4]) > 100)
                                                error.append("Line "+(data.indexOf(line) + 2)+": Invalid height value. Must be a positive integer between 1 and 100.\n");
                                        }
                                        catch (Exception e) {
                                            error.append("Line "+(data.indexOf(line) + 2)+": Invalid height value. Must be a positive integer between 1 and 100.\n");
                                        }
                                    }
                                }
                            }
                            if (error.length() < 23) {
                                try{
                                    Writer.newFile();
                                    ArrayList<Flyable> fleet = new ArrayList<Flyable>();
                                    for (String line : data) {
                                        String[] tmp = line.split(" ");
                                        fleet.add(AircraftFactory.newAircraft(tmp[0], tmp[1], Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4])));
                                    }
                                    while(rounds-- > 0) {
                                        for (Flyable aircraft : fleet) {
                                            aircraft.updateConditions();
                                        }
                                        if (fleet.isEmpty())
                                            Writer.newLine("The skies are empty...");
                                    }
                                }
                                catch (Exception e){
                                    customExceptions.throwException("ERROR: Error while running the simulation.");
                                }
                            }
                            else
                            customExceptions.throwException(error.deleteCharAt(error.length() - 1).toString());
                        }
                    }
                    else
                        customExceptions.throwException("ERROR: Specified file is blank");
                }
                catch (FileNotFoundException e) {
                    customExceptions.throwException("ERROR: The specified file could not be located on our radar.");
                }
            }
        }
        catch (CustomExceptions e) {
            System.out.println(e);
        }
    }
}