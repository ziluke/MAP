package View.Commands;

import Controller.Controller;

public class RunExample extends Command {
    private Controller ctrl;

    public RunExample(String key, String desc, Controller contr){
        super(key, desc);
        ctrl = contr;
    }

    @Override
    public void execute() {
            ctrl.allStep();
    }
}
