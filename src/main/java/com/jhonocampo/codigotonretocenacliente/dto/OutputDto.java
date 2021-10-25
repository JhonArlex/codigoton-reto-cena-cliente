package com.jhonocampo.codigotonretocenacliente.dto;

import java.util.List;

import com.jhonocampo.codigotonretocenacliente.model.Client;

public class OutputDto {
    private FilterDto filter;
    private List<Client> clients;

    public OutputDto() {
    }

    public FilterDto getFilter() {
        return filter;
    }
    public void setFilter(FilterDto filter) {
        this.filter = filter;
    }
    public List<Client> getClients() {
        return clients;
    }
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "OutputDto [clients=" + clients + ", filter=" + filter + "]";
    }

    
    
}
