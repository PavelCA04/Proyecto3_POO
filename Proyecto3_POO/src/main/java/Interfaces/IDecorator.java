/*
    processMessage: Método utilizado para transformar el mensaje. 
    getContent: Método que regresa el contenido del mensaje en String. 
    setContent: Método que nos permite establecer el contenido del mensaje.
 */
package Interfaces;

/**
 *
 * @author pavel
 */
public interface IDecorator {
    public IDecorator processMessage  ();
    public String getContent();
    public void setContent(String content);
}
