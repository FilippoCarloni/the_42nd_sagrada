package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;

import java.io.*;

class ClientStatus implements Serializable {
    private static final long serialVersionUID = Settings.SERIAL_VERSION_CLIENTSTATUS;
    private String username;
    private String sessionID;
    ClientStatus(String sessionID, String username){
        this.sessionID=sessionID;
        this.username=username;
    }

    String getUsername() {
        return username;
    }

    String getSesssion() {
        return sessionID;
    }

    static ClientStatus restoreClientStatus(String name){
        FileInputStream fileIn=null;
        ObjectInputStream in=null;
        ClientStatus status=null;
        try {
            fileIn = new FileInputStream("./ClientStatus_"+name+".ser");
            in = new ObjectInputStream(fileIn);
            status = (ClientStatus) in.readObject();
        } catch (IOException i) {
            System.out.println("A previous game session of "+name+" session not found");
        } catch (ClassNotFoundException c) {
            System.out.println("A previous game session not found");
            System.exit(0);
        } finally {
            try {
                if(in!=null)
                    in.close();
                if(fileIn!=null)
                    fileIn.close();
            } catch (IOException e) {

            }
        }
        return status;
    }

    static void saveStatus(ClientStatus clientStatus){
        FileOutputStream fileOut=null;
        ObjectOutputStream out=null;
        try {
            fileOut = new FileOutputStream("./ClientStatus_"+clientStatus.getUsername()+".ser");
            out= new ObjectOutputStream(fileOut);
            out.writeObject(clientStatus);
            out.close();
            fileOut.close();
        } catch (IOException i) {

        }
        finally {
            try {
                if (out != null)
                    out.close();
                if (fileOut != null)
                    fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
