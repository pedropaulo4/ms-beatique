package br.com.beautique.ms_sync.utils;

import java.util.logging.Logger;

public class SyncLogger {

    private static final Logger logger = Logger.getLogger("SyncLogger");

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.severe(message);
    }

    public static void trace(String message) {
        logger.finer(message);
    }
}
