package com.jhonocampo.codigotonretocenacliente.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jhonocampo.codigotonretocenacliente.dto.FilterDto;
import com.jhonocampo.codigotonretocenacliente.model.Client;

public class ClientRepository {

    EntityManagerFactory emf = null;

    public ClientRepository() {
        Map properties = new HashMap();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        emf = Persistence.createEntityManagerFactory("com.jhonocampo.codigotonretocenacliente.model.Client",
                properties);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Client> findClients(FilterDto filter) {
        Boolean whereFlag = false;
        String queryClient = "SELECT c.company as company, c.id, c.code, c.location, c.type, c.male, c.encrypt, SUM(a.balance) as balance "
                + "FROM Client c " + "JOIN Account a ON a.client_id  = c.id ";

        if (filter.getTypeClient() != null) {
            if (!whereFlag) {
                queryClient += "WHERE type = " + filter.getTypeClient() + " ";
                whereFlag = true;
            } else {
                queryClient += "AND type = " + filter.getTypeClient() + " ";
            }
        }

        if (filter.getLocation() != null) {
            if (!whereFlag) {
                queryClient += "WHERE location = " + filter.getLocation() + " ";
                whereFlag = true;
            } else {
                queryClient += "AND location = " + filter.getLocation() + " ";

            }
        }

        if (filter.getStartingRange() != null) {
            if (!whereFlag) {
                queryClient += "WHERE balance > " + filter.getStartingRange() + " ";
                whereFlag = true;
            } else {
                queryClient += "AND balance > " + filter.getStartingRange() + " ";

            }
        }

        if (filter.getFinalRange() != null) {
            if (!whereFlag) {
                queryClient += "WHERE balance > " + filter.getFinalRange() + " ";
                whereFlag = true;
            } else {
                queryClient += "AND balance > " + filter.getFinalRange() + " ";

            }
        }

        queryClient += "GROUP BY c.id " + "ORDER BY balance DESC, code ASC";

        List<Client> clients = getEntityManager().createNativeQuery(queryClient, Client.class).getResultList();
        return clients;
    }
}
