module soporte{

  interface Oficinista{
    void recivirMensaje(string mensaje);
  };
  
  interface Cliente{
    void recivirMensaje(string mensaje);
  };

  interface Moderador{
    void addCliente(Cliente* cliente);
    void removeCliente(Cliente* cliente);
    void addOficinista(Oficinista* oficinista);
    void removeOficinista(Oficinista* oficinista);
    void responderCliente(Oficinista* oficinista, string mensaje);
    void responderOficinista(Cliente* cliente, string mensaje);
  };

};