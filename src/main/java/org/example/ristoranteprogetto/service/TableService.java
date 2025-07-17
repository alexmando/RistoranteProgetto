package org.example.ristoranteprogetto.service;

//logica per tavoli lato admin

import jakarta.transaction.Transactional;
import org.example.ristoranteprogetto.mapper.TableMapper;
import org.example.ristoranteprogetto.model.dto.TableDTO;
import org.example.ristoranteprogetto.model.entity.TableEntity;
import org.example.ristoranteprogetto.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TableService {

    private final TableRepository tableRepository;
    private final TableMapper tableMapper;

    @Transactional
    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }

    public List<TableDTO> getAllTables() {
        return tableRepository.findAll()
                .stream()
                .map(tableMapper::toDto)
                .collect(Collectors.toList());
    }

    public TableDTO getTableById(Long id) {
        return tableRepository.findById(id)
                .map(tableMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public TableDTO saveTable(TableDTO tableDTO) {
        TableEntity table = tableMapper.toEntity(tableDTO);
        return tableMapper.toDto(tableRepository.save(table));
    }

    @Transactional
    public TableDTO updateTable(Long id, TableDTO dto) {
        return tableRepository.findById(id)
                .map(existingTable -> {
                    existingTable.setPosti(dto.getPosti());
                    TableEntity updated = tableRepository.save(existingTable);
                    return tableMapper.toDto(updated);
                })
                .orElse(null);
    }

    @Transactional
    public TableDTO createTable(TableDTO dto) {
        TableEntity table = tableMapper.toEntity(dto);
        TableEntity saved = tableRepository.save(table);
        return tableMapper.toDto(saved);
    }
}