package com.jhonocampo.codigotonretocenacliente.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.jhonocampo.codigotonretocenacliente.dto.DistribuiteDto;
import com.jhonocampo.codigotonretocenacliente.dto.FilterDto;
import com.jhonocampo.codigotonretocenacliente.dto.OutputDto;
import com.jhonocampo.codigotonretocenacliente.model.Client;
import com.jhonocampo.codigotonretocenacliente.repository.ClientRepository;
import com.jhonocampo.utils.controllers.GenericController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.pattern.Util;

@RestController
@RequestMapping("assingTable")
public class AssingTableController extends GenericController {

    private ClientRepository clientRepository = null;

    public AssingTableController() {
        super(AssingTableController.class);

        clientRepository = new ClientRepository();
    }

    @PostMapping("/distribute")
    public ResponseEntity<String> distribuite(@Valid DistribuiteDto params) {
        try {
            String content = new String(params.getFile().getBytes());
            List<FilterDto> filters = processFile(content);
            List<OutputDto> output = new ArrayList<>();
            for (int i = 0; i < filters.size(); i++) {
                List<Client> clients = filterClients(filters.get(i));
                clients = deleteClientCompaniesRepeat(clients);
                clients = validWomenMen(clients);
                OutputDto out = new OutputDto();
                out.setFilter(filters.get(i));
                out.setClients(clients);
                output.add(out);
            }
            String distribuiteString = generateOutput(output);
            return new ResponseEntity<>(distribuiteString, HttpStatus.OK);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logUtil.error(e.getMessage());

        }
        return null;
    }

    private List<Client> deleteClientCompaniesRepeat(List<Client> clients) {
        return clients.stream()
                    .filter( distinctByKey(Client::getCompany) )
                    .collect( Collectors.toList() );
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private List<Client> filterClients(FilterDto filters) {
        logUtil.info("inicia filterClients");
        List<Client> clients = null;
        clients = clientRepository.findClients(filters);
        logUtil.info(clients.toString());
        logUtil.info("Finaliza filterClients");
        return clients;
    }



    /**
     * Filtros 
     * Tipo de Cliente (TC) 
     * Código de ubicación geográfica (UG) 
     * Rango Inicial Balance (RI) 
     * Rango Final Balance (RF)
     */
    private List<FilterDto> processFile(String content) {
        logUtil.info("Inicia procesar archivo");
        List<String> splitTables = new ArrayList<>(Arrays.asList(content.split("<")));
        splitTables.remove(0);
        List<FilterDto> filters = new ArrayList<FilterDto>();
        for (int i = 0; i < splitTables.size(); i++) {
            FilterDto filter = new FilterDto();
            List<String> splitTable = new ArrayList<>(Arrays.asList(splitTables.get(i).split(">")));
            filter.setTable(splitTable.get(0));
            String replaceReturn = splitTable.get(1).replace("\r", "");
            replaceReturn = replaceReturn.replace("\n", ";");
            List<String> splitFilters = new ArrayList<>(Arrays.asList(replaceReturn.split(";")));
            splitFilters.remove(0);
            for (int j = 0; j < splitFilters.size(); j++) {
                String[] filterSplit = splitFilters.get(j).split(":");
                switch (filterSplit[0]) {
                case "TC":
                    filter.setTypeClient(filterSplit[1]);
                    break;
                case "UG":
                    filter.setLocation(filterSplit[1]);
                    break;
                case "RI":
                    filter.setStartingRange(filterSplit[1]);
                    break;
                case "RF":
                    filter.setFinalRange(filterSplit[1]);
                    break;
                default:
                    break;
                }
            }
            filters.add(filter);

        }
        logUtil.info(filters.toString());
        logUtil.info("Finaliza procesar archivo");
        return filters;
    }
    
    private List<Client> validWomenMen(List<Client> clients) {
        logUtil.info("Inicia validWomenMen");
        Integer limit = 4;
        List<Client> mens = clients.stream().filter(item -> item.getMale() == 1).collect(Collectors.toList());
        List<Client> womens = clients.stream().filter(item -> item.getMale() == 0).collect(Collectors.toList());

        if (mens.size() > 4) {
            mens = mens.subList(0, limit);
        }

        if (womens.size() > 4){
            womens = womens.subList(0, limit);
        }

        if (mens.size() < womens.size()){
            limit = mens.size();
            womens = womens.subList(0, limit);
        } else if(mens.size() > womens.size()){
            limit = womens.size();
            mens = mens.subList(0, limit);
        } else {
            mens = mens.subList(0, limit);
            womens = womens.subList(0, limit);
        }
        List<Client> all = new ArrayList<>();
        all.addAll(mens);
        all.addAll(womens);
        all = all.stream().sorted(Comparator.comparingDouble(Client::getBalance).reversed()).collect(Collectors.toList());
        logUtil.info(all.toString());
        logUtil.info("Fin validWomenMen");
        return all;
    }

    private String generateOutput(List<OutputDto> output){
        logUtil.info("Inicia generateOutput");
        logUtil.info(output.toString());
        String out = "";
        for (int i = 0; i < output.size(); i++) {
            OutputDto oDto = output.get(i);
            out += "<"+oDto.getFilter().getTable()+">\n\r";
            if (oDto.getClients().size() < 4){
                out += "CANCELADA,";
            } else {
                for (int j = 0; j < oDto.getClients().size(); j++) {
                    Client c = oDto.getClients().get(j);
                    if (c.getEncrypt() == 1) {
                        out += decrypt(c.getCode()) + ",";
                    } else {
                        out += c.getCode() + ",";
                    }
                }
            }
            out = out.substring(0, out.length()-1);
            out +="\n\r";
        }
        logUtil.info("Salida: " + out);
        logUtil.info("Fin generateOutput");
        return out;
    }

    private String decrypt(String code) {
        final String uri = "https://test.evalartapp.com/extapiquest/code_decrypt/";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri + code, String.class).replaceAll("\"", "");
        logUtil.info("Decrypt: " + result);
        return result;
    }

    /**
     * Criterios de aceptacion
     * 
      No mas de 8 personas por mesa
     * 
     Si hay mas de ocho, se debe priorizar los que mas tengan sumando el balance
     de todas sus cuentas
     * 
     * En caso de igualdad del total, se debe ordenar ascendente por codigo
     * 
      si los ocupantes de una mesa es menor a 4, se debe cancelar la mesa
     * 
     Una persona por empresa en la mesa. La de mayor monto
     * 
    Se debe tener el mismo numero de hombres y mujeres en cada mesa En caso de
     diferencia, se debe eliminar el del menor balance.
     * 
     * desencriptar copdigo segun sea el caso
     * https://test.evalartapp.com/extapiquest/code_decrypt/
     */

}
