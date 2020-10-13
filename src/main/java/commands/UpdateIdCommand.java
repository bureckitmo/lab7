package commands;

import database.Credentials;
import database.DatabaseController;
import exceptions.InvalidValueException;
import managers.CollectionManager;
import managers.ConsoleManager;
import models.Movie;

public class UpdateIdCommand extends AbstractCommand {

    public UpdateIdCommand(){
        cmdName = "update";
        description = "обновляет значение элемента коллекции";
        argCount = 1;
        needInput = true;
    }

    @Override
    public Object getInput(ConsoleManager consoleManager){
        return consoleManager.getMovie();
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager, DatabaseController databaseController, Credentials credentials) {
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new InvalidValueException("Format error");
        }

        String cityID = databaseController.updateMovie(id, (Movie) inputData, credentials);
        if (cityID == null) {
            if(collectionManager.update((Movie) inputData, id))
                consoleManager.writeln("Element with id(" + id + ") - edited");
            else
                consoleManager.writeln("Element with id(" + id + ") - doesn't");
        } else {
            consoleManager.writeln("Have some problems: " + cityID);
        }

        inputData = null;

        return null;
    }
}
