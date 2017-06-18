package moderador;

import java.util.ArrayList;

import Ice.Current;
import soporte.ClientePrx;
import soporte.OficinistaPrx;
import soporte._ModeradorDisp;

/**
 * 
 * @author Gautarra
 *
 */
public class Moderador extends _ModeradorDisp{
  
  class Comunicador{
    ClientePrx cliente;
    OficinistaPrx oficinista;
  }
 
  ArrayList<Comunicador> comunicadorList = null;
  int indexOperadora = 0, maxOperadora = 0;
  int indexCliente = 0, maxCliente = 0;

  @Override
  public void addCliente(ClientePrx cliente, Current __current) {
    if(comunicadorList == null){
      comunicadorList = new ArrayList<>();
    }
    if(comunicadorList.isEmpty() || indexCliente > indexOperadora){
      Comunicador comunicador = new Comunicador();
      comunicadorList.add(comunicador);
      maxCliente++;
    }
    
    comunicadorList.get(indexCliente).cliente = cliente;
    System.out.println(cliente);
    indexCliente++;
  }

  @Override
  public void removeCliente(ClientePrx cliente, Current __current) {
    boolean borrado = false;
    int index = 0;
    while(!borrado && index < comunicadorList.size()){
      if(comunicadorList.get(index).cliente.equals(cliente)){
        comunicadorList.get(index).cliente = null;
        indexCliente--;
        borrado = true;
      }
      index++;
    }
  }  

  @Override
  public void addOficinista(OficinistaPrx oficinista, Current __current) {
    if(comunicadorList == null){
      comunicadorList = new ArrayList<>();
    }
    if(comunicadorList.isEmpty() || indexCliente < indexOperadora){
      Comunicador comunicador = new Comunicador();
      comunicadorList.add(comunicador);
    }
    comunicadorList.get(indexOperadora).oficinista = oficinista;
    System.out.println(oficinista);
    indexOperadora++;
  }

  @Override
  public void removeOficinista(OficinistaPrx oficinista, Current __current) {
    boolean borrado = false;
    int index = 0;
    while(!borrado && index < comunicadorList.size()){
      if(comunicadorList.get(index).oficinista.equals(oficinista)){
        comunicadorList.get(index).oficinista = null;
        indexOperadora--;
        borrado = true;
      }
      index++;
    }
  }

  @Override
  public void responderCliente(OficinistaPrx oficinista, String mensaje, Current __current) {
    boolean encontrado = false;
    int index = 0;
    
    while(!encontrado){
      if(comunicadorList.get(index).oficinista.equals(oficinista) && comunicadorList.get(index).cliente != null ){
        encontrado = true;
        comunicadorList.get(index).cliente.recivirMensaje(mensaje);
      }
    }
  }

  @Override
  public void responderOficinista(ClientePrx cliente, String mensaje, Current __current) {
    boolean encontrado = false;
    int index = 0;
    
    while(!encontrado){
      if(comunicadorList.get(index).cliente.equals(cliente) && comunicadorList.get(index).oficinista != null){
        encontrado = true;
        comunicadorList.get(index).oficinista.recivirMensaje(mensaje);
      }
    }
  }

}
