package Service;

//logica per tavoli lato admin

import Mapper.TableMapper;
import Model.Dto.TableDTO;
import Model.Entity.TableEntity;
import org.example.ristoranteprogetto.Repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TableService {

    private final TableRepository tableRepository;
    private final TableMapper tableMapper;

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

    public TableDTO saveTable(TableDTO tableDTO) {
        TableEntity table = tableMapper.toEntity(tableDTO);
        return tableMapper.toDto(tableRepository.save(table));
    }

    public TableDTO updateTable(Long id, TableDTO dto) {
        return tableRepository.findById(id)
                .map(existingTable -> {
                    // Aggiorna i campi dell'entità esistente con quelli del DTO
                    existingTable.setPosti(dto.getPosti());
                    // salva e ritorna il DTO aggiornato
                    TableEntity updated = tableRepository.save(existingTable);
                    return tableMapper.toDto(updated);
                })
                .orElse(null);
    }

    public TableDTO createTable(TableDTO dto) {
        TableEntity table = tableMapper.toEntity(dto);
        TableEntity saved = tableRepository.save(table);
        return tableMapper.toDto(saved);
    }
}