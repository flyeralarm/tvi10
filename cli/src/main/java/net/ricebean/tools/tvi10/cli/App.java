package net.ricebean.tools.tvi10.cli;

import com.lowagie.text.DocumentException;
import net.ricebean.tools.tvi10.lib.StripBuilder;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Applications main class.
 */
public class App {

    private static final String OPT_CODE = "code";

    private static final String OPT_HELP = "help";

    /**
     * Applications main entrance point
     * @param args The applications command arguments.
     */
    public static void main(String[] args) throws ParseException, IOException, DocumentException {

        // parse input
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(initOptions(), args);

        // analyze input
        if(cmd.hasOption(OPT_HELP)) {
            printHelp();
        }

        if(cmd.getArgList().size() == 0) {
            System.out.println("Please enter a target path.");
            System.out.println("");
            printHelp();

        } else {
            File file = Paths.get(cmd.getArgList().get(0)).toFile();

            long value = Long.parseLong(cmd.getOptionValue(OPT_CODE));
            StripBuilder.createTvi10Strip(value, file);

            System.out.println("Creation successful.");
        }
    }

    /**
     * Print the commands help.
     */
    private static void printHelp() {
        String header = "ricebean.net tvi 10";
        String footer = "Please report issues to https://ricebean.net/tvi10";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("tvi10 [OPTIONS] targetPdf", header, initOptions(), footer, false);
    }

    /**
     * Initialize the applications options.
     * @return The applications options.
     */
    private static Options initOptions() {

        Options options = new Options();

        options.addOption(Option.builder("c")
                .longOpt(OPT_CODE)
                .argName("long")
                .hasArg()
                .desc("The code to be encoded.")
                .required()
                .build()
        );

        options.addOption(Option.builder("h")
                .longOpt(OPT_HELP)
                .hasArg(false)
                .desc("Show this help.")
                .build()
        );

        return options;
    }
}
