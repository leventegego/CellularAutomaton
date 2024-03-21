package simulation;

import java.io.Serializable;
import java.util.*;

public class Ruleset implements GridManipulator, Serializable
{
    private String str;
    private Set<Integer> born;
    private Set<Integer> survive;

    /**
     * Szamjegyek string-jet Integer-ek Set-jeve alakitja.
     * A stringben levo szamoknak csak a szamjegyeit rakja bele.
     * 
     * @param digits Csak szamjegyekbol allo string.
     * @return A Set, ami a string szamjegyeit tartalmazza.
     */
    private Set<Integer> setFromDigits(String digits)
    {
        Set<Integer> ret = new HashSet<Integer>();

        for(char ch : digits.toCharArray())
            ret.add(Character.getNumericValue(ch)); // ha ez false, akkor duplan van egy szamjegy, de kiterdekel

        return ret;
    }

    /**
     * Letrehoz egy Ruleset-et az adott szabaly-stringbol.
     * A Ruleset kepes a tabla szabaly szerinti valtoztatasara.
     * 
     * @param ruleString A szabaly reprezentacioja, pl: "B3/S23" -nel
     * 3 szomszed eseten megszuletik es 2 vagy 3 szomszed eseten tulel.
     * @throws IllegalArgumentException Ha a megadott string formatuma helytelen, akkor kivetelt dob.
     */
    public Ruleset(String ruleString) throws IllegalArgumentException
    {
        setRule(ruleString);
    }

    /**
     * A konstruktorhoz hasonloan beallitja a szabalyrendszert string alapjan.
     * 
     * @param ruleString A szabaly reprezentacioja, pl: "B3/S23" -nel
     * 3 szomszed eseten megszuletik es 2 vagy 3 szomszed eseten tulel.
     * @throws IllegalArgumentException Ha a megadott string formatuma helytelen, akkor kivetelt dob.
     */
    public void setRule(String ruleString) throws IllegalArgumentException
    {
        if(!ruleString.matches("B[0-9]{0,9}/S[0-9]{0,9}")) // B<szamok>/S<szamok>
            throw new IllegalArgumentException();

            

        str = ruleString;

        String[] parts = ruleString.split("/");
        String bornDigits = parts[0].substring(1);      // kezdo betut kihagyjuk
        String surviveDigits = parts[1].substring(1);   // 

        born = setFromDigits(bornDigits);
        survive = setFromDigits(surviveDigits);
    }

    /**
     * String-e alakitja a szabalyt.
     * 
     * @return a szabaly szokasos string reprezentacioja.
     */
    public String toString()
    {
        return str;
    }

    /**
     * A szabaly alapjan megmondja, hogy adott szomszedszam eseten
     * megszuletik-e a cella.
     * (Csak halott cella eseten ertelmezheto)
     * 
     * @param neighbourCount Hany szomszedja el a cellanak
     * @return El-e a cella a kovetkezo iteracioban
     */
    public boolean isBorn(int neighbourCount)
    {
        return born.contains(Integer.valueOf(neighbourCount));
    }

    /**
     * A szabaly alapjan megmondja, hogy adott szomszedszam eseten
     * tulel-e a cella.
     * (Csak elo cella eseten ertelmezheto)
     * 
     * @param neighbourCount Hany szomszedja el a cellanak
     * @return El-e a cella a kovetkezo iteracioban
     */
    public boolean doesSurvive(int neighbourCount)
    {
        return survive.contains(Integer.valueOf(neighbourCount));
    }

    /**
     * GridManipulator-t implementalja
     * 
     * A szabal alapjan megmondja, hogy
     * az adott cella el-e a kovetkezo iteracioban.
     * 
     * @param grid A tabla, amin a cellakat vizsgaljuk.
     * @param pos A pozicio, ahol a cella van a tablan.
     */
    public void apply(Grid grid, Vector2 pos)
    {
        Cell c = grid.at(pos);
        
        int count = 0;
        for(Vector2 dir : Vector2.dirs2)   // megszamoljuk az elo szomszedokat
        {
            Vector2 neighbor = Vector2.sum(pos, dir);
            if(grid.isValid(neighbor) && grid.at(neighbor).isAlive())
                count++;
        }

        // eletben van es tulel vagy halott de megszuletik
        c.setNext( c.isAlive() ? doesSurvive(count) : isBorn(count) );
    }
    
}
