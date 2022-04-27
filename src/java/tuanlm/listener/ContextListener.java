/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author MINH TUAN
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String fileName = context.getRealPath("/") + "WEB-INF" + "\\ResourceFile.txt";
        Map<String, String> map = readfile(fileName);
        context.setAttribute("MAP", map);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    public Map<String, String> readfile(String fileName) {
        Map<String, String> map = new HashMap<>();

        FileReader fr = null;
        BufferedReader br = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] details = line.split("=");
                if (details != null && details.length == 2) {
                    map.put(details[0], details[1]);
                }
            }
            System.out.println(map.size());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return map;
    }
}
