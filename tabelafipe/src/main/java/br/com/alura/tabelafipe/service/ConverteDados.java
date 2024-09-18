package br.com.alura.tabelafipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverteDados{

    private ObjectMapper om = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try{
            return om.readValue(json, classe);
        }catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        CollectionType lista = om.getTypeFactory()
                .constructCollectionType(List.class, classe);

        try {
            return om.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
