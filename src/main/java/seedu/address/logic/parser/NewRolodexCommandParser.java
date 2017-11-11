package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXTENSION_FORMAT;
import static seedu.address.commons.util.StringUtil.replaceBackslashes;
import static seedu.address.storage.util.RolodexStorageUtil.ROLODEX_FILE_EXTENSION;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageExtension;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageFilepath;

import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.parser.exceptions.ParseArgsException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NewRolodexCommand object
 */
public class NewRolodexCommandParser implements Parser<NewRolodexCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NewRolodexCommand
     * and returns a NewRolodexCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * @throws ParseArgsException if the user input does not conform the expected format, but is in a suggestible format
     */
    @Override
    public NewRolodexCommand parse(String args) throws ParseArgsException, ParseException {
        String trimmedAndFormattedArgs = replaceBackslashes(args.trim());
        if (trimmedAndFormattedArgs.isEmpty()
                || !isValidRolodexStorageFilepath(trimmedAndFormattedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewRolodexCommand.MESSAGE_USAGE));
        }
        if (!isValidRolodexStorageExtension(trimmedAndFormattedArgs)) {
            throw new ParseArgsException(String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));
        }

        return new NewRolodexCommand(trimmedAndFormattedArgs);
    }
}
