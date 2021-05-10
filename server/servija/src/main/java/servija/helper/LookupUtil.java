package servija.helper;

public class LookupUtil {
   public static <E extends Enum<E>> E lookup(Class<E> e, String id) {   
	   E result; 
      try {          
         result = Enum.valueOf(e, id);
      } catch (IllegalArgumentException ex) {
         // log error or something here
           return null;
      }

      return result;
   }
}