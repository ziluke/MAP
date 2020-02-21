package View;

import View.Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){commands = new HashMap<>();
    }

    public void addCommand(Command cmd){
        commands.put(cmd.getKey(), cmd);
    }

    private void printMenu(){
        for(Command com : commands.values()){
            String line = String.format("%s: %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner sc = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Input option: ");
            String key = sc.nextLine();
            Command com = commands.get(key);
            if(com == null){
                System.out.println("Invalid option!\n");
                continue;
            }
            com.execute();
        }
    }
}
