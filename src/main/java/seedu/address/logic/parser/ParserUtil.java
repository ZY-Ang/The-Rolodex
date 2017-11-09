package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.index.Index.INDEX_VALIDATION_REGEX;
import static seedu.address.commons.util.StringUtil.replaceBackslashes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.model.person.Address.ADDRESS_BLOCK_NUMBER_MATCHING_REGEX;
import static seedu.address.model.person.Address.ADDRESS_BLOCK_WORD_MATCHING_REGEX;
import static seedu.address.model.person.Email.EMAIL_VALIDATION_REGEX;
import static seedu.address.model.person.Phone.PHONE_VALIDATION_REGEX;
import static seedu.address.storage.util.RolodexStorageUtil.FILEPATH_REGEX_NON_STRICT;
import static seedu.address.storage.util.RolodexStorageUtil.ROLODEX_FILE_EXTENSION;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageFilepath;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> subject} into an {@code Optional<String>} if {@code subject} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseSubject(Optional<String> subject) throws IllegalValueException {
        requireNonNull(subject);
        return subject.isPresent() ? subject : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /**
     * Attempts to parse a {@code String} to an {@code Integer}.
     * Looks for numbers given a value and parses the first instance of the number.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean isParsableInt(String value) {
        try {
            parseFirstInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the first integer found in a {@code String}.
     * @param value to be parsed.
     * @return {@code int} positive, non-zero value of the integer.
     * @throws NumberFormatException if no integer was found.
     */
    public static int parseFirstInt(String value) throws NumberFormatException {
        Pattern numbers = Pattern.compile(INDEX_VALIDATION_REGEX);
        Matcher m = numbers.matcher(value);
        if (m.find()) {
            int firstInt = Integer.parseInt(m.group());
            if (firstInt != 0) {
                return Math.abs(firstInt);
            }
        }
        throw new NumberFormatException();
    }

    /**
     * Returns a {@code String} after the first integer has been removed.
     * @param value to be parsed.
     * @return a {@code String} without the first integer.
     */
    public static String parseRemoveFirstInt(String value) {
        String firstInt;
        try {
            firstInt = Integer.toString(parseFirstInt(value));
        } catch (NumberFormatException e) {
            firstInt = "";
        }
        return value.substring(0, value.indexOf(firstInt)).trim()
                .concat(" ")
                .concat(value.substring(value.indexOf(firstInt) + firstInt.length()).trim()).trim();
    }

    /**
     * Attempts to parse a {@code String} to an {@code Name}.
     * Uses the {@code parseName} method directly and assert if the {@code String} is parsable.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean isParsableName(String value) {
        try {
            Optional possible = parseName(Optional.of(parseRemainingName(value)));
            return possible.isPresent() && possible.get() instanceof Name;
        } catch (NumberFormatException | IllegalValueException e) {
            return false;
        }
    }

    /**
     * Returns a parsed {@code String} name from
     * the remaining {@code String} value.
     */
    public static String parseRemainingName(String value) {
        return value.replaceAll("[^A-Za-z0-9 ]", "");
    }

    /**
     * Attempts to parse a {@code String} to a file path.
     * Looks for a regex given a value and parses the first instance of the file path.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean isParsableFilePath(String value) {
        try {
            String tryFilepath = parseFirstFilePath(value);
            return isValidRolodexStorageFilepath(tryFilepath);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the file path found in a {@code String}.
     * @param value to be parsed.
     * @return {@code String} value of the file path appended with the file extension.
     * @throws IllegalArgumentException if no file path was found.
     */
    public static String parseFirstFilePath(String value) throws IllegalArgumentException {
        Matcher m = Pattern.compile(FILEPATH_REGEX_NON_STRICT).matcher(replaceBackslashes(value).trim());
        if (m.find() && isValidRolodexStorageFilepath(m.group())) {
            return m.group().replaceAll(ROLODEX_FILE_EXTENSION, "").trim() + ROLODEX_FILE_EXTENSION;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Attempts to parse a {@code String} to a phone.
     * Looks for a regex given a value and parses the first instance of the phone.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean isParsablePhone(String value) {
        try {
            Optional possible = parsePhone(Optional.of(parseFirstPhone(value)));
            return possible.isPresent() && possible.get() instanceof Phone;
        } catch (IllegalArgumentException | IllegalValueException e) {
            return false;
        }
    }

    /**
     * Returns the phone found in a {@code String}.
     * @param value to be parsed.
     * @return {@code String} value of the phone.
     * @throws IllegalArgumentException if no phone was found.
     */
    public static String parseFirstPhone(String value) throws IllegalArgumentException {
        Matcher m = Pattern.compile(PHONE_VALIDATION_REGEX).matcher(value);
        if (m.find()) {
            return m.group();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a {@code String} after the first phone has been removed.
     * @param value to be parsed.
     * @return a {@code String} without the first phone.
     */
    public static String parseRemoveFirstPhone(String value) {
        String firstPhone;
        try {
            firstPhone = parseFirstPhone(value);
        } catch (IllegalArgumentException e) {
            firstPhone = "";
        }
        return value.substring(0, value.indexOf(firstPhone)).trim()
                .concat(" ")
                .concat(value.substring(value.indexOf(firstPhone) + firstPhone.length()).trim()).trim();
    }

    /**
     * Attempts to parse a {@code String} to a email.
     * Looks for a regex given a value and parses the first instance of the email.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean isParsableEmail(String value) {
        try {
            Optional possible = parseEmail(Optional.of(parseFirstEmail(value)));
            return possible.isPresent() && possible.get() instanceof Email;
        } catch (IllegalArgumentException | IllegalValueException e) {
            return false;
        }
    }

    /**
     * Returns the email found in a {@code String}.
     * @param value to be parsed.
     * @return {@code String} value of the email.
     * @throws IllegalArgumentException if no email was found.
     */
    public static String parseFirstEmail(String value) throws IllegalArgumentException {
        Matcher m = Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(value);
        if (m.find()) {
            return m.group();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a {@code String} after the first email has been removed.
     * @param value to be parsed.
     * @return a {@code String} without the first email.
     */
    public static String parseRemoveFirstEmail(String value) {
        String firstEmail;
        try {
            firstEmail = parseFirstEmail(value);
        } catch (IllegalArgumentException e) {
            firstEmail = "";
        }
        return value.substring(0, value.indexOf(firstEmail)).trim()
                .concat(" ")
                .concat(value.substring(value.indexOf(firstEmail) + firstEmail.length()).trim()).trim();
    }

    /**
     * Returns a {@code String} after the tags have been removed.
     * @param value to be parsed.
     * @param tags that were added.
     * @return a {@code String} without the tags.
     */
    public static String parseRemoveTags(String value, List<String> tags) {
        for (String tag : tags) {
            value = value.substring(0, value.indexOf(tag)).trim()
                    .concat(" ")
                    .concat(value.substring(value.indexOf(tag) + tag.length()).trim()).trim();
        }
        return value;
    }

    /**
     * Attempts to parse a {@code String} to an address.
     * Looks for a regex given a value and parses the address, until the end of the string.
     *
     * @return {@code true} if successfully parsed,
     * {@code false} otherwise.
     */
    public static boolean isParsableAddressTillEnd(String value) {
        try {
            Optional possible = parseAddress(Optional.of(parseAddressTillEnd(value)));
            return possible.isPresent() && possible.get() instanceof Address;
        } catch (IllegalArgumentException | IllegalValueException e) {
            return false;
        }
    }

    /**
     * Returns the address found in a {@code String}, until the end of the String.
     * @param value to be parsed.
     * @return {@code String} value of the address.
     * @throws IllegalArgumentException if no file path was found.
     */
    public static String parseAddressTillEnd(String value) throws IllegalArgumentException {
        Matcher m = Pattern.compile(PREFIX_ADDRESS.toString()).matcher(value);
        if (m.find()) {
            return value.substring(value.indexOf(m.group()) + PREFIX_ADDRESS.toString().length()).trim();
        }
        m = Pattern.compile(ADDRESS_BLOCK_WORD_MATCHING_REGEX).matcher(value);
        if (m.find()) {
            return value.substring(value.indexOf(m.group())).trim();
        }
        m = Pattern.compile(ADDRESS_BLOCK_NUMBER_MATCHING_REGEX).matcher(value);
        if (m.find()) {
            return value.substring(value.indexOf(m.group())).trim();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns a {@code String} after the address has been removed.
     * @param value to be parsed.
     * @return a {@code String} without the address.
     */
    public static String parseRemoveAddressTillEnd(String value) {
        String address = parseAddressTillEnd(value);
        return value.substring(0, value.indexOf(address)).trim();
    }
}
