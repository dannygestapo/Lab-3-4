
package by.bsuir.serko.bettingapp.db;


public enum DatabaseType {
    
    MYSQL, ORACLE, MONGODB;
    
    private static DatabaseType[] copyOfValues = values();
    
    public static DatabaseType forValue(String dbType) {
        for(DatabaseType type : copyOfValues) {
            if(type.name().equalsIgnoreCase(dbType)) {
                return type;
            }
        }
        return null;
    }
    
}
