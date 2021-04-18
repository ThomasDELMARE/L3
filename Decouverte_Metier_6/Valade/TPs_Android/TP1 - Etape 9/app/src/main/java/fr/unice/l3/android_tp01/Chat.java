package fr.unice.l3.android_tp01;

public interface Chat {
    String obtenirTextTapé() ;
    void ajouterMessage(String msg);
    void ajouterMessage(final String msg, final int couleur);
    void activerInterface(boolean activation);
}
