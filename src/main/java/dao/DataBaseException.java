package dao;

import org.apache.log4j.Logger;

import javax.swing.*;

public class DataBaseException extends Exception{
    private Logger logger = Logger.getLogger(ContactDao.class);
    public DataBaseException(){
        super("Erreur base de données");
    }

    public DataBaseException(Throwable ex){
        super(ex);
       logger.error(ex);

    }
}
