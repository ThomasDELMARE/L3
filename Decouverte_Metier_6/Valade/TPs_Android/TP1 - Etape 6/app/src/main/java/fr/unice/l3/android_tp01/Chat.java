package fr.unice.l3.android_tp01;

public interface Chat {
    public String obtenirTextTapé() ;
    public void ajouterMessage(String msg);
    public void ajouterMessage(final String msg, final int couleur);
}
