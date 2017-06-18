package cliente;

import Ice.Current;
import soporte._ClienteDisp;

/**
 * 
 * @author Gautarra
 *
 */
public class Cliente extends _ClienteDisp{

  @Override
  public void recivirMensaje(String mensaje, Current __current) {
    System.out.println("Oficinista: " + mensaje);    
  }

}
