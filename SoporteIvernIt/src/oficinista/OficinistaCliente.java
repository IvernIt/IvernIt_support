package oficinista;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import Ice.Application;
import Ice.ObjectPrx;
import soporte.ModeradorPrx;
import soporte.ModeradorPrxHelper;
import soporte.OficinistaPrx;
import soporte.OficinistaPrxHelper;

/**
 * 
 * @author Gautarra
 *
 */
public class OficinistaCliente extends Application{

  Ice.Communicator comm = null;
  
  Scanner teclado;
  InputStream in;
  OutputStream out;
    
  //Cliente
  ModeradorPrx proxy;
  Oficinista oficinista;
  OficinistaPrx oficinistaPrx;
  
  //Server
  Ice.ObjectAdapter adapter;
  Ice.Identity identity;
  
  public static void main(String[] args) {
    OficinistaCliente oficinista = new OficinistaCliente();
    int status = oficinista.main("Oficinista", args);
    System.out.println(status);
  }
  
  /**
   * 
   */
  public OficinistaCliente(){
    teclado = new Scanner(System.in);
    comm = Ice.Util.initialize();
    Ice.ObjectPrx base = comm.stringToProxy("Moderador:tcp -h 172.17.28.58 -p 5577");
    proxy = ModeradorPrxHelper.checkedCast(base);
    if(proxy == null) System.out.println("No se ha podido conectar");
  }

  @Override
  public int run(String[] arg0) {
    String mensaje = null;
    
    init();
        
    System.out.println("Escribe tu mensaje");
    while(mensaje != "fin"){
      mensaje = teclado.nextLine();
      proxy.responderCliente(oficinistaPrx, mensaje);
    }
    
    
    return 0;
  }
  
  /**
   * 
   */
  private void init(){
    comm = OficinistaCliente.communicator();
    adapter = comm.createObjectAdapterWithEndpoints("Oficinista", "tcp -h 172.17.28.58 -p 5533");
    Ice.Object object = new Oficinista();
    identity = comm.stringToIdentity("Oficinista");
    adapter.add(object, identity);
    adapter.activate();   
    
    oficinista = new Oficinista();
    ObjectPrx obj = adapter.addWithUUID(oficinista);
    oficinistaPrx = OficinistaPrxHelper.checkedCast(obj);
    proxy.addOficinista(oficinistaPrx);
  }

}
