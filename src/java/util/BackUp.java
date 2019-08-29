package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de realizar un backup diario de la base de datos. Ademas, elimina aquellos que tienen una semana de antiguedad.
 * @author IES 9-024 LAVALLE.
 * @version 2.019.
 */
public class BackUp extends TimerTask{

    @Override
    public void run() {
        boolean antiguo;
        
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        ArrayList ficheros = new ArrayList();
        
        try {
            antiguo = true;
            Files.walk(Paths.get("C:/backups")).forEach(ruta-> {
                if (Files.isRegularFile(ruta)) {
                    ficheros.add(ruta);
                }
            });
            
            for(int i = 0; i < ficheros.size(); i++){
                try {
                    System.out.println(ficheros.get(i).toString());
                    BasicFileAttributes attrs;
                    attrs = Files.readAttributes(Paths.get(ficheros.get(i).toString()), BasicFileAttributes.class);
                    FileTime time = attrs.creationTime();
                    Date creacion = new Date(time.toMillis());
                    int dias=(int) ((fecha.getTime()-creacion.getTime())/86400000);
                    System.out.println(dias);
                    if(dias >= 7){
                        File file = new File(ficheros.get(i).toString());
                        file.delete();
                    }
                    if(dias < 1){
                        antiguo = false;
                    }

                    
                } catch (IOException ex) {
                    Logger.getLogger(ConectorDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(antiguo == true){
                Process p;
                String cmd = "mysqldump -u bibliotecario -pies9024 biblioteca";
        
                try {
                    p = Runtime.getRuntime().exec(cmd);
                    InputStream is = p.getInputStream();
                    FileOutputStream fos = new FileOutputStream("C:/backups/biblioteca_"+sdf.format(fecha)+".sql");
                    byte[] buffer = new byte[1024];
                    int leido = is.read(buffer);
                    System.out.println(leido);
                    while(leido > 0){
                        fos.write(buffer, 0, leido);
                        leido = is.read(buffer);
                    }

                    fos.close();

                } catch (IOException ex) {
                    Logger.getLogger(ConectorDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
        } catch (IOException ex) {
            Logger.getLogger(ConectorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
