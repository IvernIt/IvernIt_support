package moderador;

import java.util.Scanner;

import Ice.Application;

/**
 * 
 * @author Gautarra
 *
 */
public class Servidor extends Application{
  
  Ice.Communicator comm = null;
  
  //Server
  Ice.ObjectAdapter adapter;
  Ice.Identity id;

  Scanner teclado;
  
  /**
   * 
   */
  public Servidor(){
    teclado = new Scanner(System.in);
    comm = Ice.Util.initialize();
  }

  public static void main(String[] args) {
    Servidor server = new Servidor();
    int status = server.main("Server Moderator Manager", args);
    System.out.println(status);
  }

  @Override
  public int run(String[] arg0) {
    init();
    
    System.out.println("Press return to stop the service");
    teclado.nextLine();
    
    free();
    return 0;
  }

  /**
   * 
   */
  private void init() {
    comm = Servidor.communicator();
    adapter = comm.createObjectAdapterWithEndpoints("Moderador", "tcp -h 172.17.28.58 -p 5577");
    Ice.Object object = new Moderador();
    id = comm.stringToIdentity("Moderador");
    adapter.add(object, id);
    adapter.activate();
  }

  /**
   * 
   */
  private void free() {
    adapter.remove(id);
    adapter.deactivate();
    adapter.waitForDeactivate();
    adapter.destroy();
    comm.destroy();
    teclado.close();
  }

}
