package com.jhonocampo.codigotonretocenacliente.dto;

public class FilterDto {
    private String table;
    private String typeClient;
    private String location;
    private String startingRange;
    private String finalRange;

    

    @Override
    public String toString() {
        return "FilterDto [finalRange=" + finalRange + ", location=" + location + ", startingRange=" + startingRange
                + ", table=" + table + ", typeClient=" + typeClient + "]";
    }
    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public String getTypeClient() {
        return typeClient;
    }
    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getStartingRange() {
        return startingRange;
    }
    public void setStartingRange(String startingRange) {
        this.startingRange = startingRange;
    }
    public String getFinalRange() {
        return finalRange;
    }
    public void setFinalRange(String finalRange) {
        this.finalRange = finalRange;
    }

    
}
