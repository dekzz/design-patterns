
package org.dt.datomala_zadaca_4;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Serves for opening, writing and closing the given
 * log file name.
 * 
 * @author dex
 */
public class Log {
    
    private static Log logInstance;
    private static String fileName;
    static FileHandler fh;  
    static final Logger logger = Logger.getLogger("UzDiz_log");
    
    /**
     * Sets log file name via constructor
     * 
     * @param fileName - name of log file
     */
    private Log(String fileName) {
        Log.fileName = fileName;
    }
    
    /**
     * Gets a Log instance (Singleton pattern)
     * 
     * @return Returns Log instance
     */
    public static Log getInstance() {
        if(logInstance != null)
            return logInstance;
        else
            return logInstance = new Log(fileName);
    }
    
    /**
     * Opens log file for reading/writing
     * 
     * @return true if successful, false otherwise
     */
    private boolean openLog() {
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(fileName, true);
            logger.addHandler(fh);
            //logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
            return true;
            
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Writes given message to previously opened log file and then closes it.
     * Method is synchronized for multi-threading
     * 
     * @param message - String sequence of an event that is to be logged
     * @return true if successful, false otherwise
     */
    public synchronized boolean writeLog(String message) {
        if(openLog()) {
            logger.info(message); 
            closeLog();
            return true;
        } else {
            System.out.println("! Could not open log file!");
            return false;
        }
    }
    
    /**
     * Closes the log file
     * 
     */
    private void closeLog() {
        fh.close();
    }

    public static void setFileName(String fileName) {
        Log.fileName = fileName;
    }
    
}
