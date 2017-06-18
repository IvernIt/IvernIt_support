package cliente;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import Ice.Application;
import Ice.ObjectPrx;
import soporte.ClientePrx;
import soporte.ClientePrxHelper;
import soporte.ModeradorPrx;
import soporte.ModeradorPrxHelper;

/**
 * 
 * @author Gautarra
 *
 */
public class ClienteCliente extends Application{
  
  Ice.Communicator comm = null;
  
  Scanner teclado;
  InputStream in;
  OutputStream out;
  
  //Cliente
  ModeradorPrx proxy;
  Cliente cliente;
  ClientePrx clientePrx;
  
  //Server
  Ice.ObjectAdapter adapter;
  Ice.Identity identity;
  
  public static void main(String[] args){
    ClienteCliente cliente = new ClienteCliente();
    int status = cliente.main("Cliente soporte", args);
    System.out.println(status);
  }
  
  /**
   * 
   */
  public ClienteCliente(){
    teclado = new Scanner(System.in);
    comm = Ice.Util.initialize();
    Ice.ObjectPrx base = comm.stringToProxy("Moderador:tcp -h 172.17.28.58 -p 5577");
    proxy = ModeradorPrxHelper.checkedCast(base);
    if(proxy == null) System.out.println("No se ha podido conectar");
  }

  @Override
  public int run(String[] arg0) {
    String mensaje = "";
    
    init();
        
    System.out.println("Escribe tu mensaje");
    while(!mensaje.equals("fin")){
      mensaje = teclado.nextLine();
      if(!mensaje.equals("fin")){
        proxy.responderOficinista(clientePrx, mensaje);
      }
    }
    
    proxy.removeCliente(clientePrx);    
    
    return 0;
  }
  
  /**
   * 
   */
  private void init() {
    comm = ClienteCliente.communicator();
    adapter = comm.createObjectAdapterWithEndpoints("Cliente", "tcp -h 172.17.28.58 -p 5599");
    Ice.Object object = new Cliente();
    identity = comm.stringToIdentity("Cliente");
    adapter.add(object, identity);
    adapter.activate();
    
    cliente = new Cliente();
    ObjectPrx obj = adapter.addWithUUID(cliente);
    clientePrx = ClientePrxHelper.checkedCast(obj);
    proxy.addCliente(clientePrx);
  }

}
