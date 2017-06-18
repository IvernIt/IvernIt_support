package oficinista;

import Ice.Current;
import soporte._OficinistaDisp;

/**
 * 
 * @author Gautarra
 *
 */
public class Oficinista extends _OficinistaDisp{

  @Override
  public void recivirMensaje(String mensaje, Current __current) {
    System.out.println("Cliente: " + mensaje);
  }
  

}
