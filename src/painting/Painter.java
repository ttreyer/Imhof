package painting;

import java.util.function.Predicate;

import javafx.scene.shape.Polyline;
import painting.LineStyle.LINE_CAP;
import painting.LineStyle.LINE_JOIN;
import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;


/**
 * Représente un peintre de base
 * @author Thierry Treyer (235116)
 * @author Dominique Roduit (234868)
 *
 */
public interface Painter {
    /**
     * Dessine la carte sur la toile
     * @param map Carte
     * @param canvas Toile
     */
    public void drawMap(Map map, Java2DCanvas canvas);
    
    /**
     * Retourne un peintre dessinant l'intérieur de tous les polygones de la carte qu'il reçoit avec la couleur spécifiée
     * @param color Couleur de remplissage des polygones
     * @return Peintre de base (cf. description de la méthode)
     */
    public static Painter polygon(Color color) {
        return (map, canvas) -> {
            for(Attributed<Polygon> p : map.polygons())
                canvas.drawPolygon(p.value(), color);
        };
    }
    
    /**
     * Retourne un peintre dessinant toutes les lignes de la carte qu'on lui fournis avec le style correspondant
     * @param lc Type de terminaison 
     * @param lj Type de jointure
     * @param color Couleur des traits
     * @param width Largeur des traits
     * @param dashed Séquence d'alternance pour le dessin en traitillés des segments
     * @return Peintre de base (cf. description de la méthode)
     */
    public static Painter line(LINE_CAP lc, LINE_JOIN lj, Color color, float width, float[] dashed) {
        return (map, canvas) -> {
            for(Attributed<PolyLine> p : map.polyLines()) 
                canvas.drawPolyline(p.value(), new LineStyle(lc, lj, color, width, dashed));  
        };
    }
    
    /**
     * Retourne un peintre dessinant toutes les lignes de la carte qu'on lui fournis avec le style correpondant
     * @param width Largeur des traits
     * @param color Couleur des traits
     * @return Peintre de base (cf. description de la méthode)
     */
    public static Painter line(float width, Color color) {
        return (map, canvas) -> {
          for(Attributed<PolyLine> p : map.polyLines()) 
              canvas.drawPolyline(p.value(), new LineStyle(width, color));
        };
    }
    
    /**
     * Retourne un peintre dessinant les pourtours de l'enveloppe
     * et des trous de tous les polygons de la carte avec le style donné
     * @param style Style de traits
     * @return Peintre de base (cf. description de la méthode)
     */
    public static Painter outline(LineStyle style) {
        return (map, canvas) -> {
            for(Attributed<Polygon> p : map.polygons()) {
                for(ClosedPolyLine h : p.value().holes())
                    canvas.drawPolyline(h, style);
                
                canvas.drawPolyline(p.value().shell(), style);
            }
        };
    }
    
    /**
     * Retourne un peintre dessinant les pourtours de l'enveloppe
     * et des trous de tous les polygones de la carte qu'on lui fournit
     * @param lc Type de terminaison 
     * @param lj Type de jointure
     * @param color Couleur des traits
     * @param width Largeur des traits
     * @param dashed Séquence d'alternance pour le dessin en traitillés des segments
     * @return Peintre de base (cf. description de la méthode)
     */
    public static Painter outline(LINE_CAP lc, LINE_JOIN lj, Color color, float width, float[] dashed) {
        LineStyle style = new LineStyle(lc, lj, color, width, dashed);
        return outline(style);
    }
    
    /**
     * Retourne un peintre dessinant les pourtours de l'enveloppe
     * et des trous de tous les polygones de la carte qu'on lui fournit
     * @param width Largeur des traits
     * @param color Couleur des traits
     * @return Peintre de base (cf. description de la méthode)
     */
    public static Painter outline(float width, Color color) {
        LineStyle style = new LineStyle(width, color);
        return outline(style);
    }
    

    public default Painter when(Predicate<Attributed<?>> p) {
        return (map, canvas) -> {
            
        };
    }
    
    public default Painter above(Painter p) {
        return (map, canvas) -> {
            
        };
    }
    
    
    public default Painter layered() {
        return (map, canvas) -> {
            
        };
    }
    
}
