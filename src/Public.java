import java.util.HashMap;

public enum Public {
    NV("NV"), MV("MV");

    public static final HashMap<String, Public> validPublic = createValidPublic();

    // The categorie string.
    private String dpublic;

    /**
     * Initialise with the corresponding public.
     * 
     * @param dpublic
     *            The public string.
     */
    Public(String dpublic) {
        this.dpublic = dpublic;
    }

    private static HashMap<String, Public> createValidPublic() {
        HashMap<String, Public> map = new HashMap<String, Public>();
        for (Public dpublic : Public.values()) {
            map.put(dpublic.toString(), dpublic);
        }
        return map;
    }

    /**
     * @return The public as a string.
     */
    public String toString() {
        return dpublic;
    }
}
