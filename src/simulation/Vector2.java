package simulation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Vector2 implements Serializable
{
    private int x;
    private int y;

    /**
     * Letrehoz egy vektort xy koordinatakkal.
     * A vektor immutabilis, azaz a koordinatak nem modosithatoak.
     * 
     * @param x vizsszintes koordinata
     * @param y fuggoleges koordinata
     */
    public Vector2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int x()
    {
        return x;
    }

    public int y()
    {
        return y;
    }


    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Megmondja ket vektorrol, hogy egyenloek-e
     * Akkor egyenloek, ha a koordinataik egyenloek.
     */
    public boolean equals(Object o)
    {
        if (!(o instanceof Vector2))
            return false;

        Vector2 v = (Vector2)o;

        return v.x == x && v.y == y;
    }

    public int hashCode()
    {
        return x + y;
    }

    // ------------------- static -------------------

    public static Vector2
        N = new Vector2(0, 1),
        S = new Vector2(0, -1),
        W = new Vector2(-1, 0),
        E = new Vector2(1, 0),
        NW = sum(N, W),
        NE = sum(N, E),
        SW = sum(S, W),
        SE = sum(S, E);

    
    
    // szomszedok iranyai

    public static Set<Vector2> dirs1 = new HashSet<Vector2>
        ( Arrays.asList(N, S, W, E) );
    
    public static Set<Vector2> dirs2 = new HashSet<Vector2>
        ( Arrays.asList(N, S, W, E, NW, NE, SW, SE) );

    /**
     * Ket vektor osszege
     * @param a Az elso vektor
     * @param b A masodik vektor
     * @return Az osszeg
     */
    public static Vector2 sum(Vector2 a, Vector2 b)
    {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    /**
     * A vektor ellentetje
     * @param a A vektor
     * @return A vele ellentetes vektor
     */
    public static Vector2 opposite(Vector2 a)
    {
        return new Vector2(-a.x, -a.y);
    }

    /**
     * Ket vektor kulonbsege
     * @param a A kivonas bal oldala
     * @param b A kivonas jobb oldala
     * @return Az eredmeny
     */
    public static Vector2 diff(Vector2 a, Vector2 b)
    {
        return sum(a, opposite(b));
    }


}
