package model;

import java.io.Serializable;

public class Parametre implements Serializable {

    protected int nbHeure;
    protected int nbElement;
    protected float superieurA;
    protected float inferieurA;


    public int getNbHeure() {
        return nbHeure;
    }

    public void setNbHeure(int nbHeure) {
        this.nbHeure = nbHeure;
    }

    public int getNbElement() {
        return nbElement;
    }

    public void setNbElement(int nbElement) {
        this.nbElement = nbElement;
    }

    public float getSuperieurA() {
        return superieurA;
    }

    public void setSuperieurA(float superieurA) {
        this.superieurA = superieurA;
    }

    public float getInferieurA() {
        return inferieurA;
    }

    public void setInferieurA(float inferieurA) {
        this.inferieurA = inferieurA;
    }

    @Override
    public String toString() {
        return "Parametre{" +
                "nbHeure=" + nbHeure +
                ", nbElement=" + nbElement +
                ", superieurA=" + superieurA +
                ", inferieurA=" + inferieurA +
                '}';
    }
}
