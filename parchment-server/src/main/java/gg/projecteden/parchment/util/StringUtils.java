package gg.projecteden.parchment.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String colorChar = "§";
    private static final String altColorChar = "&";
    private static final String colorCharsRegex = "[" + colorChar + altColorChar + "]";
    private static final Pattern hexPattern = Pattern.compile(colorCharsRegex + "#[a-fA-F\\d]{6}");

    public static String colorize(String input) {
        if (input == null)
            return null;

        while (true) {
            Matcher matcher = hexPattern.matcher(input);
            if (!matcher.find()) break;

            String color = matcher.group();
            input = input.replace(color, ChatColor.of(color.replaceFirst(colorCharsRegex, "")).toString());
        }

        return ChatColor.translateAlternateColorCodes(altColorChar.charAt(0), input);
    }

}