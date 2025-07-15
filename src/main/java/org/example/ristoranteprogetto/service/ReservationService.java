package org.example.ristoranteprogetto.service;

import jakarta.transaction.Transactional;
import org.example.ristoranteprogetto.mapper.ReservationMapper;
import org.example.ristoranteprogetto.model.dto.ReservationDTO;
import org.example.ristoranteprogetto.model.entity.ReservationEntity;
import org.example.ristoranteprogetto.model.entity.TableEntity;
import org.example.ristoranteprogetto.repository.ReservationRepository;
import org.example.ristoranteprogetto.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // uso questa soluzione perchè il professore ci ha sconsigliato di utilizzare Autowired
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final ReservationMapper reservationMapper;



    public List<ReservationEntity> getReservationsByDate(LocalDate date) {
        return reservationRepository.findByData(date);
    }



    public List<TableEntity> getAvailableTables(LocalDate data, LocalTime orario, int numeroPersone) {
        List<TableEntity> allTables = tableRepository.findAll();
        return allTables.stream()
                .filter(t -> t.getPosti() >= numeroPersone)
                .filter(t -> reservationRepository.findByTableIdAndDataAndOrario(t.getId(), data, orario).isEmpty())
                .toList();
    }

    public ReservationEntity saveReservation(ReservationEntity reservation) {
        return reservationRepository.save(reservation);
    }

    public Optional<ReservationEntity> getById(Long id) {
        return reservationRepository.findById(id);
    }


    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }



    public ReservationDTO createReservation(ReservationDTO dto) {
        ReservationEntity reservation = reservationMapper.toEntity(dto);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationDTO> getReservationsByUserId(UUID userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    /*public ReservationDTO updateReservation(Long id, ReservationDTO updatedDto) {
        return reservationRepository.findById(id)
                .map(existing -> {
                    existing.setData(updatedDto.getDate());
                    existing.setTime(updatedDto.getTime());
                    existing.setPeople(updatedDto.getPeople());
                    existing.setStatus(updatedDto.getStatus());
                    // aggiorna table e user solo se serve
                    if (updatedDto.getTableId() != null) {
                        TableEntity table = new TableEntity();
                        table.setId(updatedDto.getTableId());
                        existing.setTable(table);
                    }
                    if (updatedDto.getUserId() != null) {
                        UserEntity user = new UserEntity();
                        user.setId(updatedDto.getUserId());
                        existing.setUser(user);
                    }
                    return reservationMapper.toDto(reservationRepository.save(existing));
                })
                .orElse(null);
    }*/

    public ReservationDTO updateStatus(Long id, String status) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setStatus(status);  // aggiorna lo stato
                    ReservationEntity updated = reservationRepository.save(reservation);
                    return reservationMapper.toDto(updated);
                })
                .orElse(null);
    }
}
