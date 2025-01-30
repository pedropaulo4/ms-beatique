package br.com.beautique.api.utils;

import org.modelmapper.ModelMapper;

public class ConvertUtil <S,T> {

    private final ModelMapper modelMapper;
    private Class<S> sourceType;
    private Class<T> targetType;

    public ConvertUtil(Class<S> sourceType, Class<T> targetType) {
        this.modelMapper = new ModelMapper();
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public T convertToTarget(S source) {
        return modelMapper.map(source, targetType);
    }

    public S convertToSource(T target) {
        return modelMapper.map(target,sourceType);
    }
}
