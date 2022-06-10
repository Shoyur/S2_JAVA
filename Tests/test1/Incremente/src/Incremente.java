public class Incremente {
    
    private int increment;
    private int petitPlus; 
    
    public Incremente(int inc, int petit) { 
        increment = inc; 
        petitPlus = petit; 
    }
    
    public int additionne(int n) { 
        return n + increment + petitPlus; 
    } 
} 
