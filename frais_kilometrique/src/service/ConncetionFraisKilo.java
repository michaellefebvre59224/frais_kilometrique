package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConncetionFraisKilo {
    //---------------------- U R L   D E   C O N N E C T I O N ------------------------------------------
    private final static String URL = "jdbc:mysql://localhost:3306/frais_kilo";

    //---------------------- U T I L I S A T E U R   D E   C O N N E C T I O N --------------------------
    private final static String USER = "michael";

    //------------------- M O T   D E   P A S S E   D E   C O N N E C T I O N ----------------------------
    private final static String PW = "mdppopmichael";

    //--------------------------------- S I N G L E T O N ------------------------------------------------
    private static Connection INSTANCE;

    //-------------------------------- C O N N E C T I O N   P R I V É -----------------------------------
    private ConncetionFraisKilo() {
        try{
            INSTANCE = DriverManager.getConnection(URL,USER,PW);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getInstance(){
        if (INSTANCE == null){
            new ConncetionFraisKilo();
        }
        return INSTANCE;
    }

}
